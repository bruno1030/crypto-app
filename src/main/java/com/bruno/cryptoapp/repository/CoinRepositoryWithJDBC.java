package com.bruno.cryptoapp.repository;

import com.bruno.cryptoapp.dto.CoinTransationDTO;
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
public class CoinRepositoryWithJDBC {

    private static String INSERT = "insert into coin (name, price, quantity, datetime) values (?,?,?,?)";

    private static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name"; // vai trazer a soma da quantidade por cada nome de coin, senao a lista pode ficar muito grande, com todos os registros de cada coin. Dessa forma ele agrupa por name, e faz a soma total da quantidade por cada coin

    private static String SELECT_BY_NAME = "select * from coin where name = ?";

    private static String DELETE = "delete from coin where id = ?";

    private static String UPDATE = "update coin set name = ?, price = ?, quantity = ? where id = ?";

    private JdbcTemplate jdbcTemplate;

    public CoinRepositoryWithJDBC(JdbcTemplate jdbcTemplate){
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

    public List<CoinTransationDTO> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinTransationDTO>() {    // sao 2 parametros: a query, e o RowMapper
            @Override
            public CoinTransationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {   // os resultados da tabela vem no rs
                CoinTransationDTO coin = new CoinTransationDTO();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }
            // o map vai iterar e fazer a tarefa acima pra todas as coins que retornarem do banco de dados
        });
    }

    public List<Coin> getByName(String name){
        Object[] attr = new Object[]{ name };
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Coin>() {
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Coin coin = new Coin();
                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }
        }, attr);    // aqui no finalzinho eh terceiro parametro do metodo query
    }

    public String remove(int id){
        int result = 0;
        result = jdbcTemplate.update(DELETE, id);

        if(result == 1) {
            return "Removido com sucesso";
        } else{
            return "Nao foi possivel remover";
        }
    }

    public Coin update(Coin coin){
        Object[] attributes = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getId()
        };
        jdbcTemplate.update(UPDATE, attributes);
        return coin;
    }

}