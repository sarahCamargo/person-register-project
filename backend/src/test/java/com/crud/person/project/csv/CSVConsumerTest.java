package com.crud.person.project.csv;

import com.crud.person.project.exception.GenerateCSVException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CSVConsumerTest {

    @Mock
    private PessoaService pessoaService;

    @Mock
    private CSVService csvService;

    @InjectMocks
    private CSVConsumer csvConsumer;

    @Test
    void testProcessCSVRequest() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        pessoa1.setNome("Pessoa 1");

        List<Pessoa> pessoas = List.of(pessoa1);

        when(pessoaService.findAll()).thenReturn(pessoas);

        csvConsumer.processCSVRequest("Gerar CSV");

        verify(pessoaService, times(1)).findAll();
        verify(csvService, times(1)).saveCSVToFile(pessoas);
    }

    @Test
    void testProcessCSVRequestEmptyList() {

        when(pessoaService.findAll()).thenReturn(List.of());

        assertThrows(GenerateCSVException.class, () -> csvConsumer.processCSVRequest("Gerar CSV"));

        verify(pessoaService, times(1)).findAll();
        verify(csvService, never()).saveCSVToFile(any());
    }

}
