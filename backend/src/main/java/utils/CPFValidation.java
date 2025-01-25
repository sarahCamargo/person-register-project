package utils;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.crud.person.project.exception.CpfNotValidException;

public class CPFValidation {

    public static void validaCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            throw new CpfNotValidException("CPF Inválido.");
        }
    }
}
