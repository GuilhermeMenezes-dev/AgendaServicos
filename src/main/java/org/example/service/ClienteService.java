package org.example.service;

import org.example.exception.RegraNegocioException;
import org.example.model.Cliente;
import org.example.repository.AgendaRepository;
import org.example.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final AgendaRepository agendaRepository;

    public ClienteService(ClienteRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    public List<Cliente> findAll() { return repository.findAll(); }

    public Optional<Cliente> findOne(Long id) { return repository.findById(id); }

    public long count() { return repository.count(); }

    public Cliente save(Cliente cliente) {
        // RN07 (formato do e-mail é validado com @Email na entidade)
        Optional<Cliente> existente = repository.findByEmailIgnoreCase(cliente.getEmail());
        if (existente.isPresent() && !Objects.equals(existente.get().getId(), cliente.getId())) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com esse e-mail.");
        }
        return repository.save(cliente);
    }

    public void delete(Long id) {
        // RN06
        if (agendaRepository.existsByClienteId(id)) {
            throw new RegraNegocioException("Não é possível excluir: o cliente possui agendamentos cadastrados.");
        }
        repository.deleteById(id);
    }
}
