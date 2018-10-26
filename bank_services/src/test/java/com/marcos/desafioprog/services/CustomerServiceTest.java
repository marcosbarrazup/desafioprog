package com.marcos.desafioprog.services;

import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.repositories.AccountRepository;
import com.marcos.desafioprog.repositories.CustomerRepository;
import com.marcos.desafioprog.services.base.DesafioProgBaseTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CustomerServiceTest extends DesafioProgBaseTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private CustomerService customerService;

    private static Validator validator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void find_IfCustomerExistsShouldReturnThat(){

        Customer customer = new Customer();
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        Customer result = customerService.find("02144312664");
        assertNotNull(result);

    }
    @Test
    public void find_IfAccountIsNullMustReturnNull(){

        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        thrown.expect(ObjectNotFoundException.class);
        Customer result = customerService.find("02144123123");
        assertNull(result);
    }

    @Test
    public void insert_ValidAccountShouldInsertAndReturnThat(){
        Account account = new Account();
        Customer customer = new Customer();
        customer.setCpf("87427837398");
        customer.setName("Nome");
        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(account);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        Customer result = customerService.insert(customer);
        assertNotNull(result);
    }
    @Test
    public void insert_IfDoesntSaveTheAccountShouldReturnNull(){

        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(null);
        Customer customer = new Customer();
        customer.setCpf("87427837398");
        customer.setName("Nome");
        Customer result = customerService.insert(customer);
        assertNull(result);
    }

    @Test
    public void insert_IfDoesntSaveTheCustomerShouldReturnNull(){
        Account account = new Account();
        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(account);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Customer customer = new Customer();
        customer.setCpf("87427837398");
        customer.setName("Nome");
        Customer result = customerService.insert(customer);
        assertNull(result);
    }
    @Test
    public void insert_IfAccountAlreadyExistsThrowAnException(){
        Customer customer = new Customer();
        customer.setCpf("87427837398");
        customer.setName("Nome");

        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        thrown.expect(ExistentAccountException.class);
        customerService.insert(customer);
    }
    @Test
    public void insert_IfNameIsNullThenThrowAnException(){
        Customer customer = new Customer();
        customer.setCpf("01293819203");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }
    @Test
    public void insert_IfNameIsBlankThenThrowAnException(){
        Customer customer = new Customer();
        customer.setCpf("01293819203");
        customer.setName("");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }
    @Test
    public void insert_IfCpfIsNullThenThrowAnException(){
        Customer customer = new Customer();
        customer.setName("Nome");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }
    @Test
    public void insert_IfCpfDoesntHave11DigitsThenThrowAnException(){
        Customer customer = new Customer();
        customer.setName("Nome");
        customer.setCpf("192819382");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }
    @Test
    public void insert_IfCpfIsInvalidThenThrowAnException(){
        Customer customer = new Customer();
        customer.setName("Nome");
        customer.setCpf("12391829381");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }

    @Test
    public void update_IfAllTheArgumentsAreValidThenReturnTheNewCustomer(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setName("Name");
        customer.setCpf("87427837398");
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        Customer result = customerService.update(customer, "87427837398");
        assertNotNull(result);
    }
    @Test
    public void update_IfDoesntHaveTheNewCpf(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setName("Name");
        customer.setCpf("87427837398");

        Customer customer2 = new Customer();
        customer2.setId(15);
        customer2.setName("Name");
        customer2.setCpf("02144312664");
        when(customerRepository.findByCpf("87427837398")).thenReturn(customer);
        when(customerRepository.findByCpf("02144312664")).thenReturn(null);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        Customer result = customerService.update(customer2, "87427837398");
        assertNotNull(result);
    }
    @Test
    public void update_IfCpfIsInvalidThenThrowAnException(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setName("Name");
        customer.setCpf("10293810293");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);

    }
    @Test
    public void update_IfNameIsBlankThenThrowAnException(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setCpf("87427837398");
        customer.setName("");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);

    }
    @Test
    public void update_IfNameIsNullThenThrowAnException(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setCpf("87427837398");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);

    }
    @Test
    public void update_IfCpfIsNullThenThrowAnException(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setName("Name");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        System.out.println(violations);

    }
    @Test
    public void update_IfCpfAlreadyExistsThenThrowAnException(){
        Customer customer = new Customer();
        customer.setId(10);

        Customer customer2 = new Customer();
        customer2.setId(15);
        customer2.setName("Name");
        customer2.setCpf("02144312664");
        when(customerRepository.findByCpf("87427837398")).thenReturn(customer);
        when(customerRepository.findByCpf("02144312664")).thenReturn(customer2);
        thrown.expect(ExistentAccountException.class);
        customerService.update(customer2 , "87427837398");

    }
    @Test
    public void update_IfNotFoundTheCustomerThenThrowAnException(){
        Customer customer2 = new Customer();
        customer2.setId(15);
        customer2.setName("Name");
        customer2.setCpf("02144312664");
        when(customerRepository.findByCpf("87427837398")).thenReturn(null);

        thrown.expect(ObjectNotFoundException.class);
        customerService.update(customer2 , "87427837398");

    }
    @Test
    public void update_IfCpfAlreadyExistsAndIsHimselfThenReturnTheNewCustomer(){
        Customer customer = new Customer();
        customer.setId(10);

        Customer customer2 = new Customer();
        customer2.setId(10);
        customer2.setName("Name");
        customer2.setCpf("87427837398");
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
        Customer result = customerService.update(customer2, "87427837398");
        assertNotNull(result);

    }


    @Test
    public void delete_IfCustomerExistsVerifyThatHeWasDeleted(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setCpf("02144312664");
        Account account = new Account();
        account.setId(10);
        customer.setAccount(account);
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);

        customerService.delete("02144312664");

        verify((customerRepository), times(1)).deleteById(anyInt());
        verify((accountRepository), times(1)).deleteById(anyInt());
    }
    @Test
    public void delete_InvalidCustomerShouldThrowAnException(){
        Customer customer = new Customer();
        customer.setId(10);
        customer.setCpf("02144312664");
        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        customerService.delete("02144312664");

        verify((customerRepository), times(0)).deleteById(anyInt());
    }

    @Test
    public void findAll_FoundAtLeastOneShouldReturnAnCustomerList(){
        List<Customer> list = new ArrayList<>();
        Customer cli = new Customer();
        list.add(cli);
        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> result = customerService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void findAll_NotFoundShouldReturnAnException(){
        List<Customer> list = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(list);
        thrown.expect(ObjectNotFoundException.class);
        customerService.findAll();

    }

}