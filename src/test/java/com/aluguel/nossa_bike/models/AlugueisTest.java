package com.aluguel.nossa_bike.models;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;


public class AlugueisTest {

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        LocalDateTime dataHora = LocalDateTime.now();
        UUID tranca = UUID.randomUUID();
        UUID bicicleta = UUID.randomUUID();
        Cartao cartao = new Cartao();
        Ciclista ciclista = new Ciclista();

        Alugueis alugueis = new Alugueis(id, dataHora, tranca, bicicleta, cartao, ciclista);

        assertEquals(id, alugueis.getId());
        assertEquals(dataHora, alugueis.getDataHora());
        assertEquals(tranca, alugueis.getTranca());
        assertEquals(bicicleta, alugueis.getBicicleta());
        assertEquals(cartao, alugueis.getCartao());
        assertEquals(ciclista, alugueis.getCiclista());
    }
}