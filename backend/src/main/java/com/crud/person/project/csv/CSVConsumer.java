package com.crud.person.project.csv;

import com.crud.person.project.csv.messaging.RabbitConfig;
import com.crud.person.project.exception.GenerateCSVException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.PessoaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CSVConsumer {

    @Autowired
    private CSVService csvService;

    @Autowired
    private PessoaService pessoaService;

    @RabbitListener(queues = RabbitConfig.CSV_QUEUE)
    public void processCSVRequest(String message) {
        List<Pessoa> pessoas = pessoaService.findAll();

        if (pessoas.isEmpty()) {
            throw new GenerateCSVException("Não há pessoas cadastradas no sistema.");
        }

        csvService.saveCSVToFile(pessoas);
    }
}
