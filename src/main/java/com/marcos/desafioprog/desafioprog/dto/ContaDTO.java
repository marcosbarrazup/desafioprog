package com.marcos.desafioprog.desafioprog.dto;

import com.marcos.desafioprog.desafioprog.domain.Conta;

import java.time.LocalDate;
import java.util.Objects;

public class ContaDTO {

    private Integer id;

    private LocalDate dataCriacao;

    private Double saldo;

    public ContaDTO() {
    }

    public ContaDTO(Conta conta) {
        this.id = conta.getId();
        this.dataCriacao =conta.getDataCriacao();
        this.saldo = conta.getSaldo();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaDTO contaDTO = (ContaDTO) o;
        return Objects.equals(getId(), contaDTO.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
