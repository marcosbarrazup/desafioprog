package com.marcos.desafioprog.desafioprog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private LocalDate dataCriacao;
    private Double saldo;

    @JsonIgnore
    @OneToMany(mappedBy = "conta")
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany(mappedBy = "contaOrigem")
    private List<Transferencia>  transferenciasOrigem;

    @JsonIgnore
    @OneToMany(mappedBy = "contaDestino")
    private List<Transferencia>  transferenciasDestino;




    public Conta(Integer id, String dataCriacao, Double saldo) {
        this.id = id;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataCriacao = LocalDate.parse(dataCriacao, dateTimeFormatter);
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Objects.equals(getId(), conta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
