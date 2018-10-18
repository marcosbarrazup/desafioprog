package com.marcos.desafioprog.controllers;

import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.dto.CustomerDTO;
import com.marcos.desafioprog.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/{cpf}", method = RequestMethod.GET)
    public Customer find(@PathVariable String  cpf){

        Customer customer = customerService.find(cpf);
        return customer;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO insert(@Valid @RequestBody Customer customer) {

        customer = customerService.insert(customer);

        return new CustomerDTO(customer);
    }

    @RequestMapping(value = "/{cpf}", method = RequestMethod.PUT)
    public CustomerDTO update(@Valid @RequestBody Customer customer, @PathVariable String cpf) {

        customer = customerService.update(customer, cpf);
        return new CustomerDTO(customer);


    }

    @RequestMapping(value = "/{cpf}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String cpf) {
        customerService.delete(cpf);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> findAll(){

        List<Customer> list = customerService.findAll();
        List<CustomerDTO> listDto =  list.stream().map(obj -> new CustomerDTO(obj)).collect(Collectors.toList());
        return listDto;
    }


}
