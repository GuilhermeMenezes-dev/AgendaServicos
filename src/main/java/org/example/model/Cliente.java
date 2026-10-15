package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Informe o nome do cliente.")
    @Size(max = 100, message = "Máximo de 100 caracteres.")
    private String nome;

    @NotBlank(message = "Informe o telefone.")
    @Size(max = 20, message = "Máximo de 20 caracteres.")
    private String telefone;

    @NotBlank(message = "Informe o e-mail.")
    @Email(message = "E-mail inválido.")
    @Column(unique = true)
    private String email;

    @ManyToOne
    @NotNull(message = "Selecione um endereço.")
    private Endereco endereco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

}
