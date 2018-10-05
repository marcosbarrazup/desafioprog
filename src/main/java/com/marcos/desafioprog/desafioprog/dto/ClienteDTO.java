package com.marcos.desafioprog.desafioprog.dto;

import com.marcos.desafioprog.desafioprog.domain.Cliente;

import java.time.LocalDate;
import java.util.Objects;

public class ClienteDTO {

    private Integer id;
    private String nome;
    private LocalDate dataCriacao;
    private String cpf;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente c){
        this.id = c.getId();
        this.nome = c.getNome();
        this.dataCriacao = c.getDataCriacao();
        this.cpf = c.getCpf();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO that = (ClienteDTO) o;
        return Objects.equals(getId(), that.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
