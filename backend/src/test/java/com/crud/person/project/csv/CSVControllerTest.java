package com.crud.person.project.csv;

import com.crud.person.project.csv.messaging.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CSVControllerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CSVController csvController;

    @Test
    void testGetCSV() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        csvController.getCSV(response);

        verify(rabbitTemplate, times(1)).convertAndSend(RabbitConfig.CSV_QUEUE, "Gerar CSV");
        assertEquals("Mensagem enviada para gerar o CSV!", response.getContentAsString());
        assertEquals("text/plain", response.getContentType());
    }

    @Test
    void testGetCSVException() throws Exception {
        MockHttpServletResponse response = mock(MockHttpServletResponse.class);

        doThrow(new IOException("Erro ao escrever a resposta")).when(response).getWriter();

        assertThrows(RuntimeException.class, () -> csvController.getCSV(response));
    }
}
