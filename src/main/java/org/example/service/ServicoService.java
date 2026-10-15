package org.example.service;

import org.example.exception.RegraNegocioException;
import org.example.model.Servico;
import org.example.repository.AgendaRepository;
import org.example.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {
    private final ServicoRepository repository;
    private final AgendaRepository agendaRepository;

    public ServicoService(ServicoRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    public List<Servico> findAll() { return repository.findAll(); }

    public Optional<Servico> findOne(Long id) { return repository.findById(id); }

    public Servico save(Servico servico) {
        // RN03
        if (servico.getValor() == null || servico.getValor() <= 0) {
            throw new RegraNegocioException("O valor do serviço deve ser maior que zero.");
        }
        return repository.save(servico);
    }

    public void delete(Long id) {
        // RN05
        if (agendaRepository.existsByServicoId(id)) {
            throw new RegraNegocioException("Não é possível excluir: o serviço está vinculado a agendamentos.");
        }
        repository.deleteById(id);
    }
}
