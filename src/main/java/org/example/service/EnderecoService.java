package org.example.service;

import org.example.model.Endereco;
import org.example.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<Endereco> findAll() { return repository.findAll(); }
    public Optional<Endereco> findOne(Long id) { return repository.findById(id); }
    public Endereco save(Endereco obj) { return repository.save(obj); }
    public void delete(Long id) { repository.deleteById(id); }
}
