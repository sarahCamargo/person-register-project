package com.crud.person.project.controller;

import com.crud.person.project.exception.CpfAlreadyRegisteredException;
import com.crud.person.project.exception.ValidationException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.csv.CSVService;
import com.crud.person.project.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import utils.CPFValidation;
import utils.NumberValidation;

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
        pessoa.setCpf("95550964066");

        when(pessoaService.findByCpf(pessoa.getCpf())).thenReturn(Optional.empty());
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa savedPessoa = pessoaController.save(pessoa);

        assertNotNull(savedPessoa);
        verify(pessoaService, times(1)).save(pessoa);

    }

    @Test
    void saveCpfExistsTest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf("95550964066");

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
        pessoa1.setCpf("95550964066");

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        pessoa2.setNome("Lucas");
        pessoa2.setCpf("93570033040");

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
        pessoa.setCpf("95550964066");

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
        pessoa.setCpf("95550964066");

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
        pessoa.setCpf("95550964066");
        pessoa.setTelefone("41999999999");

        Pessoa pessoaEdited = new Pessoa();
        pessoaEdited.setNome("Sarah Camargo");
        pessoaEdited.setCpf("93570033040");
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
        pessoaEdited.setCpf("95550964066");
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
    void saveCpfValid() {
        assertDoesNotThrow(() -> CPFValidation.validaCpf("95550964066"));
    }

    @Test
    void saveCpfNotValid() {
        String cpfInvalido = "123123";
        assertThrows(ValidationException.class, () -> CPFValidation.validaCpf(cpfInvalido));
    }

    @Test
    void saveNumberValid() {
        assertDoesNotThrow(() -> NumberValidation.validateInteger("12"));
        assertDoesNotThrow(() -> NumberValidation.validateInteger(""));
        assertDoesNotThrow(() -> NumberValidation.validateInteger(null));
    }

    @Test
    void saveNumberNotValid() {
        assertThrows(ValidationException.class, () -> NumberValidation.validateInteger("teste"));
    }

}
