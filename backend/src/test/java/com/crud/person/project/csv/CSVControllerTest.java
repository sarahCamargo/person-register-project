package com.crud.person.project.csv;

import com.crud.person.project.csv.messaging.RabbitConfig;
import com.crud.person.project.exception.GenerateCSVException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static com.crud.person.project.csv.CSVController.RESPONSE_MESSAGE;
import static com.crud.person.project.exception.GenerateCSVException.GENERATE_RESPONSE_ERROR_MESSAGE;
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
        assertEquals(RESPONSE_MESSAGE, response.getContentAsString());
        assertEquals("text/plain", response.getContentType());
    }

    @Test
    void testGetCSVException() throws Exception {
        MockHttpServletResponse response = mock(MockHttpServletResponse.class);

        doThrow(new IOException()).when(response).getWriter();

        GenerateCSVException exception = assertThrows(
                GenerateCSVException.class,
                () -> csvController.getCSV(response)
        );

        assertEquals(GENERATE_RESPONSE_ERROR_MESSAGE, exception.getMessage());
    }
}
