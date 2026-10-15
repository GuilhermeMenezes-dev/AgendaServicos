package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;

@Entity
public class Agenda {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Informe a descrição.")
    @Size(max = 250, message = "Máximo de 250 caracteres.")
    private String descricao;

    @ManyToOne
    @NotNull(message = "Selecione um serviço.")
    private Servico servico;

    @NotNull(message = "Informe a data e hora.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime data;

    @ManyToOne
    @NotNull(message = "Selecione um cliente cadastrado.")
    private Cliente cliente;

    @ManyToOne
    @NotNull(message = "Selecione um usuário.")
    private Usuario usuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getDataFormatada() {
        return data == null ? "" : data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
