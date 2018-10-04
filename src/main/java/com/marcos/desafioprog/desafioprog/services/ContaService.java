package com.marcos.desafioprog.desafioprog.services;

import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.domain.Operacao;
import com.marcos.desafioprog.desafioprog.exceptions.InsufficientBalanceException;
import com.marcos.desafioprog.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.desafioprog.repositories.ContaRepository;
import com.marcos.desafioprog.desafioprog.repositories.OperacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if(operacao.getContaOrigem().saca(operacao.getValor())) {
            operacao.getContaDestino().deposita(operacao.getValor());
            return operacaoRepository.save(operacao);
        }
            throw new InsufficientBalanceException("Saldo Insuficiente!");
    }

    public Operacao deposit(Operacao operacao) {

        Conta conta = operacao.getContaOrigem();
        conta.deposita(operacao.getValor());

        return operacaoRepository.save(operacao);
    }

    public boolean withdraw(Operacao operacao) {


        if(operacao.getContaOrigem().saca(operacao.getValor())) {
            operacaoRepository.save(operacao);
            return true;
        }
        return  false;
    }
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Set<Operacao> extrato(Integer id) {
        Conta conta = find(id);
        Set<Operacao> set = operacaoRepository.
                findAll().
                stream().
                filter(c ->
                c.getContaOrigem().getId() == id || c.getContaDestino().getId() == id).collect(Collectors.toSet());

        return  set;
    }
}
