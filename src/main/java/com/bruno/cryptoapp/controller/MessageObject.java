package com.bruno.cryptoapp.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageObject {

    private String name;
    private String type;
    private boolean nullable;
    private String mensagemErro;
}
