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
    public ResponseEntity <List<AccountDTO>> findAll(){
        List<Account> list = service.findAll();
        List<AccountDTO> listDto =  list.stream().map(obj -> new AccountDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer  id){

        Account obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/{source}/transfer/{destination}", method = RequestMethod.POST)
    public ResponseEntity<?> transfer (@PathVariable Integer source, @RequestBody Operation operation, @PathVariable Integer destination){

        if(source ==  destination) throw new ExistentAccountException("Can not transfer to the same account!");
        operation.setSourceAccount(service.find(source));
        operation.setDestinationAccount(service.find(destination));

        Operation result = service.transfer(operation);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/{id}/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> deposit (@PathVariable Integer id,  @RequestBody Operation operation){


        operation.setDestinationAccount(service.find(id));

        Operation obj = service.deposit(operation);


        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(value = "/{id}/withdraw",  method = RequestMethod.POST)
    public ResponseEntity<?> withdraw(@PathVariable Integer id, @RequestBody Operation operation){

        operation.setSourceAccount(service.find(id));


        Operation result = service.withdraw(operation);

        return ResponseEntity.ok().body(result);

    }

    @RequestMapping(value = "/{id}/balance", method = RequestMethod.GET)
    public ResponseEntity<Account> balance(@PathVariable Integer  id){

        Account obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(value = "/{id}/statement", method = RequestMethod.GET)
    public ResponseEntity<?> statement(@PathVariable Integer id){

        OperationDTO saldo =  new OperationDTO(id,service.find(id).getBalance());
        List<OperationDTO> list = service.statement(id);

        list.add(saldo);

        return  ResponseEntity.ok().body(list);


    }
}
