package com.crud.person.project.csv;

import com.crud.person.project.csv.messaging.RabbitConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/download")
    public void getCSV(HttpServletResponse response) {
        rabbitTemplate.convertAndSend(RabbitConfig.CSV_QUEUE, "Gerar CSV");
        response.setContentType("text/plain");
        try {
            response.getWriter().write("Mensagem enviada para gerar o CSV!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao escrever a resposta", e);
        }
    }
}
