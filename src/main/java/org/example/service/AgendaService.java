package org.example.service;

import org.example.exception.RegraNegocioException;
import org.example.model.Agenda;
import org.example.repository.AgendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendaService {
    private final AgendaRepository repository;

    public AgendaService(AgendaRepository repository) {
        this.repository = repository;
    }

    public List<Agenda> findAll() { return repository.findAll(); }

    public Optional<Agenda> findOne(Long id) { return repository.findById(id); }

    public long count() { return repository.count(); }

    public double faturamentoPrevisto() {
        return repository.findAll().stream()
                .filter(a -> a.getServico() != null && a.getServico().getValor() != null)
                .mapToDouble(a -> a.getServico().getValor())
                .sum();
    }

    // RN10
    public List<Agenda> pesquisar(Long cliente, Long servico, LocalDate de, LocalDate ate) {
        LocalDateTime inicio = de == null ? null : de.atStartOfDay();
        LocalDateTime fim = ate == null ? null : ate.atTime(LocalTime.MAX);
        return repository.findAll().stream()
                .filter(a -> cliente == null || a.getCliente().getId().equals(cliente))
                .filter(a -> servico == null || a.getServico().getId().equals(servico))
                .filter(a -> inicio == null || !a.getData().isBefore(inicio))
                .filter(a -> fim == null || !a.getData().isAfter(fim))
                .sorted(Comparator.comparing(Agenda::getData))
                .collect(Collectors.toList());
    }

    public Agenda save(Agenda agenda) {
        // RN04
        if (agenda.getCliente() == null || agenda.getCliente().getId() == null) {
            throw new RegraNegocioException("O agendamento deve estar associado a um cliente cadastrado.");
        }
        // RN01
        if (agenda.getData() == null || agenda.getData().isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("Não é permitido agendar com data anterior à data atual.");
        }
        // RN02
        Long idAtual = agenda.getId() == null ? -1L : agenda.getId();
        if (repository.existsByUsuarioIdAndDataAndIdNot(agenda.getUsuario().getId(), agenda.getData(), idAtual)) {
            throw new RegraNegocioException("Este usuário já possui um agendamento nessa data e horário.");
        }
        return repository.save(agenda);
    }

    public void delete(Long id) { repository.deleteById(id); }
}
