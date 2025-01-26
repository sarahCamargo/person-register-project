package com.crud.person.project.exception;

public class CpfAlreadyRegisteredException extends RuntimeException {

    public static final String CPF_ALREADY_REGISTERED_MESSAGE = "CPF já cadastrado no sistema.";

    public CpfAlreadyRegisteredException() {
        super(CPF_ALREADY_REGISTERED_MESSAGE);
    }
}
