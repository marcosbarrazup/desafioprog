package com.marcos.desafioprog.desafioprog;

import com.marcos.desafioprog.desafioprog.domain.Cliente;
import com.marcos.desafioprog.desafioprog.domain.Conta;
import com.marcos.desafioprog.desafioprog.repositories.ClienteRepository;
import com.marcos.desafioprog.desafioprog.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class DesafioprogApplication  implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
    private ContaRepository contaRepository;

	public static void main(String[] args) {
		SpringApplication.run(DesafioprogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


        Cliente cli1 = new Cliente(null,"02144312664", "Marcos","12/06/2018",null);
		Conta c1 = new Conta(null, cli1.getDataCriacao(), 0.0);
		cli1.setIdConta(c1);

        Cliente cli2 = new Cliente(null,"11711928172", "Joao","11/09/2018",null);
        Conta c2 = new Conta(null, cli1.getDataCriacao(), 0.0);
        cli2.setIdConta(c2);

        Cliente cli3 = new Cliente(null,"01927561820", "Carlos","25/09/2018",null);
        Conta c3 = new Conta(null, cli1.getDataCriacao(), 0.0);
        cli3.setIdConta(c3);
		contaRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));


	}
}
