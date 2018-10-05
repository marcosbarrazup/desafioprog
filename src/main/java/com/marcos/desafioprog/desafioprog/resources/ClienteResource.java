package com.marcos.desafioprog.desafioprog.resources;

import com.marcos.desafioprog.desafioprog.domain.Cliente;
import com.marcos.desafioprog.desafioprog.dto.ClienteDTO;
import com.marcos.desafioprog.desafioprog.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer  id){

        Cliente obj = clienteService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/inserir-cliente", method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestBody Cliente obj) {

        obj = clienteService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromPath("localhost:8080/clientes").path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body("Cliente inserido com sucesso!");


    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Cliente obj, @PathVariable Integer id) {
        obj.setId(id);
        obj = clienteService.update(obj);
        return ResponseEntity.ok().body("Cliente atualizado!");

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.ok().body("Cliente e conta associada deletados!");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity <List<ClienteDTO>> findAll(){
        List<Cliente> list = clienteService.findAll();
        List<ClienteDTO> listDto =  list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }


}
