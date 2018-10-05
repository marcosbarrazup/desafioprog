package com.marcos.desafioprog.desafioprog.services;

import com.marcos.desafioprog.desafioprog.domain.Cliente;
import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.exceptions.ExistentAccountException;
import com.marcos.desafioprog.desafioprog.exceptions.ObjectNotFoundException;
import com.marcos.desafioprog.desafioprog.repositories.ClienteRepository;
import com.marcos.desafioprog.desafioprog.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ContaRepository contaRepository;

    public Cliente find(Integer id) throws ObjectNotFoundException {
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente insert(Cliente obj) throws ExistentAccountException{

        if(obj.getCpf()== null) {
            throw new IllegalArgumentException("Erro: CPF não informado!");
        }
        else if (obj.getNome() == null){
            throw new IllegalArgumentException("Erro: Nome não informado!");
        }

        if (clienteRepository.findByCpf(obj.getCpf()) == null) {
            obj.setId(null);
            obj.setIdConta(null);
            obj.setDataCriacao(LocalDate.now());
            Conta conta = new Conta(null, obj.getDataCriacao(), 0.0);
            conta = contaRepository.saveAndFlush(conta);
            if (conta != null) {
                obj.setIdConta(conta);
                return clienteRepository.saveAndFlush(obj);
            }
            return null;
        }

        throw new ExistentAccountException("Erro! CPF já cadastrado.");
    }

    public Cliente update(Cliente obj) {

        if(obj.getCpf()== null) {
            throw new IllegalArgumentException("Erro: CPF não informado!");
        }
        else if (obj.getNome() == null){
            throw new IllegalArgumentException("Erro: Nome não informado!");
        }

        if (clienteRepository.findByCpf(obj.getCpf()) == null) {
            Cliente existente = find(obj.getId());
            obj.setDataCriacao(existente.getDataCriacao());
            obj.setIdConta(existente.getIdConta());

            return clienteRepository.saveAndFlush(obj);
        }

        throw new ExistentAccountException("Erro! CPF já cadastrado.");
    }

    public void delete(Integer id) {
        Cliente cliente = find(id);
        clienteRepository.deleteById(id);
        contaRepository.deleteById(cliente.getIdConta().getId());

    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

}
