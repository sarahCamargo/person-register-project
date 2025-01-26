package com.crud.person.project.csv;

import com.crud.person.project.exception.GenerateCSVException;
import com.crud.person.project.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static com.crud.person.project.exception.GenerateCSVException.ERROR_CREATING_DIRECTORY_MESSAGE;
import static com.crud.person.project.exception.GenerateCSVException.ERROR_SAVING_CSV_FILE_MESSAGE;

@Service
public class CSVService {

    private final CSVConfig csvConfig;

    @Autowired
    public CSVService(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    public String getCSV(List<Pessoa> pessoas) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Id,Nome,Telefone,CPF,CEP,Logradouro,Bairro,Municipio,Estado,Numero,Complemento\n");
        for (Pessoa pessoa : pessoas) {
            csvContent.append(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                    pessoa.getId(),
                    pessoa.getNome(),
                    pessoa.getTelefone(),
                    pessoa.getCpf(),
                    pessoa.getCep(),
                    pessoa.getLogradouro(),
                    pessoa.getBairro(),
                    pessoa.getMunicipio(),
                    pessoa.getEstado(),
                    pessoa.getNumero(),
                    pessoa.getComplemento()));
        }
        return csvContent.toString();
    }

    public void saveCSVToFile(List<Pessoa> pessoas) {
        String directory = csvConfig.getDirectory();
        String filename = csvConfig.getFilename();

        Path path = Path.of(directory, filename);

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new GenerateCSVException(ERROR_CREATING_DIRECTORY_MESSAGE);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(getCSV(pessoas));
        } catch (IOException e) {
            throw new GenerateCSVException(ERROR_SAVING_CSV_FILE_MESSAGE);
        }
    }
}
