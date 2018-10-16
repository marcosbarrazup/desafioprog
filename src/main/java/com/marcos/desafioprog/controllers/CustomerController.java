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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer find(@PathVariable Integer  id){

        Customer customer = customerService.find(id);
        return customer;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer insert(@Valid @RequestBody Customer customer) {

        customer = customerService.insert(customer);
        return customer;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Customer update(@Valid @RequestBody Customer customer, @PathVariable Integer id) {
        customer.setId(id);
        customer = customerService.update(customer);
        return customer;


    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        customerService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> findAll(){

        List<Customer> list = customerService.findAll();
        List<CustomerDTO> listDto =  list.stream().map(obj -> new CustomerDTO(obj)).collect(Collectors.toList());
        return listDto;
    }


}
