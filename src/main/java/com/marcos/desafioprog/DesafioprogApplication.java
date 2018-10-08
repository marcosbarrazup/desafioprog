package com.marcos.desafioprog;

import com.marcos.desafioprog.domain.Customer;
import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.repositories.CustomerRepository;
import com.marcos.desafioprog.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class DesafioprogApplication  implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
    private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(DesafioprogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


        Customer cli1 = new Customer(null,"02144312664", "Marcos","12/06/2018",null);
		Account c1 = new Account(null, cli1.getCreationDate(), 0.0);
		cli1.setAccount(c1);

        Customer cli2 = new Customer(null,"62505026530", "Joao","11/09/2018",null);
        Account c2 = new Account(null, cli1.getCreationDate(), 0.0);
        cli2.setAccount(c2);

        Customer cli3 = new Customer(null,"77656157425", "Carlos","25/09/2018",null);
        Account c3 = new Account(null, cli1.getCreationDate(), 0.0);
        cli3.setAccount(c3);
		accountRepository.saveAll(Arrays.asList(c1, c2, c3));
		customerRepository.saveAll(Arrays.asList(cli1, cli2, cli3));


	}
}
