package com.marcos.desafioprog.desafioprog.services;

import com.marcos.desafioprog.desafioprog.domain.Cliente;
import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.desafioprog.repositories.ClienteRepository;
import com.marcos.desafioprog.desafioprog.repositories.ContaRepository;
import com.marcos.desafioprog.desafioprog.services.base.DesafioProgBaseTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ClienteServiceTest extends DesafioProgBaseTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ContaRepository contaRepository;
    @InjectMocks
    private ClienteService clienteService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void findFound(){

        Cliente cliente = new Cliente();
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        Cliente result = clienteService.find(10);
        assertNotNull(result);

    }
    @Test
    public void findNotFound(){

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        Cliente result = clienteService.find(10);
        assertNull(result);
    }

    @Test
    public void insertOk(){
        Conta conta = new Conta();
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);
        Cliente cliente = new Cliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente result = clienteService.insert(cliente);
        assertNotNull(result);
    }
    @Test
    public void insertNotOk(){
        Conta conta = new Conta();
        when(contaRepository.save(any(Conta.class))).thenReturn(null);
        Cliente cliente = new Cliente();
        Cliente result = clienteService.insert(cliente);
        assertNull(result);
    }

    @Test
    public void updateOk(){
        Cliente cliente = new Cliente();
        cliente.setId(10);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        Cliente result = clienteService.update(cliente);
        assertNotNull(result);
    }

    @Test
    public void deleteOk(){
        Cliente cliente = new Cliente();
        cliente.setId(10);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        clienteService.delete(10);

        verify((clienteRepository), times(1)).deleteById(anyInt());
    }

    @Test
    public void findAllFound(){
        List<Cliente> list = new ArrayList<>();
        Cliente cli = new Cliente();
        list.add(cli);
        when(clienteRepository.findAll()).thenReturn(list);
        List<Cliente> result = clienteService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    public void findAllNotFound(){
        List<Cliente> list = new ArrayList<>();
        when(clienteRepository.findAll()).thenReturn(list);
        List<Cliente> result = clienteService.findAll();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

}