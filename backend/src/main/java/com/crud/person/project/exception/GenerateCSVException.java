package com.crud.person.project.exception;

public class GenerateCSVException extends RuntimeException {

    public static final String NO_PERSON_REGISTERED_MESSAGE = "Não há pessoas cadastradas no sistema.";
    public static final String GENERATE_RESPONSE_ERROR_MESSAGE = "Erro ao escrever a resposta";
    public static final String ERROR_CREATING_DIRECTORY_MESSAGE = "Erro ao gerar diretório para salvar arquivo CSV";
    public static final String ERROR_SAVING_CSV_FILE_MESSAGE = "Erro ao salvar o arquivo CSV";

    public GenerateCSVException(String message) {
        super(message);
    }
}
