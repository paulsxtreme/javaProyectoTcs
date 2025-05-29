-- =====================================================
-- SCRIPT PARA BASE DE DATOS: reto-java
-- =====================================================

-- Eliminación de tablas existentes
DROP TABLE IF EXISTS movimientos CASCADE;
DROP TABLE IF EXISTS cuentas CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;
DROP TABLE IF EXISTS personas CASCADE;

-- =====================================================
-- TABLA PERSONAS (con campos de versionado y auditoría)
-- =====================================================
CREATE TABLE personas (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    genero TEXT,
    edad INTEGER,
    identificacion TEXT UNIQUE NOT NULL,
    direccion TEXT,
    telefono TEXT,
    
    -- Campos requeridos para JPA
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_personas_edad CHECK (edad IS NULL OR (edad >= 0 AND edad <= 150)),
    CONSTRAINT chk_personas_identificacion_length CHECK (LENGTH(identificacion) >= 8)
);

-- =====================================================
-- TABLA CLIENTES (con eliminación en cascada)
-- =====================================================
CREATE TABLE clientes (
    clienteid BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    
    -- Esta columna 'id' es la que mapea a personas.id (CON CASCADE)
    id BIGINT UNIQUE NOT NULL REFERENCES personas(id) ON DELETE CASCADE,
    
    contrasena TEXT NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    
    -- Campos requeridos para JPA
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_clientes_contrasena_length CHECK (LENGTH(contrasena) >= 6)
);

-- =====================================================
-- TABLA CUENTAS (con eliminación en cascada)
-- =====================================================
CREATE TABLE cuentas (
    numerocuenta BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    clienteid BIGINT NOT NULL REFERENCES clientes(clienteid) ON DELETE CASCADE,
    tipocuenta TEXT NOT NULL,
    saldoinicial NUMERIC(15,2) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    
    -- Campos requeridos para JPA
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_cuentas_tipocuenta CHECK (tipocuenta IN ('AHORRO', 'CORRIENTE')),
    CONSTRAINT chk_cuentas_saldoinicial CHECK (saldoinicial >= 0)
);

-- =====================================================
-- TABLA MOVIMIENTOS (con eliminación en cascada)
-- =====================================================
CREATE TABLE movimientos (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    numerocuenta BIGINT NOT NULL REFERENCES cuentas(numerocuenta) ON DELETE CASCADE,
    fecha TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    tipomovimiento TEXT NOT NULL,
    valor NUMERIC(15,2) NOT NULL,
    saldo NUMERIC(15,2) NOT NULL,
    
    -- Campos requeridos para JPA
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_movimientos_tipomovimiento CHECK (tipomovimiento IN ('DEBITO', 'CREDITO')),
    CONSTRAINT chk_movimientos_valor CHECK (valor != 0),
    CONSTRAINT chk_movimientos_saldo CHECK (saldo >= 0)
);

-- =====================================================
-- FUNCIÓN Y TRIGGERS PARA updated_at
-- =====================================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_personas_updated_at 
    BEFORE UPDATE ON personas 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_clientes_updated_at 
    BEFORE UPDATE ON clientes 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_cuentas_updated_at 
    BEFORE UPDATE ON cuentas 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- =====================================================
-- ÍNDICES
-- =====================================================
CREATE INDEX idx_personas_identificacion ON personas(identificacion);
CREATE INDEX idx_clientes_persona_id ON clientes(id);
CREATE INDEX idx_cuentas_clienteid ON cuentas(clienteid);
CREATE INDEX idx_movimientos_numerocuenta ON movimientos(numerocuenta);
CREATE INDEX idx_movimientos_fecha_cuenta ON movimientos(numerocuenta, fecha);

-- =====================================================
-- 10 CLIENTES
-- =====================================================

INSERT INTO personas (nombre, genero, edad, identificacion, direccion, telefono) VALUES

