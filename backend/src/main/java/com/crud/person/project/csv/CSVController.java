package com.crud.person.project.csv;

import com.crud.person.project.csv.messaging.RabbitConfig;
import com.crud.person.project.exception.GenerateCSVException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.crud.person.project.exception.GenerateCSVException.GENERATE_RESPONSE_ERROR_MESSAGE;


@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String RESPONSE_MESSAGE = "Arquivo CSV sendo gerado em output/person-register.csv";

    @GetMapping("/download")
    public void getCSV(HttpServletResponse response) {
        rabbitTemplate.convertAndSend(RabbitConfig.CSV_QUEUE, "Gerar CSV");
        response.setContentType("text/plain");
        try {
            response.getWriter().write(RESPONSE_MESSAGE);
        } catch (IOException e) {
            throw new GenerateCSVException(GENERATE_RESPONSE_ERROR_MESSAGE);
        }
    }
}
