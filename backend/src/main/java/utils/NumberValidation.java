package utils;

import com.crud.person.project.exception.ValidationException;

public class NumberValidation {

    public static void validateInteger(String numero) {
        try {
            if (numero != null && !numero.isEmpty()) {
                Integer.parseInt(numero);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Número Inválido");
        }
    }
}
