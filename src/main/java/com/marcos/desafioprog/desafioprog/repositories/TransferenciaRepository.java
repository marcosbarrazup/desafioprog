package com.marcos.desafioprog.desafioprog.repositories;

import com.marcos.desafioprog.desafioprog.domain.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {


}
