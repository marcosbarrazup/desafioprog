package com.marcos.desafioprog.desafioprog.repositories;

import com.marcos.desafioprog.desafioprog.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public Cliente findByCpf(String cpf);

}
