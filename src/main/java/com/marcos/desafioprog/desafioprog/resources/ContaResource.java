package com.marcos.desafioprog.desafioprog.resources;

import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.domain.Operacao;
import com.marcos.desafioprog.desafioprog.dto.ContaDTO;
import com.marcos.desafioprog.desafioprog.enums.TipoOperacao;
import com.marcos.desafioprog.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.desafioprog.exceptions.InsufficientBalanceException;
import com.marcos.desafioprog.desafioprog.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/contas")
public class ContaResource {
    @Autowired
    private ContaService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity <List<ContaDTO>> findAll(){
        List<Conta> list = service.findAll();
        List<ContaDTO> listDto =  list.stream().map(obj -> new ContaDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer  id){

        Conta obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/{origem}/transfer/{destino}", method = RequestMethod.POST)
    public ResponseEntity<?> transfer (@PathVariable Integer origem, @RequestBody Operacao operacao, @PathVariable Integer destino){

        if(origem ==  destino) throw new ExistentAccountException("Não é possível transferir para a mesma conta!");
        operacao.setContaOrigem(service.find(origem));
        operacao.setContaDestino(service.find(destino));

        service.transfer(operacao);
        return ResponseEntity.ok().body("Transferência Aceita!");
    }

    @RequestMapping(value = "/{id}/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> deposit (@PathVariable Integer id,  @RequestBody Operacao operacao){


        operacao.setContaOrigem(service.find(id));
        operacao.setContaDestino(service.find(id));

        Operacao obj = service.deposit(operacao);

        URI uri = ServletUriComponentsBuilder.fromPath("localhost:8080/depositos").path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body("Depósito concluído!");
    }


    @RequestMapping(value = "/{id}/withdraw",  method = RequestMethod.POST)
    public ResponseEntity<?> withdraw(@PathVariable Integer id, @RequestBody Operacao operacao){

        operacao.setContaOrigem(service.find(id));
        operacao.setContaDestino(service.find(id));

        Boolean result = service.withdraw(operacao);

        if(result) return ResponseEntity.ok().body("Saque efetuado!");
        throw new InsufficientBalanceException("Saldo Insuficiente!");
    }

    @RequestMapping(value = "/{id}/balance", method = RequestMethod.GET)
    public String balance(@PathVariable Integer  id){

        Conta obj = service.find(id);
        return ("ID: " + id
                + "\nSaldo: R$"
                + obj.getSaldo()
        );
    }


    @RequestMapping(value = "/{id}/extrato", method = RequestMethod.GET)
    public ResponseEntity<?> extrato(@PathVariable Integer id){

        Set<Operacao> set = service.extrato(id);
        return ResponseEntity.ok().body(set);

    }
}