('Juan Carlos Pérez', 'MASCULINO', 35, '1234567890', 'Av. Amazonas 123, Quito', '0998877665'),
('María Elena López', 'FEMENINO', 28, '0987654321', 'Calle 10 de Agosto 456, Quito', '0987654321'),
('Carlos Alberto Rodríguez', 'MASCULINO', 42, '1122334455', 'Via a Samborondón 789, Guayaquil', '0976543210'),
('Ana Patricia Morales', 'FEMENINO', 31, '5544332211', 'Calle Bolívar 321, Cuenca', '0965432109'),
('Luis Fernando García', 'MASCULINO', 29, '1357924680', 'Av. 6 de Diciembre 890, Quito', '0987123456'),
('Carmen Isabel Vásquez', 'FEMENINO', 45, '2468135790', 'Calle García Moreno 567, Loja', '0976234567'),
('Roberto Miguel Torres', 'MASCULINO', 38, '3691470258', 'Av. Francisco de Orellana 234, Guayaquil', '0965345678'),
('Sofía Alejandra Muñoz', 'FEMENINO', 26, '4815926037', 'Calle Sucre 678, Ambato', '0954456789'),
('Diego Andrés Herrera', 'MASCULINO', 33, '5927384601', 'Av. Eloy Alfaro 345, Machala', '0943567890'),
('Valentina Cristina Reyes', 'FEMENINO', 39, '6048271359', 'Calle Olmedo 789, Riobamba', '0932678901');

-- Insertar 10 clientes
INSERT INTO clientes (id, contrasena, estado) VALUES
(1, 'password123', TRUE),
(2, 'segura456', TRUE),
(3, 'clave789', TRUE),
(4, 'secret321', TRUE),
(5, 'luis2024', TRUE),
(6, 'carmen567', TRUE),
(7, 'roberto890', TRUE),
(8, 'sofia123', TRUE),
(9, 'diego456', TRUE),
(10, 'vale789', TRUE);

-- Insertar 14 cuentas distribuidas entre los clientes
INSERT INTO cuentas (clienteid, tipocuenta, saldoinicial, estado) VALUES
-- Cuentas para Juan Carlos Pérez (Cliente 1)
(1, 'AHORRO', 2500.00, TRUE),
(1, 'CORRIENTE', 1500.00, TRUE),

-- Cuenta para María Elena López (Cliente 2)
(2, 'AHORRO', 3200.75, TRUE),

-- Cuentas para Carlos Alberto Rodríguez (Cliente 3)
(3, 'CORRIENTE', 5000.00, TRUE),
(3, 'AHORRO', 8750.50, TRUE),

-- Cuenta para Ana Patricia Morales (Cliente 4)
(4, 'AHORRO', 1800.25, TRUE),

-- Cuentas para Luis Fernando García (Cliente 5)
(5, 'AHORRO', 4500.00, TRUE),
(5, 'CORRIENTE', 2000.00, TRUE),

-- Cuenta para Carmen Isabel Vásquez (Cliente 6)
(6, 'AHORRO', 6200.30, TRUE),

-- Cuenta para Roberto Miguel Torres (Cliente 7)
(7, 'CORRIENTE', 3800.50, TRUE),

-- Cuentas para Sofía Alejandra Muñoz (Cliente 8)
(8, 'AHORRO', 2750.00, TRUE),
(8, 'CORRIENTE', 1200.75, TRUE),

-- Cuenta para Diego Andrés Herrera (Cliente 9)
(9, 'AHORRO', 5500.25, TRUE),

-- Cuenta para Valentina Cristina Reyes (Cliente 10)
(10, 'CORRIENTE', 4100.80, TRUE);

-- Insertar 28 movimientos distribuidos entre todas las cuentas
INSERT INTO movimientos (numerocuenta, tipomovimiento, valor, saldo) VALUES
-- Movimientos para cuenta 1 (Juan - Ahorro)
(1, 'CREDITO', 500.00, 3000.00),
(1, 'DEBITO', -200.00, 2800.00),
(1, 'CREDITO', 150.00, 2950.00),

-- Movimientos para cuenta 2 (Juan - Corriente)
(2, 'DEBITO', -300.00, 1200.00),
(2, 'CREDITO', 800.00, 2000.00),

-- Movimientos para cuenta 3 (María - Ahorro)
(3, 'CREDITO', 1000.00, 4200.75),
(3, 'DEBITO', -500.00, 3700.75),

