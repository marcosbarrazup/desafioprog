package com.marcos.desafioprog.repositories;

import com.marcos.desafioprog.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    public Set<Operation> findBySourceAccount(Integer id);
    public Set<Operation> findByDestinationAccount(Integer id);
}
