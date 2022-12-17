package com.aluguel.nossa_bike.controllers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Passaport;
import com.aluguel.nossa_bike.models.Ciclista.Nacionalidade;
import com.aluguel.nossa_bike.models.Ciclista.Status;
import com.aluguel.nossa_bike.services.Cadastro;

@SpringBootTest
public class CadastroTest {


    @InjectMocks
    Cadastro cadastro = new Cadastro();

    Ciclista ciclista;
    Passaport passaport;

/*	@Before
	public void setUp() {
        MockitoAnnotations.initMocks(this);
	}

    @BeforeEach
	public void setUpCiclista() {
        startCiclista();
	}*/

    @Test
    public void whenIsEmailValidThenReturnTrue(){
        startCiclista();
        String email = ciclista.getEmail();
        assertEquals(true,cadastro.isValidEmail(email));
    }

    @Test
    public void whenIsNameValidThenReturnTrue(){
        startCiclista();
        String name = ciclista.getNome();
        assertEquals(true,cadastro.isValidName(name));
    }

    @Test
    public void whenIsStatusValidThenReturnTrue(){
        startCiclista();
        Status status = ciclista.getStatus();
        assertEquals(true,cadastro.isInativeStatus(status));
    }

    @Test
    public void whenIsValidNascThenReturnTrue(){
        startCiclista();
        String nascimento = ciclista.getNascimento();
        assertEquals(true,cadastro.isValidDate(nascimento));
    }

    @Test
    public void whenIsValidValThenReturnTrue(){
        startCiclista();
        String validade = passaport.getValidade();
        assertEquals(true,cadastro.isValidDate(validade));
    }

    @Test
    public void whenIsValidCpfThenReturnTrue(){
        startCiclista();
        String cpf = ciclista.getCpf();
        assertEquals(true,cadastro.isValidCpf(cpf));
    }

    @Test
    public void whenIsValidPassaportThenReturnTrue(){
        startCiclista();
        Passaport passaportTest = ciclista.getPassaport();
        assertEquals(true,cadastro.isValidPassaport(passaportTest));
    }

    @Test
    public void whenIsValidNacThenReturnTrue(){
        startCiclista();
        Nacionalidade nacionalidade = ciclista.getNacionalidade();
        assertEquals(true,cadastro.isValidNac(nacionalidade));
    }

    @Test
    public void whenIsValidEmailThenReturnTrue(){
        startCiclista();
        String email = ciclista.getEmail();
        assertEquals(true,cadastro.isValidEmail(email));
    }

    @Test
    public void whenIsValidUrlThenReturnTrue(){
        startCiclista();
        String urlFotoDocumento = ciclista.getUrlFotoDocumento();
        assertEquals(true,cadastro.isValidUri(urlFotoDocumento));
    }

    @Test
    public void whenIsValidThenReturnTrue(){
        startCiclista();
        List<String> resposta = cadastro.isValid(ciclista);
        List<String> zeroErros = new LinkedList<>();
        assertEquals(zeroErros,resposta);
    }

    private void startCiclista(){
        passaport = new Passaport(1, "01/12/2001", "Brasil");
        ciclista = new Ciclista(1, "Thiago", Status.INATIVO, "23/02/2000", "473.296.280-77", passaport, Nacionalidade.BRASILEIRO, "teste@teste.com"
        , "https://teste.net");
    }
}

