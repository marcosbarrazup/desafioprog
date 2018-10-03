package com.marcos.desafioprog.desafioprog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;
    private Double saldo;

    @JsonIgnore
    @OneToOne(mappedBy = "conta")
    private Cliente clientes;

    @JsonIgnore
    @OneToMany(mappedBy = "contaOrigem")
    private List<Operacao>  operacoesOrigem;

    @JsonIgnore
    @OneToMany(mappedBy = "contaDestino")
    private List<Operacao>  operacoesDestino;


    public Conta() {
    }

    public Conta(Integer id, LocalDate dataCriacao, Double saldo) {
        this.id = id;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataCriacao = dataCriacao;
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

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
        this.clientes = clientes;
    }

    public List<Operacao> getOperacoesOrigem() {
        return operacoesOrigem;
    }

    public void setOperacoesOrigem(List<Operacao> operacoesOrigem) {
        this.operacoesOrigem = operacoesOrigem;
    }

    public List<Operacao> getOperacoesDestino() {
        return operacoesDestino;
    }

    public void setOperacoesDestino(List<Operacao> operacoesDestino) {
        this.operacoesDestino = operacoesDestino;
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

    public void deposita(Double valor) {
        this.saldo+= valor;
    }

    public boolean saca(Double valor) {
        if(this.saldo-valor>=0.0) {
            this.saldo -=valor;
            return true;
        }
        return false;
    }
}
