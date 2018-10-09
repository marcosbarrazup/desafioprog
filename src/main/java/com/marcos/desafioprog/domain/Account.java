package com.marcos.desafioprog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Account implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    private Double balance;

    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private Customer customers;

    @JsonIgnore
    @OneToMany(mappedBy = "sourceAccount")
    private List<Operation>  sourceOperations;

    @JsonIgnore
    @OneToMany(mappedBy = "destinationAccount")
    private List<Operation>  destinationOperations;


    public Account() {
    }

    public Account(Integer id, LocalDate creationDate, Double balance) {
        this.id = id;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomers() {
        return customers;
    }

    public void setCustomers(Customer customers) {
        this.customers = customers;
    }

    public List<Operation> getSourceOperations() {
        return sourceOperations;
    }

    public void setSourceOperations(List<Operation> sourceOperations) {
        this.sourceOperations = sourceOperations;
    }

    public List<Operation> getDestinationOperations() {
        return destinationOperations;
    }

    public void setDestinationOperations(List<Operation> destinationOperations) {
        this.destinationOperations = destinationOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void deposit(Double valor) {
        this.balance+= valor;
    }

    public boolean withdraw(Double valor) {
        if(this.balance-valor>=0.0) {
            this.balance -=valor;
            return true;
        }
        return false;
    }
}
