package com.marcos.desafioprog.services;

import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.repositories.CustomerRepository;
import com.marcos.desafioprog.repositories.AccountRepository;
import com.marcos.desafioprog.services.base.DesafioProgBaseTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CustomerServiceTest extends DesafioProgBaseTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private CustomerService customerService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void findFound(){

        Customer customer = new Customer();
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        Customer result = customerService.find(10);
        assertNotNull(result);

    }
    @Test
    public void findNotFound(){

        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        Customer result = customerService.find(10);
        assertNull(result);
    }

    @Test
    public void insertOk(){
        Account account = new Account();
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(account);
        Customer customer = new Customer();
        customer.setCpf("1920182931");
        customer.setName("Nome");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        Customer result = customerService.insert(customer);
        assertNotNull(result);
    }
    @Test
    public void insertNotSavedConta(){
        Account account = new Account();
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(null);
        Customer customer = new Customer();
        customer.setCpf("12312312311");
        customer.setName("Nome");
        Customer result = customerService.insert(customer);
        assertNull(result);
    }

    @Test
    public void insertNotSavedCliente(){
        Account account = new Account();
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(account);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Customer customer = new Customer();
        customer.setCpf("12312312311");
        customer.setName("Nome");
        Customer result = customerService.insert(customer);
        assertNull(result);
    }
    @Test
    public void insertExistentAccount(){
        Customer customer = new Customer();
        customer.setCpf("01293819203");
        customer.setName("Nome");

        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        thrown.expect(ExistentAccountException.class);
        customerService.insert(customer);
    }
    @Test
    public void insertInvalidName(){
        Customer customer = new Customer();
        customer.setCpf("01293819203");

        thrown.expect(IllegalArgumentException.class);
        customerService.insert(customer);
    }
    @Test
    public void insertInvalidCpf(){
        Customer customer = new Customer();
        customer.setName("Nome");

        thrown.expect(IllegalArgumentException.class);
        customerService.insert(customer);
    }
    @Test
    public void updateOk(){
        Customer customer = new Customer();
        customer.setId(10);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        Customer result = customerService.update(customer);
        assertNotNull(result);
    }
    @Test
    public void updateInvalidCliente(){
        Customer customer = new Customer();
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        thrown.expect(ObjectNotFoundException.class);
        Customer result = customerService.update(customer);

    }
    @Test
    public void updateNotSaved(){
        Customer customer = new Customer();
        customer.setId(10);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        Customer result = customerService.update(customer);
        assertNull(result);
    }


    @Test
    public void deleteOk(){
        Customer customer = new Customer();
        customer.setId(10);
        Account account = new Account();
        account.setId(10);
        customer.setAccount(account);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        customerService.delete(10);

        verify((customerRepository), times(1)).deleteById(anyInt());
        verify((accountRepository), times(1)).deleteById(anyInt());
    }
    @Test
    public void deleteInvalidCliente(){
        Customer customer = new Customer();
        customer.setId(10);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        customerService.delete(10);

        verify((customerRepository), times(0)).deleteById(anyInt());
    }

    @Test
    public void findAllFound(){
        List<Customer> list = new ArrayList<>();
        Customer cli = new Customer();
        list.add(cli);
        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> result = customerService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void findAllNotFound(){
        List<Customer> list = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> result = customerService.findAll();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

}