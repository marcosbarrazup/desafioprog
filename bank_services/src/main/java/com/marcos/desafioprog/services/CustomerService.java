package com.marcos.desafioprog.services;

import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.repositories.AccountRepository;
import com.marcos.desafioprog.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Customer find(String cpf) throws ObjectNotFoundException {
        Customer obj = customerRepository.findByCpf(cpf);
        if(obj !=null) return obj;
        throw new ObjectNotFoundException("Customer not found! Id: " + cpf
                + ", Tipo: " + Customer.class.getName());

    }

    public Customer insert(Customer obj) throws ExistentAccountException {

        if (customerRepository.findByCpf(obj.getCpf()) == null) {
            obj.setId(null);
            obj.setAccount(null);
            Account account = new Account(null, LocalDate.now(), 0.0);
            account = accountRepository.saveAndFlush(account);
            if (account != null) {
                obj.setAccount(account);
                return customerRepository.saveAndFlush(obj);
            }
            return null;
        }

        throw new ExistentAccountException("CPF Already registered!");
    }

    public Customer update(Customer obj, String cpf) {


        Customer existente  = customerRepository.findByCpf(cpf);
        if(existente==null) throw new ObjectNotFoundException("Customer not Found!");

        if (customerRepository.findByCpf(obj.getCpf()) == null || customerRepository.findByCpf(obj.getCpf()).getId() == existente.getId()) {

            obj.setId(existente.getId());
            obj.setAccount(existente.getAccount());

            return customerRepository.saveAndFlush(obj);
        }

        throw new ExistentAccountException("CPF Already registered!");
    }

    public void delete(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf);
        if(customer == null) throw new ObjectNotFoundException("Customer not Found!");
        customerRepository.deleteById(customer.getId());
        accountRepository.deleteById(customer.getAccount().getId());

    }

    public List<Customer> findAll() {

        List<Customer> result = customerRepository.findAll();
        if(result.size()>0) return result;
        throw new ObjectNotFoundException("No customers found!");
    }

}
