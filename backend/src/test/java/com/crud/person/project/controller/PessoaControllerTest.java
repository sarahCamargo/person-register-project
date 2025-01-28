package com.crud.person.project.controller;

import com.crud.person.project.exception.CpfAlreadyRegisteredException;
import com.crud.person.project.exception.GlobalExceptionHandler;
import com.crud.person.project.exception.ValidationException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.PessoaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import com.crud.person.project.utils.CPFValidation;
import com.crud.person.project.utils.NumberValidation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.crud.person.project.exception.CpfAlreadyRegisteredException.CPF_ALREADY_REGISTERED_MESSAGE;
import static com.crud.person.project.exception.ValidationException.CPF_INVALID_MESSAGE;
import static com.crud.person.project.exception.ValidationException.NUMBER_INVALID_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PessoaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private static final String URI = "/api/pessoa/";

    @Test
    void saveCpfNotExistsTest() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setCpf("95550964066");

        when(pessoaService.findByCpf(pessoa.getCpf())).thenReturn(Optional.empty());
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);

        String json = this.getPersonAsJson(pessoa);

        mockMvc.perform(post(URI)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void saveCpfExistsTest() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf("95550964066");

        when(pessoaService.findByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));

        String json = this.getPersonAsJson(pessoa);

        mockMvc.perform(post(URI)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict())
                .andExpect(result -> assertInstanceOf(CpfAlreadyRegisteredException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(CPF_ALREADY_REGISTERED_MESSAGE, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void findAllTest() throws Exception {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        pessoa1.setNome("Sarah");
        pessoa1.setCpf("95550964066");

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        pessoa2.setNome("Lucas");
        pessoa2.setCpf("93570033040");

        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        String json = this.getPersonAsJson(pessoas);

        when(pessoaService.findAll()).thenReturn(pessoas);

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(pessoaService, times(1)).findAll();
    }

    @Test
    void findByIdWhenFoundTest() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Sarah");
        pessoa.setCpf("95550964066");

        when(pessoaService.findById(1L)).thenReturn(Optional.of(pessoa));

        String json = this.getPersonAsJson(pessoa);

        mockMvc.perform(get(URI + pessoa.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(pessoaService, times(1)).findById(pessoa.getId());
    }

    @Test
    void findByIdWhenNotFoundTest() throws Exception {
        when(pessoaService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get(URI + anyLong()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(pessoaService, times(1)).findById(anyLong());
    }

    @Test
    void updateWhenPersonExistsTest() throws Exception {
        Pessoa pessoaAntiga = new Pessoa();
        pessoaAntiga.setId(1L);
        pessoaAntiga.setNome("Sarah");
        pessoaAntiga.setCpf("95550964066");
        pessoaAntiga.setTelefone("41999999999");

        Pessoa pessoaEditada = new Pessoa();
        pessoaEditada.setId(1L);
        pessoaEditada.setNome("Sarah Camargo");
        pessoaEditada.setCpf("93570033040");
        pessoaEditada.setTelefone("41988888888");

        when(pessoaService.findById(pessoaAntiga.getId())).thenReturn(Optional.of(pessoaAntiga));
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoaEditada);

        String jsonPessoaAntiga = this.getPersonAsJson(pessoaAntiga);
        String jsonPessoaNova = this.getPersonAsJson(pessoaEditada);

        mockMvc.perform(put(URI + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPessoaAntiga)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonPessoaNova));

        verify(pessoaService, times(1)).findById(anyLong());
        verify(pessoaService, times(1)).save(any(Pessoa.class));
    }

    @Test
    void updateWhenPersonNotExistsTest() throws Exception {
        Pessoa pessoaAntiga = new Pessoa();
        pessoaAntiga.setNome("Sarah Camargo");
        pessoaAntiga.setCpf("95550964066");
        pessoaAntiga.setTelefone("41988888888");

        when(pessoaService.findById(anyLong())).thenReturn(Optional.empty());

        String jsonPessoaAntiga = this.getPersonAsJson(pessoaAntiga);

        mockMvc.perform(put(URI + anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPessoaAntiga)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(pessoaService, times(1)).findById(anyLong());
        verify(pessoaService, never()).save(any(Pessoa.class));
    }


    @Test
    void deleteWhenPersonExistsTest() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Sarah");

        when(pessoaService.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        mockMvc.perform(delete(URI + pessoa.getId()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(pessoaService, times(1)).deleteById(pessoa.getId());
    }

    @Test
    void deleteWhenPersonNotExistsTest() throws Exception {
        mockMvc.perform(delete(URI + anyLong()))
                .andExpect(status().isNotFound());

        verify(pessoaService, never()).deleteById(anyLong());
    }

    @Test
    void saveCpfValid() {
        assertDoesNotThrow(() -> CPFValidation.validaCpf("95550964066"));
    }

    @Test
    void saveCpfNotValid() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> CPFValidation.validaCpf("123123")
        );

        assertEquals(CPF_INVALID_MESSAGE, exception.getMessage());
    }

    @Test
    void saveNumberValid() {
        assertDoesNotThrow(() -> NumberValidation.validateInteger("12"));
        assertDoesNotThrow(() -> NumberValidation.validateInteger(""));
        assertDoesNotThrow(() -> NumberValidation.validateInteger(null));
    }

    @Test
    void saveNumberNotValid() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> NumberValidation.validateInteger("teste")
        );

        assertEquals(NUMBER_INVALID_MESSAGE, exception.getMessage());
    }

    private String getPersonAsJson(Object pessoa) throws JsonProcessingException {
        ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectMapper.writeValueAsString(pessoa);
    }

}
