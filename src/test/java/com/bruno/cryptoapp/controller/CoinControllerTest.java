package com.bruno.cryptoapp.controller;

import com.bruno.cryptoapp.controller.domain.ConsultaEstado;
import com.bruno.cryptoapp.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CoinControllerTest {

    @Mock
    CoinRepository repository;

    @Mock
    HttpRequest request;

    @Mock
    HttpClient client;

    @Mock
    HttpResponse<String> response;

    @InjectMocks
    CoinController controller = new CoinController(repository);

    @Test
    void getEstados() throws IOException, InterruptedException {

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Mockito.when(client.send(any())).thenReturn(response);

        ResponseEntity<ConsultaEstado> estados = controller.getEstados();
        assertEquals(33, estados.getBody().getId());

    }
}