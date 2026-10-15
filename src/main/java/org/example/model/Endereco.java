package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;

@Entity
public class Endereco {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Informe o logradouro.")
    @Size(max = 150, message = "Máximo de 150 caracteres.")
    private String logradouro;

    @NotBlank(message = "Informe a UF.")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 letras.")
    private String uf;

    @NotBlank(message = "Informe o estado.")
    @Size(max = 60, message = "Máximo de 60 caracteres.")
    private String estado;

    @NotBlank(message = "Informe o CEP.")
    @Size(max = 9, message = "Máximo de 9 caracteres.")
    private String cep;

    @NotBlank(message = "Informe a cidade.")
    @Size(max = 100, message = "Máximo de 100 caracteres.")
    private String cidade;

    @NotNull(message = "Informe o número.")
    @Min(value = 0, message = "O número não pode ser negativo.")
    private Integer numero;

    @Size(max = 100, message = "Máximo de 100 caracteres.")
    private String complemento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

}
