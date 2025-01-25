package com.crud.person.project.controller;

import com.crud.person.project.exception.CpfAlreadyRegisteredException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.CSVService;
import com.crud.person.project.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    private CSVService csvService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/")
    public List<Pessoa> findAll() {
        return pessoaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        return pessoaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Pessoa save(@RequestBody Pessoa pessoa) {

        if (pessoaService.findByCpf(pessoa.getCpf()).isPresent()) {
           throw new CpfAlreadyRegisteredException("CPF já cadastrado no sistema.");
        };

        return pessoaService.save(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pessoaEdited) {
        return pessoaService.findById(id)
                .map(person -> {
                    person.setNome(pessoaEdited.getNome());
                    person.setTelefone(pessoaEdited.getTelefone());
                    person.setCpf(pessoaEdited.getCpf());
                    person.setCep(pessoaEdited.getCep());
                    person.setBairro(pessoaEdited.getBairro());
                    person.setMunicipio(pessoaEdited.getMunicipio());
                    person.setEstado(pessoaEdited.getEstado());
                    person.setNumero(pessoaEdited.getNumero());
                    person.setComplemento(pessoaEdited.getComplemento());
                    return ResponseEntity.ok(pessoaService.save(person));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (pessoaService.findById(id).isPresent()) {
            pessoaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/csv/download")
    public void getCSV(HttpServletResponse response) {
        List<Pessoa> pessoas = pessoaService.findAll();
        csvService.getCSV(response, pessoas);
    }
}
