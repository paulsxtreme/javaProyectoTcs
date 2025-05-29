package com.ejercicio.java.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

// Manejador global de excepciones para toda la aplicación REST
@RestControllerAdvice
public class ExceptionHandlerConfig {

    // Maneja excepciones cuando un recurso no es encontrado (404)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontradoException(
            RecursoNoEncontradoException ex, HttpServletRequest request) {
        // Crea respuesta de error con código 404 y mensaje personalizado
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        // Retorna respuesta con estado HTTP 404
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Maneja excepciones cuando no hay saldo suficiente (400)
    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> handleSaldoNoDisponibleException(
            SaldoNoDisponibleException ex, HttpServletRequest request) {
        // Crea respuesta de error con código 400 para saldo insuficiente
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        // Retorna respuesta con estado HTTP 400
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Maneja errores de validación de campos en DTOs (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Obtiene los errores de validación del binding result
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // Procesa los errores de campo y crea la respuesta
        ErrorResponse errorResponse = processFieldErrors(fieldErrors, request);

        // Retorna respuesta con estado HTTP 400
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Maneja violaciones de restricciones a nivel de entidad (400)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        // Crea respuesta base para errores de validación de restricciones
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validación",
                request.getRequestURI()
        );

        // Procesa cada violación y extrae el nombre del campo
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            // Obtiene solo el nombre del campo (última parte del path)
            field = field.substring(field.lastIndexOf('.') + 1);
            // Agrega el error a la lista de errores de validación
            errorResponse.getErrors().add(new ErrorResponse.ValidationError(field, violation.getMessage()));
        });

        // Retorna respuesta con estado HTTP 400
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Maneja cualquier otra excepción no controlada (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        // Crea respuesta de error interno del servidor
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Error interno del servidor: " + ex.getMessage(),
                request.getRequestURI()
        );
        // Retorna respuesta con estado HTTP 500
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Metodo auxiliar que procesa errores de validación de campos
    private ErrorResponse processFieldErrors(List<FieldError> fieldErrors, HttpServletRequest request) {
        // Crea respuesta base para errores de validación
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validación",
                request.getRequestURI()
        );

        // Crea lista para almacenar errores de validación
        List<ErrorResponse.ValidationError> validationErrors = new ArrayList<>();
        // Itera sobre cada error de campo
        for (FieldError fieldError : fieldErrors) {
            // Agrega error con nombre de campo y mensaje a la lista
            validationErrors.add(new ErrorResponse.ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        // Establece la lista de errores en la respuesta
        errorResponse.setErrors(validationErrors);
        // Retorna la respuesta de error procesada
        return errorResponse;
    }
}