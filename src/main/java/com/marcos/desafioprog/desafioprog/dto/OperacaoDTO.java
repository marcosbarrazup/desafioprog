package com.marcos.desafioprog.desafioprog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.domain.Operacao;
import com.marcos.desafioprog.desafioprog.enums.TipoOperacao;

import java.time.LocalDateTime;
import java.util.Objects;

public class OperacaoDTO {

    private Integer id;
    private Double valor;
    private Integer tipoOperacao;
    private LocalDateTime dataHora;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer contaOrigem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer contaDestino;


    public OperacaoDTO(Operacao operacao) {
        this.id = operacao.getId();
        this.valor = operacao.getValor();
        this.tipoOperacao = operacao.getTipoOperacao().getCod();
        this.dataHora = operacao.getDataHora();
        if (TipoOperacao.DEPOSITO.getDescricao() == operacao.getTipoOperacao().getDescricao() ||
                TipoOperacao.TRANSFERENCIA.getDescricao() == operacao.getTipoOperacao().getDescricao()
        )
            this.contaDestino = operacao.getContaDestino().getId();

        if(TipoOperacao.SAQUE.getDescricao() == operacao.getTipoOperacao().getDescricao() ||
                TipoOperacao.TRANSFERENCIA.getDescricao() == operacao.getTipoOperacao().getDescricao())
        this.contaOrigem = operacao.getContaOrigem().getId();


    }

    public OperacaoDTO() {
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperacaoDTO that = (OperacaoDTO) o;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoOperacao getTipoOperacao() {
        return TipoOperacao.toEnum(this.tipoOperacao);
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao.getCod();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Integer contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Integer getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Integer contaDestino) {
        this.contaDestino = contaDestino;
    }
}
