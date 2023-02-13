package com.bruno.cryptoapp.controller.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaEstado {

    public int id;
    public String sigla;
    public String nome;
    public Regiao regiao;

    public class Regiao{
        public int id;
        public String sigla;
        public String nome;
    }

}
