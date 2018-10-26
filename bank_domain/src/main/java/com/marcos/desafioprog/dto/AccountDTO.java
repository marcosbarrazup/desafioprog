package com.marcos.desafioprog.dto;

import com.marcos.desafioprog.domain.Account;

import java.time.LocalDate;
import java.util.Objects;

public class AccountDTO {

    private Integer id;

    private LocalDate creationDate;

    private Double balance;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO accountDTO = (AccountDTO) o;
        return Objects.equals(getId(), accountDTO.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
