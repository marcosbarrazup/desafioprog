package com.marcos.desafioprog.desafioprog.services;

import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.domain.Operacao;
import com.marcos.desafioprog.desafioprog.enums.TipoOperacao;
import com.marcos.desafioprog.desafioprog.exceptions.InsufficientBalanceException;
import com.marcos.desafioprog.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.desafioprog.repositories.ContaRepository;
import com.marcos.desafioprog.desafioprog.repositories.OperacaoRepository;
import com.marcos.desafioprog.desafioprog.services.base.DesafioProgBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ContaServiceTest extends DesafioProgBaseTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private OperacaoRepository operacaoRepository;

    @InjectMocks
    private ContaService contaService;

    private Operacao operacao;
    private Conta conta1;
    private Conta conta2;

    @Before
    public void setup() {
        conta1 = new Conta();
        conta2 = new Conta();
        conta1.setId(1);
        conta1.setSaldo(0.0);
        conta2.setId(2);
        conta2.setSaldo(0.0);
        operacao = new Operacao();
        operacao.setContaDestino(conta2);
        operacao.setTipoOperacao(TipoOperacao.DEPOSITO);
        operacao.setId(1);
        operacao.setContaOrigem(conta1);
        operacao.setDataHora(LocalDateTime.now());
        operacao.setValor(100.0);


    }

    @Test
    public void findFound() {

        Conta conta = new Conta();
        when(contaRepository.findById(anyInt())).thenReturn(Optional.of(conta));
        Conta result = contaService.find(10);
        assertNotNull(result);

    }

    @Test
    public void findNotFound() {

        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        Conta result = contaService.find(10);
        assertNull(result);
    }

    @Test
    public void transferOk() {
        operacao.setValor(100.0);
        conta1.setSaldo(100.0);
        operacao.setContaOrigem(conta1);
        operacao.setContaDestino(conta2);

        when(operacaoRepository.saveAndFlush(any(Operacao.class))).thenReturn(operacao);
        Operacao result = contaService.transfer(operacao);

        assertNotNull(result);

    }
    @Test
    public void transferInsufficient() {
        operacao.setValor(150.0);
        conta1.setSaldo(100.0);
        operacao.setContaOrigem(conta1);
        operacao.setContaDestino(conta2);
        thrown.expect(InsufficientBalanceException.class);

        Operacao result = contaService.transfer(operacao);

    }

    @Test
    public void depositOk(){

        when(operacaoRepository.saveAndFlush(any(Operacao.class))).thenReturn(operacao);
        Operacao result = contaService.deposit(operacao);
        assertNotNull(result);
    }

    @Test
    public void withdrawOk(){
        operacao.setValor(100.0);
        conta1.setSaldo(100.0);
        operacao.setContaOrigem(conta1);
        operacao.setContaDestino(conta1);
        when(operacaoRepository.saveAndFlush(any(Operacao.class))).thenReturn(operacao);
        boolean result = contaService.withdraw(operacao);
        assertTrue(result);
    }
    @Test
    public void withdrawInsufficient(){
        operacao.setValor(150.0);
        conta1.setSaldo(100.0);
        operacao.setContaOrigem(conta1);
        operacao.setContaDestino(conta1);
        thrown.expect(InsufficientBalanceException.class);
        contaService.withdraw(operacao);

    }

    @Test
    public void findAllFound(){
        List<Conta> list = new ArrayList<>();
        list.add(conta1);
        list.add(conta2);
        when(contaRepository.findAll()).thenReturn(list);
        List<Conta> result = contaService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    public void findAllNotFound(){
        List<Conta> list = new ArrayList<>();
        when(contaRepository.findAll()).thenReturn(list);
        List<Conta> result = contaService.findAll();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void extratoHasAOperation(){
        List<Operacao> list = new ArrayList<>();
        list.add(operacao);
        when(operacaoRepository.findAll()).thenReturn(list);
        when(contaRepository.findById(anyInt())).thenReturn(Optional.of(conta1));
        Set<Operacao> result = contaService.extrato(1);
        assertNotNull(result);
        assertEquals(1,result.size());
    }
    @Test
    public void extratoHasNotAOperation(){
        List<Operacao> list = new ArrayList<>();
        when(operacaoRepository.findAll()).thenReturn(list);
        when(contaRepository.findById(anyInt())).thenReturn(Optional.of(conta1));
        Set<Operacao> result = contaService.extrato(1);
        assertNotNull(result);
        assertEquals(0,result.size());
    }

    @Test
    public void extratoInvalidAccount(){
        List<Operacao> list = new ArrayList<>();
        list.add(operacao);
        when(operacaoRepository.findAll()).thenReturn(list);
        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        thrown.expect(ObjectNotFoundException.class);
        Set<Operacao> result = contaService.extrato(1);

    }


}