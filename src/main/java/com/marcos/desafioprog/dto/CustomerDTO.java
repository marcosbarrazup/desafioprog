package com.marcos.desafioprog.dto;

import com.marcos.desafioprog.domain.Account;
import com.marcos.desafioprog.domain.Customer;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerDTO {

    //private Integer id;
    @NotEmpty(message = "Name Required")
    private String name;
    @CPF(message = "Invalid CPF!")
    @NotEmpty(message = "Name Required")
    private String cpf;

    private Account account;



    public CustomerDTO() {
    }

    public CustomerDTO(Customer c){
        this.name = c.getName();
        this.cpf = c.getCpf();
        this.account= c.getAccount();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(getCpf(), that.getCpf());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getCpf());
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }


}
