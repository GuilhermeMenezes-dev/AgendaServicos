package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;

@Entity
public class TipoAcesso {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Informe a descrição.")
    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres.")
    private String descricao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

}
