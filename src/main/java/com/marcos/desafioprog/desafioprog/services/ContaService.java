package com.marcos.desafioprog.desafioprog.services;

import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.domain.Operacao;
import com.marcos.desafioprog.desafioprog.dto.OperacaoDTO;
import com.marcos.desafioprog.desafioprog.enums.TipoOperacao;
import com.marcos.desafioprog.desafioprog.exceptions.InsufficientBalanceException;
import com.marcos.desafioprog.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.desafioprog.repositories.ContaRepository;
import com.marcos.desafioprog.desafioprog.repositories.OperacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;

    public Conta find(Integer  id){
        Optional<Conta> obj = contaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                + ", Tipo: " + Conta.class.getName()));
    }

    public Operacao transfer(Operacao operacao) {

        operacao.setId(null);
        operacao.setTipoOperacao(TipoOperacao.TRANSFERENCIA);
        operacao.setDataHora(LocalDateTime.now());

        if(operacao.getContaOrigem().saca(operacao.getValor())) {
            operacao.getContaDestino().deposita(operacao.getValor());
            return operacaoRepository.saveAndFlush(operacao);
        }
            throw new InsufficientBalanceException("Saldo Insuficiente!");
    }

    public Operacao deposit(Operacao operacao) {

        operacao.setId(null);
        operacao.setTipoOperacao(TipoOperacao.DEPOSITO);
        operacao.setDataHora(LocalDateTime.now());
        Conta conta = operacao.getContaOrigem();
        conta.deposita(operacao.getValor());

        return operacaoRepository.saveAndFlush(operacao);
    }

    public boolean withdraw(Operacao operacao) {
        operacao.setId(null);
        operacao.setTipoOperacao(TipoOperacao.SAQUE);
        operacao.setDataHora(LocalDateTime.now());

        if(operacao.getContaOrigem().saca(operacao.getValor())) {
            operacaoRepository.saveAndFlush(operacao);
            return true;
        }
        throw new InsufficientBalanceException("Saldo insuficiente!");
    }
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Set<OperacaoDTO> extrato(Integer id) {
        Conta conta = find(id);
        Set<OperacaoDTO> set = operacaoRepository.
                findAll().
                stream().
                filter(c ->
                c.getContaOrigem().getId() == id || c.getContaDestino().getId() == id)
                .map(c -> new OperacaoDTO(c)).collect(Collectors.toSet());

        return  set;
    }
}
