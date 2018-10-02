package com.marcos.desafioprog.desafioprog.repositories;

import com.marcos.desafioprog.desafioprog.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {




}
