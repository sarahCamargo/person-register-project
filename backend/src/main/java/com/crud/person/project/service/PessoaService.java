package com.crud.person.project.service;

import com.crud.person.project.model.Pessoa;
import com.crud.person.project.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findById(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa save(Pessoa person) {
        return pessoaRepository.save(person);
    }

    public void deleteById(Long id) {
        pessoaRepository.deleteById(id);
    }

    public Optional<Pessoa> findByCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }
}
