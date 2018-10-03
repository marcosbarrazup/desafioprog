package com.marcos.desafioprog.desafioprog.repositories;

import com.marcos.desafioprog.desafioprog.domain.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Integer> {


}
