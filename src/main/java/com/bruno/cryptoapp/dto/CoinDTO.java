package com.bruno.cryptoapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CoinDTO {

    private String name;
    private BigDecimal quantity;

}
