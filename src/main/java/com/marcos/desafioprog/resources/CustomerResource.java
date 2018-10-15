package com.marcos.desafioprog.resources;

import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.dto.CustomerDTO;
import com.marcos.desafioprog.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer  id){

        Customer obj = customerService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@Valid @RequestBody Customer obj) {


        obj = customerService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromPath("localhost:8080/clientes").path("/{id}").buildAndExpand(obj.getId()).toUri();
        if(obj!=null) return ResponseEntity.created(uri).body(obj);
        return ResponseEntity.badRequest().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@Valid @RequestBody Customer obj, @PathVariable Integer id) {
        obj.setId(id);
        obj = customerService.update(obj);
        return ResponseEntity.ok().body(obj);


    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity <List<CustomerDTO>> findAll(){
        List<Customer> list = customerService.findAll();
        List<CustomerDTO> listDto =  list.stream().map(obj -> new CustomerDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }


}
