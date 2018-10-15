package com.marcos.desafioprog.resources;

import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.domain.Operation;
import com.marcos.desafioprog.dto.AccountDTO;
import com.marcos.desafioprog.dto.OperationDTO;
import com.marcos.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/accounts")
public class AccountResource {

    @Autowired
    private AccountService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<AccountDTO> findAll(){
        List<Account> list = service.findAll();
        List<AccountDTO> listDto =  list.stream().map(obj -> new AccountDTO(obj)).collect(Collectors.toList());

        return listDto;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account find(@PathVariable Integer  id){

        Account account = service.find(id);
        return account;
    }

    @RequestMapping(value = "/{source}/transfer/{destination}", method = RequestMethod.POST)
    public OperationDTO transfer (@PathVariable Integer source, @RequestBody Operation operation, @PathVariable Integer destination){

        if(source ==  destination) throw new ExistentAccountException("Can not transfer to the same account!");
        operation.setSourceAccount(service.find(source));
        operation.setDestinationAccount(service.find(destination));

        OperationDTO result = new OperationDTO(service.transfer(operation));

        return result;
    }

    @RequestMapping(value = "/{id}/deposit", method = RequestMethod.POST)
    public Operation deposit (@PathVariable Integer id,  @RequestBody Operation operation){


        operation.setDestinationAccount(service.find(id));

        Operation result = service.deposit(operation);


        return result;
    }


    @RequestMapping(value = "/{id}/withdraw",  method = RequestMethod.POST)
    public Operation withdraw(@PathVariable Integer id, @RequestBody Operation operation){

        operation.setSourceAccount(service.find(id));


        Operation result = service.withdraw(operation);

        return result;

    }

    @RequestMapping(value = "/{id}/balance", method = RequestMethod.GET)
    public Account balance(@PathVariable Integer  id){

        Account account = service.find(id);
        return account;
    }


    @RequestMapping(value = "/{id}/statement", method = RequestMethod.GET)
    public List<OperationDTO> statement(@PathVariable Integer id){

        OperationDTO saldo =  new OperationDTO(id,service.find(id).getBalance());
        List<OperationDTO> list = service.statement(id);

        list.add(saldo);

        return list;


    }
}
