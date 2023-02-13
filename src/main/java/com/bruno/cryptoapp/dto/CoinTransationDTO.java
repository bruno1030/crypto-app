package com.bruno.cryptoapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CoinTransationDTO {

    private String name;
    private BigDecimal quantity;

    public CoinTransationDTO(String name, BigDecimal quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public CoinTransationDTO(){

    }

}
