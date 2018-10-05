package com.marcos.desafioprog.desafioprog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String cpf;
    private String nome;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Cliente() {
    }

    public Cliente(Integer id, String cpf, String nome, String dataCriacao, Conta idConta) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataCriacao = LocalDate.parse(dataCriacao, dateTimeFormatter);
        this.conta  = idConta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Conta getIdConta() {
        return this.conta;
    }

    public void setIdConta(Conta idConta) {
        this.conta = idConta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId()) &&
                Objects.equals(getCpf(), cliente.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf());
    }
}
