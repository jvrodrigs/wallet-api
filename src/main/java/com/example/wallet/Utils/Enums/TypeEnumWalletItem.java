package com.example.wallet.Utils.Enums;

public enum TypeEnumWalletItem {

    EN("ENTRADA"),
    SD("SAÍDA");

    private final String value;

    TypeEnumWalletItem(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeEnumWalletItem getEnum(String value){
        for (TypeEnumWalletItem t: values()){
            if (value.equals(t.getValue())){
                return t;
            }
        }
         return null;
    }
}
