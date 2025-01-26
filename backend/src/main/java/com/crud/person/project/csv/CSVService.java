package com.crud.person.project.csv;

import com.crud.person.project.exception.GenerateCSVException;
import com.crud.person.project.model.Pessoa;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class CSVService {

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
        String filePath = "output/person-report.csv";

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new GenerateCSVException("Erro ao gerar diretório para salvar arquivo CSV");
            }
        }

        Path path = Path.of(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(getCSV(pessoas));
        } catch (IOException e) {
            throw new GenerateCSVException("Erro ao salvar o arquivo CSV");
        }
    }
}
