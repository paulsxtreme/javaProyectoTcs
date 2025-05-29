package com.ejercicio.java.exception;

public class SaldoNoDisponibleException extends RuntimeException { // Excepción personalizada para saldo insuficiente

    public SaldoNoDisponibleException() { // Constructor por defecto con mensaje estándar
        super("Saldo no disponible"); // Llama al constructor padre con mensaje predefinido
    }

    public SaldoNoDisponibleException(String message) { // Constructor con mensaje personalizado
        super(message); // Llama al constructor padre con mensaje específico
    }
}