package com.marcos.desafioprog.enums;

public enum OperationType {
    TRANSFER(1, "Transfer"),
    DEPOSIT(2,  "Deposit"),
    WITHDRAW(3, "Withdraw");

    private Integer cod;
    private String description;


    OperationType(Integer cod, String description) {
        this.cod = cod;
        this.description = description;

    }

    public Integer getCod() {
        return cod;
    }
    public String getDescription() {
        return description;
    }



    public static OperationType toEnum(Integer cod){
        if(cod == null) return null;

        for(OperationType x : OperationType.values()){
            if(cod.equals(x.getCod())) return x;
        }
        throw new IllegalArgumentException("Invalid  ID: " + cod);
    }
}
