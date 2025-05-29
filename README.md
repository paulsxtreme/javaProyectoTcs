# 🏦 Proyecto Microservicio Bancario

Proyecto microservicio Bancario desarrollado con Java y Maven que permite realizar operaciones bancarias como crear, editar y eliminar clientes, así como generar movimientos para acreditar y debitar el estado de cuenta.

## 🔧 Tecnologías

- Java
- Spring Boot
- Maven
- PostgreSQL
- JPA/Hibernate

## 🚀 1. Operaciones de Clientes

### 1.1 Consultar clientes

**Endpoint:** `GET http://localhost:8080/clientes`

### 1.2 Crear Cliente

**Endpoint:** `POST http://localhost:8080/clientes`

```json
{
  "persona": {
    "nombre": "Carlos Sanchez",
    "genero": "Masculino",
    "edad": 40,
    "identificacion": "1712131415",
    "direccion": "Av. Galo Plaza Lasso N22-222",
    "telefono": "0987654321"
  },
  "contrasena": "Mipass123",
  "estado": true
}
```

**Resultado (200 OK):**
```json
{
  "clienteid": 10,
  "persona": {
    "id": 10,
    "nombre": "Carlos Sanchez",
    "genero": "Masculino",
    "edad": 40,
    "identificacion": "1712131415",
    "direccion": "Av. Galo Plaza Lasso N22-222",
    "telefono": "0987654321",
    "version": 0
  },
  "contrasena": "Mipass123",
  "estado": true,
  "version": null
}
```

## 
### 1.3 Editar Cliente
### Verificar si existe el cliente o el "id"

**Endpoint:** `PUT http://localhost:8080/clientes/9`

```json
{
  "persona": {
    "nombre": "Paul Salazar",
    "genero": "Masculino",
    "edad": 36,
    "identificacion": "1725544444",
    "direccion": "Av. Naciones unidas N10-100",
    "telefono": "0999999999"
  },
  "contrasena": "Paul4567",
  "estado": true
}
```

**Rusultado (200 OK):**
```json
{
  "clienteid": 9,
  "persona": {
    "id": 9,
    "nombre": "Paul Salazar",
    "genero": "Masculino",
    "edad": 36,
    "identificacion": "1725544444",
    "direccion": "Av. Naciones unidas N10-100",
    "telefono": "0999999999",
    "version": 0
  },
  "contrasena": "Paul4567",
  "estado": true,
  "version": null
}
```

### 1.4 Eliminar Cliente
### Verificar si existe el cliente o el "id"

**Endpoint:** `DELETE http://localhost:8080/clientes/8`


**Resultado (204 No Content)**
##



## 📊2. Operaciones de Cuentas


### 2.1 Consultar clientes

**Endpoint:** `GET http://localhost:8080/cuentas`

### 2.2 Crear Cuenta

**Endpoint:** `POST http://localhost:8080/cuentas`

```json
{
  "clienteid": 10,
  "tipocuenta": "AHORRO",
  "saldoinicial": 1000.00,
  "estado": true
}
```

**Resultado (201 Created):**
```json
{
  "numerocuenta": 5,
  "clienteid": 10,
  "nombreCliente": "Carlos Sanchez",
  "tipocuenta": "Ahorros",
  "saldoinicial": 1000.00,
  "estado": true
}
```

### 2.3 Editar Cuenta

### Verificar si existe la cuenta "5" y el clienteid: "10", se puede modificar el tipo de cuenta y el estado.

**Endpoint:** `PUT http://localhost:8080/cuentas/5`

```json
{
  "clienteid": 10,
  "tipocuenta": "CORRIENTE",
  "saldoinicial": 1000.00,
  "estado": true
}
```

**Resultado (200 OK):**
```json
{
  "numerocuenta": 5,
  "clienteid": 10,
  "nombreCliente": "Carlos Sanchez",
  "tipocuenta": "CORRIENTE",
  "saldoinicial": 1000.00,
  "estado": true
}
```
### 2.4 Eliminar Cuenta

### Verificar si existe la cuenta "5"

**Endpoint:** `DELETE http://localhost:8080/cuentas/5`

```
**Resultado (204 No Content)**
```

##
## 💰3. Operaciones de Movimientos
### 3.1 Consultar movimientos
### Verificar si existe el numerocuenta "4".
**Endpoint:** `GET http://localhost:8080/movimientos/cuenta/4`

