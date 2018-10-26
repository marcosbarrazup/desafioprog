package com.marcos.desafioprog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcos.desafioprog.domain.Operation;
import com.marcos.desafioprog.enums.OperationType;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

public class OperationDTO {

    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Min(value = 1)
    private Double value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer operationType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateHour;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sourceAccount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer destinationAccount;

    @JsonProperty("accountBalance")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double balanceAccount;



    public OperationDTO(Operation operation) {
        this.id = operation.getId();
        this.value = operation.getValue();
        this.operationType = operation.getOperationType().getCod();
        this.dateHour = operation.getDateHour();



        if (OperationType.DEPOSIT.getDescription() == operation.getOperationType().getDescription() ||
                OperationType.TRANSFER.getDescription() == operation.getOperationType().getDescription())
            this.destinationAccount = operation.getDestinationAccount().getId();

        if(OperationType.WITHDRAW.getDescription() == operation.getOperationType().getDescription() ||
                OperationType.TRANSFER.getDescription() == operation.getOperationType().getDescription())
        this.sourceAccount = operation.getSourceAccount().getId();


    }

    public OperationDTO(Integer  id, Double balanceAccount) {
        this.id = id;
        this.balanceAccount = balanceAccount;
    }


    public OperationDTO() {
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDTO that = (OperationDTO) o;
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


    public OperationType getOperationType() {
        return OperationType.toEnum(this.operationType);
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType.getCod();
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public Integer getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Integer sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Integer getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Integer destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public Double getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(Double balanceAccount) {
        this.balanceAccount = balanceAccount;
    }
}
