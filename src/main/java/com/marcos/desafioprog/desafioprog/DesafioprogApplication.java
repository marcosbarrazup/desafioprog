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
        Conta c1 = new Conta(null, "28/08/1999", 0.0);

        Cliente cli1 = new Cliente(null,"02144312664", "Marcos","28/09/1999",c1);
		contaRepository.save(c1);
		clienteRepository.save(cli1);


	}
}