```json
 {
        "id": 8,
        "numerocuenta": 4,
        "fecha": "2025-05-28T15:08:25.620726-05:00",
        "tipomovimiento": "DEBITO",
        "valor": -1000.00,
        "saldo": 4000.00,
        "nombreCliente": "Carlos Alberto Rodríguez",
        "tipoCuenta": "CORRIENTE",
        "version": null
    }
```



### 3.2 crear movimiento "Realizar Depósito"
### Verificar si existe el numerocuenta "4".
**Endpoint:** `POST http://localhost:8080/movimientos`

```json
{
  "numerocuenta": 4,
  "tipomovimiento": "CREDITO",
  "valor": 500.00
}
```

**Resultado (201 Created):**
```json
{
  "id": 6,
  "numerocuenta": 4,
  "fecha": "2025-05-20T15:30:45-05:00",
  "tipomovimiento": "CREDITO",
  "valor": 500.00,
  "saldo": 5500.00,
  "nombreCliente": "Marianela Montalvo",
  "tipoCuenta": "Ahorros"
}
```

### 3.3 crear movimiento "Realizar Retiro"
### Verificar si existe el numerocuenta "4".
**Endpoint:** `POST http://localhost:8080/movimientos`

```json
{
  "numerocuenta": 4,
  "tipomovimiento": "DEBITO",
  "valor": 300.00
}
```

**Resultado (201 Created):**
```json
{
  "id": 7,
  "numerocuenta": 4,
  "fecha": "2025-05-20T15:35:20-05:00",
  "tipomovimiento": "DEBITO",
  "valor": -300.00,
  "saldo": 5200.00,
  "nombreCliente": "Marianela Montalvo",
  "tipoCuenta": "Ahorros"
}
```
### 3.4 Eliminar movimiento

### Verificar si existe el movimiento "32"
**Endpoint:** `DELETE http://localhost:8080/movimientos/32`


### 3.5 Saldo no disponible
### Verificar si existe el numerocuenta "20" y valor inicial en cero.
**Endpoint:** `POST http://localhost:8080/movimientos`

```json
{
  "numerocuenta": 20,
  "tipomovimiento": "DEBITO",
  "valor": 100.00
}
```

**Resultado (400 Bad Request):**
```json
{
    "timestamp": "2025-05-29T10:20:47.4334519-05:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Saldo no disponible",
    "path": "/movimientos",
    "errors": []
}
```


##
### 4. Generar Reporte
##



**Endpoint:** `GET http://localhost:8080/reportes?clienteid=3&fechaInicio=2025-05-01&fechaFin=2025-05-31http://localhost:8080/reportes?clienteid=3&fechaInicio=2025-05-01&fechaFin=2025-05-31`

**Resultado (200 OK):**

```json
{
    "nombreCliente": "Carlos Alberto Rodríguez",
    "identificacion": "1122334455",
    "cuentas": [
        {
            "numeroCuenta": 4,
            "tipoCuenta": "CORRIENTE",
            "saldoDisponible": 5000.00,
            "movimientos": [
                {
                    "fecha": "2025-05-28T15:08:25.620726-05:00",
                    "tipoMovimiento": "DEBITO",
                    "valor": -500.00,
                    "saldo": 5500.00,
                    "version": null
                },
                {
                    "fecha": "2025-05-28T15:08:25.620726-05:00",
                    "tipoMovimiento": "CREDITO",
                    "valor": 2000.00,
                    "saldo": 6000.00,
                    "version": null
                },
                {
                    "fecha": "2025-05-28T15:08:25.620726-05:00",
                    "tipoMovimiento": "DEBITO",
                    "valor": -1000.00,
                    "saldo": 4000.00,
                    "version": null
                },
                {
                    "fecha": "2025-05-28T15:32:24.278909-05:00",
                    "tipoMovimiento": "CREDITO",
                    "valor": 314.26,
                    "saldo": 5814.26,
                    "version": null
                },
                {
                    "fecha": "2025-05-28T15:32:28.736751-05:00",
                    "tipoMovimiento": "CREDITO",
                    "valor": 314.26,
                    "saldo": 6128.52,
                    "version": null
                },
                {
                    "fecha": "2025-05-28T17:10:15.056068-05:00",
                    "tipoMovimiento": "CREDITO",
                    "valor": 350.26,
                    "saldo": 6478.78,
                    "version": null
                }
            ]
        }
    ]
}
```


