package com.crud.person.project.controller;

import com.crud.person.project.exception.CpfAlreadyRegisteredException;
import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.CPFValidation;
import utils.NumberValidation;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

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

        CPFValidation.validaCpf(pessoa.getCpf());
        NumberValidation.validateInteger(pessoa.getNumero());

        if (pessoaService.findByCpf(pessoa.getCpf()).isPresent()) {
           throw new CpfAlreadyRegisteredException("CPF já cadastrado no sistema.");
        };

        return pessoaService.save(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pessoaEdited) {

        CPFValidation.validaCpf(pessoaEdited.getCpf());
        NumberValidation.validateInteger(pessoaEdited.getNumero());

        return pessoaService.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(pessoaEdited.getNome());
                    pessoa.setTelefone(pessoaEdited.getTelefone());
                    pessoa.setCpf(pessoaEdited.getCpf());
                    pessoa.setCep(pessoaEdited.getCep());
                    pessoa.setLogradouro(pessoaEdited.getLogradouro());
                    pessoa.setBairro(pessoaEdited.getBairro());
                    pessoa.setMunicipio(pessoaEdited.getMunicipio());
                    pessoa.setEstado(pessoaEdited.getEstado());
                    pessoa.setNumero(pessoaEdited.getNumero());
                    pessoa.setComplemento(pessoaEdited.getComplemento());
                    return ResponseEntity.ok(pessoaService.save(pessoa));
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
}
