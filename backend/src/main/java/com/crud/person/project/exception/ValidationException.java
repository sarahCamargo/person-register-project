package com.crud.person.project.exception;

public class ValidationException extends RuntimeException {

    public static final String CPF_INVALID_MESSAGE = "CPF Inválido.";
    public static final String NUMBER_INVALID_MESSAGE = "Número Inválido";

    public ValidationException(String message) {
        super(message);
    }
}
