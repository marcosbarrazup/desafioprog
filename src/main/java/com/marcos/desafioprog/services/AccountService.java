package com.marcos.desafioprog.services;

import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.domain.Operation;
import com.marcos.desafioprog.dto.OperationDTO;
import com.marcos.desafioprog.enums.OperationType;
import com.marcos.desafioprog.exceptions.InsufficientBalanceException;
import com.marcos.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.repositories.AccountRepository;
import com.marcos.desafioprog.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    public Account find(Integer  id){
        Optional<Account> obj = accountRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id
                + ", Tipo: " + Account.class.getName()));
    }

    public Operation transfer(Operation operation) {

        operation.setId(null);
        operation.setOperationType(OperationType.TRANSFER);
        operation.setDateHour(LocalDateTime.now());

        if(operation.getSourceAccount().withdraw(operation.getValue())) {
            operation.getDestinationAccount().deposit(operation.getValue());
            return operationRepository.saveAndFlush(operation);
        }
            throw new InsufficientBalanceException("Insufficient Balance!");
    }

    public Operation deposit(Operation operation) {

        operation.setId(null);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setDateHour(LocalDateTime.now());
        Account account = operation.getDestinationAccount();
        account.deposit(operation.getValue());

        return operationRepository.saveAndFlush(operation);
    }

    public Operation withdraw(Operation operation) {
        operation.setId(null);
        operation.setOperationType(OperationType.WITHDRAW);
        operation.setDateHour(LocalDateTime.now());

        if(operation.getSourceAccount().withdraw(operation.getValue())) {
            operationRepository.saveAndFlush(operation);
            return operation;
        }
        throw new InsufficientBalanceException("Insufficient Balance!");
    }
    public List<Account> findAll() throws ObjectNotFoundException{

        List<Account> result = accountRepository.findAll();
        if(result.size()>0) return result;
        throw new ObjectNotFoundException("No accounts found!");
    }

    public List<OperationDTO> statement(Integer id) {
        Account account = find(id);
        List <OperationDTO> list = operationRepository.
                findAll().
                stream().
                filter(c ->
                c.getSourceAccount().getId() == id || c.getDestinationAccount().getId() == id)
                .map(c -> new OperationDTO(c)).collect(Collectors.toList());
        if(list.size() == 0) throw new ObjectNotFoundException("No operations found!");

        return  list;
    }
}
