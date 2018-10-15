package com.marcos.desafioprog.dto;

import com.marcos.desafioprog.domain.Customer;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerDTO {

    private Integer id;
    @NotEmpty(message = "Name Required")
    private String name;
    @CPF(message = "Invalid CPF!")
    @NotEmpty(message = "Name Required")
    private String cpf;

    private LocalDate creationDate;


    public CustomerDTO() {
    }

    public CustomerDTO(Customer c){
        this.id = c.getId();
        this.name = c.getName();
        this.creationDate = c.getCreationDate();
        this.cpf = c.getCpf();
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
        return Objects.equals(getId(), that.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