-- Movimientos para cuenta 4 (Carlos - Corriente)
(4, 'DEBITO', -1000.00, 4000.00),
(4, 'CREDITO', 2000.00, 6000.00),
(4, 'DEBITO', -500.00, 5500.00),

-- Movimientos para cuenta 5 (Carlos - Ahorro)
(5, 'CREDITO', 1249.50, 10000.00),

-- Movimientos para cuenta 6 (Ana - Ahorro)
(6, 'CREDITO', 700.00, 2500.25),
(6, 'DEBITO', -300.00, 2200.25),

-- Movimientos para cuenta 7 (Luis - Ahorro)
(7, 'CREDITO', 800.00, 5300.00),
(7, 'DEBITO', -250.00, 5050.00),

-- Movimientos para cuenta 8 (Luis - Corriente)
(8, 'DEBITO', -400.00, 1600.00),
(8, 'CREDITO', 600.00, 2200.00),

-- Movimientos para cuenta 9 (Carmen - Ahorro)
(9, 'CREDITO', 1500.00, 7700.30),
(9, 'DEBITO', -800.00, 6900.30),

-- Movimientos para cuenta 10 (Roberto - Corriente)
(10, 'DEBITO', -650.00, 3150.50),
(10, 'CREDITO', 900.00, 4050.50),

-- Movimientos para cuenta 11 (Sofía - Ahorro)
(11, 'CREDITO', 450.00, 3200.00),

-- Movimientos para cuenta 12 (Sofía - Corriente)
(12, 'DEBITO', -300.00, 900.75),
(12, 'CREDITO', 500.00, 1400.75),

-- Movimientos para cuenta 13 (Diego - Ahorro)
(13, 'CREDITO', 750.00, 6250.25),
(13, 'DEBITO', -200.00, 6050.25),

-- Movimientos para cuenta 14 (Valentina - Corriente)
(14, 'DEBITO', -500.00, 3600.80),
(14, 'CREDITO', 1200.00, 4800.80);

-- =====================================================
-- VERIFICACIÓN DE RESTRICCIONES CASCADE
-- =====================================================

-- Verificar que las restricciones de cascada están configuradas correctamente
SELECT 
    tc.table_name AS tabla,
    tc.constraint_name AS restriccion,
    kcu.column_name AS columna,
    ccu.table_name AS tabla_referenciada,
    ccu.column_name AS columna_referenciada,
    rc.delete_rule AS regla_eliminacion
FROM information_schema.table_constraints tc
JOIN information_schema.key_column_usage kcu 
    ON tc.constraint_name = kcu.constraint_name
JOIN information_schema.constraint_column_usage ccu 
    ON ccu.constraint_name = tc.constraint_name
JOIN information_schema.referential_constraints rc 
    ON tc.constraint_name = rc.constraint_name
WHERE tc.constraint_type = 'FOREIGN KEY'
    AND tc.table_schema = 'public'
ORDER BY tc.table_name;

-- Verificación de datos insertados
SELECT 'ESTRUCTURA CREADA CORRECTAMENTE CON 10 CLIENTES' as mensaje;
SELECT 
    'personas' as tabla, COUNT(*) as registros FROM personas
UNION ALL
SELECT 
    'clientes' as tabla, COUNT(*) as registros FROM clientes
UNION ALL
SELECT 
    'cuentas' as tabla, COUNT(*) as registros FROM cuentas
UNION ALL
SELECT 
    'movimientos' as tabla, COUNT(*) as registros FROM movimientos;

-- =====================================================
-- CONSULTA RESUMEN DE CLIENTES Y SUS CUENTAS
-- =====================================================
SELECT 
    c.clienteid,
    p.nombre,
    p.identificacion,
    COUNT(cu.numerocuenta) as total_cuentas,
    STRING_AGG(cu.tipocuenta, ', ') as tipos_cuenta,
    SUM(cu.saldoinicial) as saldo_inicial_total
FROM clientes c
JOIN personas p ON c.id = p.id
LEFT JOIN cuentas cu ON c.clienteid = cu.clienteid
GROUP BY c.clienteid, p.nombre, p.identificacion
ORDER BY c.clienteid;

-- =====================================================
-- INFORMACIÓN IMPORTANTE SOBRE ELIMINACIÓN EN CASCADA
-- =====================================================

