package com.marcos.desafioprog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Customer implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    private String cpf;
    private String name;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Customer() {
    }

    public Customer(Integer id, String cpf, String name, String creationDate, Account idAccount) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.creationDate = LocalDate.parse(creationDate, dateTimeFormatter);
        this.account = idAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) &&
                Objects.equals(getCpf(), customer.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf());
    }

    private static final int[] cpfWeight = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int digit(String str, int[] weight) {
        int sum = 0;
        for (int i=str.length()-1, digit; i >= 0; i-- ) {
            digit = Integer.parseInt(str.substring(i,i+1));
            sum += digit*weight[weight.length-str.length()+i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    public  boolean isValidCPF() {
        if ((cpf==null) || (cpf.length()!=11)) throw new IllegalArgumentException("CPF must have  11 digits");

        Integer digit1 = digit(cpf.substring(0,9), cpfWeight);
        Integer digit2 = digit(cpf.substring(0,9) + digit1, cpfWeight);
        return cpf.equals(cpf.substring(0,9) + digit1.toString() + digit2.toString());
    }

}
