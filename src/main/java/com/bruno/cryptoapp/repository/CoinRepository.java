package com.bruno.cryptoapp.repository;

import com.bruno.cryptoapp.entity.Coin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {

    private static String INSERT = "insert into coin (name, price, quantity, datetime) values (?,?,?,?)";

    private static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name";

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

    public List<Coin> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Coin>() {    // sao 2 parametros: a query, e o RowMapper
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {   // os resultados da tabela vem no rs
                Coin coin = new Coin();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }
            // o map vai iterar e fazer a tarefa acima pra todas as coins que retornarem do banco de dados
        });
    }

}
