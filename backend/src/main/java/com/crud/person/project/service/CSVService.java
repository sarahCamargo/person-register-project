package com.crud.person.project.service;

import com.crud.person.project.model.Pessoa;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CSVService {

    public void getCSV(HttpServletResponse response, List<Pessoa> pessoas) {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=person-report.csv");
        response.setCharacterEncoding("UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

            writer.println("Id,Nome,Idade,Email");

            for (Pessoa pessoa : pessoas) {
                writer.println(String.format("%d,%s",
                        pessoa.getId(),
                        pessoa.getNome()));
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar o arquivo CSV", e);
        }
    }
}
