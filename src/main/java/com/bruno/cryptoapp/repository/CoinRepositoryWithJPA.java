package com.bruno.cryptoapp.repository;

import com.bruno.cryptoapp.dto.CoinTransationDTO;
import com.bruno.cryptoapp.entity.Coin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepositoryWithJPA {

    private EntityManager entityManager;

    public CoinRepositoryWithJPA(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional // essa anotacao ja faz o trabalho de iniciar o transactional (begin) e depois fazer o commit
    public Coin insert(Coin coin){
        entityManager.persist(coin);
        return coin;
    }

    // nao foi necessario colocar @Transactional pois esse metodo getAll nao faz nenhuma alteracao, soh faz consulta, diferente do insert e update
    public List<CoinTransationDTO> getAll(){
        String jpql = "select new com.bruno.cryptoapp.dto.CoinTransationDTO(c.name, sum(c.quantity)) from Coin c group by c.name";
        TypedQuery<CoinTransationDTO> query = entityManager.createQuery(jpql, CoinTransationDTO.class);
        return query.getResultList();      // nesse resultList vem uma lista com as transacoes
    }

    public List<Coin> getByName(String name){
        String jpql = "select c from Coin c where c.name like :name";
        TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);   // aqui eu passo a query (jpql), e a entidade que vai ser mapeada (Coin.class)
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

//    public String remove(int id){
//        int result = 0;
//        result = jdbcTemplate.update(DELETE, id);
//
//        if(result == 1) {
//            return "Removido com sucesso";
//        } else{
//            return "Nao foi possivel remover";
//        }
//    }

    @Transactional
    public Coin update(Coin coin){
        entityManager.merge(coin);
        return coin;
    }

}
