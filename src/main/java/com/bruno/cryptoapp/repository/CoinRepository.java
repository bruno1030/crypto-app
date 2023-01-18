package com.bruno.cryptoapp.repository;

import com.bruno.cryptoapp.entity.Coin;
import org.springframework.jdbc.core.JdbcTemplate;

public class CoinRepository {

    private static String INSERT = "insert into coin (name, price, quantity, datetime) values (?,?,?,?)";

    private JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Coin insert(Coin coin){
        Object[] attributes = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDateTime()
        };
        jdbcTemplate.update(INSERT, attributes);   // aqui entao sao 2 parametros: a query sql, e os atributos para insercao
        return coin;
    }
}
