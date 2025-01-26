package com.crud.person.project.csv;

import com.crud.person.project.csv.messaging.RabbitConfig;
import com.crud.person.project.exception.GenerateCSVException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static com.crud.person.project.csv.CSVController.RESPONSE_MESSAGE;
import static com.crud.person.project.exception.GenerateCSVException.DIRECTORY_FILENAME_NULL_MESSAGE;
import static com.crud.person.project.exception.GenerateCSVException.GENERATE_RESPONSE_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CSVControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private CSVConfig csvConfig;

    @InjectMocks
    private CSVController csvController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(csvController).build();
    }

    @Test
    void testGetCSV() throws Exception {
        when(csvConfig.getDirectory()).thenReturn("output");
        when(csvConfig.getFilename()).thenReturn("person-register.csv");

        String responseExpected = RESPONSE_MESSAGE + "output/person-register.csv";

        mockMvc.perform(get("/api/csv/download"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain"))
                .andExpect(content().string(responseExpected));

        verify(rabbitTemplate, times(1)).convertAndSend(RabbitConfig.CSV_QUEUE, "Gerar CSV");
    }

    @Test
    void testGetCSVException() throws Exception {
        when(csvConfig.getDirectory()).thenReturn("output");
        when(csvConfig.getFilename()).thenReturn("person-register.csv");

        MockHttpServletResponse response = mock(MockHttpServletResponse.class);

        doThrow(new IOException()).when(response).getWriter();

        GenerateCSVException writerException = assertThrows(
                GenerateCSVException.class,
                () -> csvController.getCSV(response)
        );

        assertEquals(GENERATE_RESPONSE_ERROR_MESSAGE, writerException.getMessage());

        doThrow(new AmqpException("")).when(rabbitTemplate).convertAndSend(RabbitConfig.CSV_QUEUE, "Gerar CSV");

        GenerateCSVException rabbitTemplateException = assertThrows(
                GenerateCSVException.class,
                () -> csvController.getCSV(response)
        );

        assertEquals(GENERATE_RESPONSE_ERROR_MESSAGE, rabbitTemplateException.getMessage());
    }

    @Test
    void testDirectoryFileNameNull() {

        MockHttpServletResponse response = mock(MockHttpServletResponse.class);

        GenerateCSVException exception = assertThrows(
                GenerateCSVException.class,
                () -> csvController.getCSV(response)
        );

        assertEquals(DIRECTORY_FILENAME_NULL_MESSAGE, exception.getMessage());
    }
}
