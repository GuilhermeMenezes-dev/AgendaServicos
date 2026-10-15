package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Informe o login.")
    @Size(max = 50, message = "Máximo de 50 caracteres.")
    @Column(unique = true)
    private String usuario;

    @NotBlank(message = "Informe a senha.")
    private String senha;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dtCadastro;

    @ManyToOne
    @NotNull(message = "Selecione um tipo de acesso.")
    private TipoAcesso tipoAcesso;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDate getDtCadastro() { return dtCadastro; }
    public void setDtCadastro(LocalDate dtCadastro) { this.dtCadastro = dtCadastro; }

    public TipoAcesso getTipoAcesso() { return tipoAcesso; }
    public void setTipoAcesso(TipoAcesso tipoAcesso) { this.tipoAcesso = tipoAcesso; }

    public String getDtCadastroFormatada() {
        return dtCadastro == null ? "" : dtCadastro.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
