package com.bruno.cryptoapp.controller;

import com.bruno.cryptoapp.entity.Coin;
import com.bruno.cryptoapp.repository.CoinRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private CoinRepository coinRepository;

    public CoinController(CoinRepository coinRepository){
        this.coinRepository = coinRepository;
    }

    @PostMapping
    public ResponseEntity insertCoin(@RequestBody Coin coin){

        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));   //entao a responsabilidade de passar o date time fica para o back end, nao para o front, que vai precisar passar apenas as outras 3 informacoes da entidade no payload (name, price e quantity)
            return new ResponseEntity<Coin>(coinRepository.insert(coin), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

}
