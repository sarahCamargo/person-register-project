package com.crud.person.project.exception;

public class GenerateCSVException extends RuntimeException {

    public static final String NO_PERSON_REGISTERED_MESSAGE = "Não há pessoas cadastradas no sistema.";
    public static final String GENERATE_RESPONSE_ERROR_MESSAGE = "Erro ao escrever a resposta";

    public GenerateCSVException(String message) {
        super(message);
    }
}
