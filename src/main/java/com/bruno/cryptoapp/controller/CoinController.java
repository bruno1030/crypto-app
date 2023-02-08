package com.bruno.cryptoapp.controller;

import com.bruno.cryptoapp.entity.Coin;
import com.bruno.cryptoapp.repository.CoinRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coin")
@Slf4j
public class CoinController {

    private CoinRepository coinRepository;

    @Bean
    public Coin init(){
        Coin c1 = new Coin();
        c1.setName("BITCOIN");
        c1.setPrice(new BigDecimal(100));
        c1.setQuantity(new BigDecimal(0.0005));
        c1.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin c2 = new Coin();
        c2.setName("BITCOIN");
        c2.setPrice(new BigDecimal(100));
        c2.setQuantity(new BigDecimal(0.0025));
        c2.setDateTime(new Timestamp(System.currentTimeMillis()));

        Coin c3 = new Coin();
        c3.setName("ETHEREUM");
        c3.setPrice(new BigDecimal(500));
        c3.setQuantity(new BigDecimal(0.0045));
        c3.setDateTime(new Timestamp(System.currentTimeMillis()));

        coinRepository.insert(c1);
        coinRepository.insert(c2);
        coinRepository.insert(c3);

        return c1;
    }

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

    @GetMapping
    public ResponseEntity getCoin(){
        return new ResponseEntity<>(coinRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity getCoinByName(@PathVariable String name){
        try{
            return new ResponseEntity<>(coinRepository.getByName(name), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        try{
            return new ResponseEntity<String>(coinRepository.remove(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Coin coin){
        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<Coin>(coinRepository.update(coin), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    String NOME = "Nome deve ser String";
    String PRICE = "Nome deve ser numerico (decimal)";
    String QUANTITY = "Nome deve ser numerico (decimal)";
    String FLAG = "Nome deve ser numerico (decimal)";

    @PostMapping("/json")
    public String stringToJson(@RequestBody String payload){    // teste aleatorio que fiz sobre JSONObject (para work)

//        List<Object[]> objsTeste = new ArrayList<>();
//        objsTeste.add(new Object[]{"name", });

        try{
            JSONObject jsonObject = new JSONObject(payload);

            List<MessageObject> objetos = new ArrayList<>();
            objetos.add(new MessageObject("name", "String ('a' ou 'r')", true, NOME));
            objetos.add(new MessageObject("price", "numerico (Double ou Integer)", false, PRICE));
            objetos.add(new MessageObject("quantity", "numerico (Double/Integer)", true, QUANTITY));
            objetos.add(new MessageObject("flag", "String ('s' ou 'n')", false, FLAG));

            for (int i = 0; i < objetos.size(); i++) {
                if(!objetos.get(i).isNullable() && !jsonObject.has(objetos.get(i).getName())){
                    System.out.println(objetos.get(i).getMensagemErro());
                }
            }
            String texto = "xxx";
        }catch (Exception e){
            e.printStackTrace();
        }

        String texto = "xxx";

        return "ok";
    };

}
