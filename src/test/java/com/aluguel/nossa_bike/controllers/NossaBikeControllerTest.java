/*package com.aluguel.nossa_bike.controllers;

import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class NossaBikeControllerTest {

    @Autowired
    NossaBikeController controller;
    
  @Test
    public void olaTestt() {
    LinkedList<String> listaZeroErro = new LinkedList<String>();
    cadastroMock = mock(Cadastro.class);
    Ciclista ciclista = new Ciclista();

    when(cadastroMock.isValid(any())).thenReturn(listaZeroErro);
    assertEquals(listaZeroErro, controller.cadastrar(ciclista));
  }*/

  /*@Test
    public void testIT(){
            
    RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8081/").build();
    Client client = new Client(1,"teste");        
    ResponseEntity<String> response = restTemplate.exchange("/client", HttpMethod.POST,, null);
            
    assertEquals("Olá, mundo.", response.getBody());
  }

}*/