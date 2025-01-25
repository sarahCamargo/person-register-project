package com.crud.person.project.service;

import com.crud.person.project.exception.GenerateCSVException;
import com.crud.person.project.model.Pessoa;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {

    public void getCSV(HttpServletResponse response, List<Pessoa> pessoas) {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=person-report.csv");
            response.setCharacterEncoding("UTF-8");

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
            response.getWriter().write(csvContent.toString());
        } catch (IOException e) {
            throw new GenerateCSVException("Erro ao gerar conteúdo CSV");
        }
    }
}
