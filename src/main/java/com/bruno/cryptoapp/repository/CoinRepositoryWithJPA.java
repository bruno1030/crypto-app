package com.bruno.cryptoapp.repository;

import com.bruno.cryptoapp.dto.CoinTransationDTO;
import com.bruno.cryptoapp.entity.Coin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepositoryWithJPA {

    private EntityManager entityManager;

    public CoinRepositoryWithJPA(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @@Transactional // essa anotacao ja faz o trabalho de iniciar o transactional (begin) e depois fazer o commit
    public Coin insert(Coin coin){
        entityManager.persist(coin);
        return coin;
    }

    public List<CoinTransationDTO> getAll(){
        String jpql = "select new com.bruno.cryptoapp.dto.CoinTransationDTO(c.name, sum(c.quantity)) from Coin c group by c.name";
        TypedQuery<CoinTransationDTO> query = entityManager.createQuery(jpql, CoinTransationDTO.class);
        return query.getResultList();      // nesse resultList vem uma lista com as transacoes
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

    @Transactional
    public Coin update(Coin coin){
        entityManager.merge(coin);
        return coin;
    }

}
