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

	public static void main(String[] args) {
		SpringApplication.run(DesafioprogApplication.class, args);
	}

	@Override
	public void run(String... args) {


	}
}
