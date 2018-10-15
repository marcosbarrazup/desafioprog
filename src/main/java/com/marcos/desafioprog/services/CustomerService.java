package com.marcos.desafioprog.services;

import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.repositories.CustomerRepository;
import com.marcos.desafioprog.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Customer find(Integer id) throws ObjectNotFoundException {
        Optional<Customer> obj = customerRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Customer not found! Id: " + id
                + ", Tipo: " + Customer.class.getName()));
    }

    public Customer insert(Customer obj) throws ExistentAccountException {

        if (customerRepository.findByCpf(obj.getCpf()) == null) {
            obj.setId(null);
            obj.setAccount(null);
            obj.setCreationDate(LocalDate.now());
            Account account = new Account(null, obj.getCreationDate(), 0.0);
            account = accountRepository.saveAndFlush(account);
            if (account != null) {
                obj.setAccount(account);
                return customerRepository.saveAndFlush(obj);
            }
            return null;
        }

        throw new ExistentAccountException("CPF Already registered!");
    }

    public Customer update(Customer obj) {


        if (customerRepository.findByCpf(obj.getCpf()) == null || customerRepository.findByCpf(obj.getCpf()).getId() == obj.getId()) {
            Customer existente = find(obj.getId());
            obj.setCreationDate(existente.getCreationDate());
            obj.setAccount(existente.getAccount());

            return customerRepository.saveAndFlush(obj);
        }

        throw new ExistentAccountException("CPF Already registered!");
    }

    public void delete(Integer id) {
        Customer customer = find(id);
        customerRepository.deleteById(id);
        accountRepository.deleteById(customer.getAccount().getId());

    }

    public List<Customer> findAll() {

        List<Customer> result = customerRepository.findAll();
        if(result.size()>0) return result;
        throw new ObjectNotFoundException("No customers found!");
    }

}
