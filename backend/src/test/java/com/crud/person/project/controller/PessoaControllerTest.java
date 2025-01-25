package com.crud.person.project.controller;

import com.crud.person.project.exception.CpfAlreadyRegisteredException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.CSVService;
import com.crud.person.project.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @Mock
    private CSVService csvService;

    @InjectMocks
    private PessoaController pessoaController;

    @Test
    void saveCpfNotExistsTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setCpf("123123123");

        when(pessoaService.findByCpf(pessoa.getCpf())).thenReturn(Optional.empty());
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa savedPessoa = pessoaController.save(pessoa);

        assertNotNull(savedPessoa);
        verify(pessoaService, times(1)).save(pessoa);

    }

    @Test
    void saveCpfExistsTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf("123123123");

        when(pessoaService.findByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa));

        CpfAlreadyRegisteredException exception = assertThrows(
                CpfAlreadyRegisteredException.class,
                () -> pessoaController.save(pessoa)
        );

        assertEquals("CPF já cadastrado no sistema.", exception.getMessage());
        verify(pessoaService, never()).save(any(Pessoa.class));
    }

    @Test
    void findAllTest() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        pessoa1.setNome("Sarah");
        pessoa1.setCpf("123123123");

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        pessoa2.setNome("Lucas");
        pessoa2.setCpf("321321321");

        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        when(pessoaService.findAll()).thenReturn(pessoas);

        List<Pessoa> retornoPessoas = pessoaController.findAll();

        assertNotNull(retornoPessoas);
        assertEquals(pessoas.size(), retornoPessoas.size());
        assertEquals(pessoas, retornoPessoas);
        verify(pessoaService, times(1)).findAll();
    }

    @Test
    void findByIdWhenFoundTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Sarah");
        pessoa.setCpf("123123123");

        when(pessoaService.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        ResponseEntity<Pessoa> response = pessoaController.findById(pessoa.getId());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(pessoa, response.getBody());
        verify(pessoaService, times(1)).findById(pessoa.getId());
    }

    @Test
    void findByIdWhenNotFoundTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Sarah");
        pessoa.setCpf("123123123");

        when(pessoaService.findById(pessoa.getId())).thenReturn(Optional.empty());

        ResponseEntity<Pessoa> response = pessoaController.findById(pessoa.getId());

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertFalse(response.hasBody());
        verify(pessoaService, times(1)).findById(pessoa.getId());
    }

    @Test
    void updateWhenPersonExistsTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Sarah");
        pessoa.setCpf("123123123");
        pessoa.setTelefone("41999999999");

        Pessoa pessoaEdited = new Pessoa();
        pessoaEdited.setNome("Sarah Camargo");
        pessoaEdited.setCpf("321321321");
        pessoaEdited.setTelefone("41988888888");

        when(pessoaService.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaService.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Pessoa> response = pessoaController.update(pessoa.getId(), pessoaEdited);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(pessoaEdited.getNome(), Objects.requireNonNull(response.getBody()).getNome());
        assertEquals(pessoaEdited.getTelefone(), response.getBody().getTelefone());
        verify(pessoaService, times(1)).findById(pessoa.getId());
        verify(pessoaService, times(1)).save(any(Pessoa.class));
    }

    @Test
    void updateWhenPersonNotExistsTest() {
        final Long id = 99L;
        Pessoa pessoaEdited = new Pessoa();
        pessoaEdited.setNome("Sarah Camargo");
        pessoaEdited.setCpf("321321321");
        pessoaEdited.setTelefone("41988888888");

        when(pessoaService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Pessoa> response = pessoaController.update(id, pessoaEdited);

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        verify(pessoaService, times(1)).findById(id);
        verify(pessoaService, never()).save(any(Pessoa.class));
    }


    @Test
    void deleteWhenPersonExistsTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Sarah");

        when(pessoaService.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        ResponseEntity<Void> response = pessoaController.delete(pessoa.getId());

        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        verify(pessoaService, times(1)).deleteById(pessoa.getId());
    }

    @Test
    void deleteWhenPersonNotExistsTest() {
        Long id = 99L;

        ResponseEntity<Void> response = pessoaController.delete(id);

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        verify(pessoaService, never()).deleteById(id);
    }

    @Test
    void getCSVTest() throws Exception {
        MockitoAnnotations.openMocks(this);

        doAnswer(invocation -> {
            HttpServletResponse response = invocation.getArgument(0);

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=person-report.csv");

            response.getWriter().write(getCsvString());

            return null;
        }).when(csvService).getCSV(any(HttpServletResponse.class), anyList());

        MockHttpServletResponse response = new MockHttpServletResponse();

        pessoaController.getCSV(response);
        assertEquals(getCsvString(), response.getContentAsString());
        assertEquals("text/csv", response.getContentType());
        assertEquals("attachment; filename=person-report.csv", response.getHeader("Content-Disposition"));
    }

    private String getCsvString() {
        return "Id,Nome,Telefone,CPF,CEP,Logradouro,Bairro,Municipio,Estado,Numero,Complemento\n" +
                "1,Sarah,41999999999,123123123,89012520,Rua Teste1,Victor Konder,Blumenau,SC,100,AP 200\n" +
                "2,Lucas,41988888888,321321321,89012520,Rua Teste2,Victor Konder,Blumenau,SC,101,AP 201\n";
    }

}
