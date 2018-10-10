package com.marcos.desafioprog.services;

import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.domain.Operation;
import com.marcos.desafioprog.dto.OperationDTO;
import com.marcos.desafioprog.enums.OperationType;
import com.marcos.desafioprog.exceptions.InsufficientBalanceException;
import com.marcos.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.repositories.AccountRepository;
import com.marcos.desafioprog.repositories.OperationRepository;
import com.marcos.desafioprog.services.base.DesafioProgBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class AccountServiceTest extends DesafioProgBaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private AccountService accountService;

    private Operation operation;
    private Account account1;
    private Account account2;

    @Before
    public void setup() {
        account1 = new Account();
        account2 = new Account();
        account1.setId(1);
        account1.setBalance(0.0);
        account2.setId(2);
        account2.setBalance(0.0);
        operation = new Operation();
        operation.setDestinationAccount(account2);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setId(1);
        operation.setSourceAccount(account1);
        operation.setDateHour(LocalDateTime.now());
        operation.setValue(100.0);


    }

    @Test
    public void findFound() {

        Account account = new Account();
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        Account result = accountService.find(10);
        assertNotNull(result);

    }

    @Test
    public void findNullAccount() {

        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        Account result = accountService.find(10);
        assertNull(result);
    }

    @Test
    public void transferOk() {
        operation.setValue(100.0);
        account1.setBalance(100.0);
        operation.setSourceAccount(account1);
        operation.setDestinationAccount(account2);

        when(operationRepository.saveAndFlush(any(Operation.class))).thenReturn(operation);
        Operation result = accountService.transfer(operation);

        assertNotNull(result);

    }

    @Test
    public void transferInsufficient() {
        operation.setValue(150.0);
        account1.setBalance(100.0);
        operation.setSourceAccount(account1);
        operation.setDestinationAccount(account2);
        thrown.expect(InsufficientBalanceException.class);

        Operation result = accountService.transfer(operation);

    }


    @Test
    public void depositOk(){

        when(operationRepository.saveAndFlush(any(Operation.class))).thenReturn(operation);
        Operation result = accountService.deposit(operation);
        assertNotNull(result);
    }


    @Test
    public void withdrawOk(){
        operation.setValue(100.0);
        account1.setBalance(100.0);
        operation.setSourceAccount(account1);
        operation.setDestinationAccount(account1);
        when(operationRepository.saveAndFlush(any(Operation.class))).thenReturn(operation);
        Operation result = accountService.withdraw(operation);
        assertNotNull(result);
    }
    @Test
    public void withdrawInsufficient(){
        operation.setValue(150.0);
        account1.setBalance(100.0);
        operation.setSourceAccount(account1);
        operation.setDestinationAccount(account1);
        thrown.expect(InsufficientBalanceException.class);
        accountService.withdraw(operation);

    }

    @Test
    public void findAllFound(){
        List<Account> list = new ArrayList<>();
        list.add(account1);
        list.add(account2);
        when(accountRepository.findAll()).thenReturn(list);
        List<Account> result = accountService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    public void findAllNotFound(){
        List<Account> list = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(list);
        thrown.expect(ObjectNotFoundException.class);
        accountService.findAll();

    }

    @Test
    public void statementHasAOperation(){
        List<Operation> list = new ArrayList<>();
        list.add(operation);
        when(operationRepository.findAll()).thenReturn(list);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account1));
        List<OperationDTO> result = accountService.statement(1);
        assertNotNull(result);
        assertEquals(1,result.size());
    }
    @Test
    public void statementHasNotAOperation(){
        List<Operation> list = new ArrayList<>();
        when(operationRepository.findAll()).thenReturn(list);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account1));
        thrown.expect(ObjectNotFoundException.class);
        accountService.statement(1);

    }

    @Test
    public void statementInvalidAccount(){
        List<Operation> list = new ArrayList<>();
        list.add(operation);
        when(operationRepository.findAll()).thenReturn(list);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        List<OperationDTO> result = accountService.statement(1);

    }


}