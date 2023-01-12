package com.aluguel.nossa_bike.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class CiclistaTest {
    @Test
    public void testGetters() {
        UUID id = UUID.randomUUID();
        String nome = "John Doe";
        Ciclista.AccountStatus status = Ciclista.AccountStatus.ATIVO;
        String nascimento = "01/01/2000";
        String cpf = "123.456.789-00";
        Passaport passaport = new Passaport();
        Ciclista.Nacionalidade nacionalidade = Ciclista.Nacionalidade.BRASILEIRO;
        String emailUser = "user@example.com";
        String urlFotoDocumento = "https://example.com/image.jpg";

        Ciclista ciclista = new Ciclista(id, nome, status, nascimento, cpf, passaport, nacionalidade, emailUser, urlFotoDocumento);

        assertEquals(id, ciclista.getId());
        assertEquals(nome, ciclista.getNome());
        assertEquals(status, ciclista.getStatus());
        assertEquals(nascimento, ciclista.getNascimento());
        assertEquals(cpf, ciclista.getCpf());
        assertEquals(passaport, ciclista.getPassaport());
        assertEquals(nacionalidade, ciclista.getNacionalidade());
        assertEquals(emailUser, ciclista.getEmailUser());
        assertEquals(urlFotoDocumento, ciclista.getUrlFotoDocumento());
    }
}
