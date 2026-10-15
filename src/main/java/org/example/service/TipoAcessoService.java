package org.example.service;

import org.example.model.TipoAcesso;
import org.example.repository.TipoAcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoAcessoService {
    private final TipoAcessoRepository repository;

    public TipoAcessoService(TipoAcessoRepository repository) {
        this.repository = repository;
    }

    public List<TipoAcesso> findAll() { return repository.findAll(); }
    public Optional<TipoAcesso> findOne(Long id) { return repository.findById(id); }
    public TipoAcesso save(TipoAcesso obj) { return repository.save(obj); }
    public void delete(Long id) { repository.deleteById(id); }
}
