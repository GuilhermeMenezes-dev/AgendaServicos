package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;

@Entity
public class Servico {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Informe o nome do serviço.")
    @Size(max = 100, message = "Máximo de 100 caracteres.")
    private String nome;

    @NotNull(message = "Informe o valor.")
    @Positive(message = "O valor do serviço deve ser maior que zero.")
    private Double valor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

}
