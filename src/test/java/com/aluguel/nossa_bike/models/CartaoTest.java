package com.aluguel.nossa_bike.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class CartaoTest {
    @Test
    public void testGetters() {
        UUID id = UUID.randomUUID();
        String nome = "John Doe";
        long numero = 1234567890;
        String validade = "10/2022";
        int cvv = 123;
        Ciclista ciclista = null;
        Cartao cartao = new Cartao(id, nome, numero, validade, cvv, ciclista);

        assertEquals(id, cartao.getId());
        assertEquals(nome, cartao.getNomeTitular());
        assertEquals(numero, cartao.getNumero());
        assertEquals(validade, cartao.getValidade());
        assertEquals(cvv, cartao.getCvv());
        assertEquals(ciclista, cartao.getCiclista());
    }
}
