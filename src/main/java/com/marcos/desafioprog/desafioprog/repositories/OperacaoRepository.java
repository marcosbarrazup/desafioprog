package com.marcos.desafioprog.desafioprog.repositories;

import com.marcos.desafioprog.desafioprog.domain.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Integer> {

    public Set<Operacao> findByContaOrigem(Integer id);
    public Set<Operacao> findByContaDestino(Integer id);
}
