package com.marcos.desafioprog.desafioprog.enums;

public enum TipoOperacao {
    TRANSFERENCIA(1, "Transferência"),
    DEPOSITO(2,  "Depósito"),
    SAQUE(3, "Saque");

    private Integer cod;
    private String descricao;


    TipoOperacao( Integer cod,String descricao) {
        this.cod = cod;
        this.descricao = descricao;

    }

    public Integer getCod() {
        return cod;
    }
    public String getDescricao() {
        return descricao;
    }



    public static TipoOperacao toEnum(Integer cod){
        if(cod == null) return null;

        for(TipoOperacao x : TipoOperacao.values()){
            if(cod.equals(x.getCod())) return x;
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
