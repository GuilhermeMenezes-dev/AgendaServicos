package org.example.repository;

import org.example.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    boolean existsByServicoId(Long servicoId);

    boolean existsByClienteId(Long clienteId);

    boolean existsByUsuarioIdAndDataAndIdNot(Long usuarioId, LocalDateTime data, Long id);
}
