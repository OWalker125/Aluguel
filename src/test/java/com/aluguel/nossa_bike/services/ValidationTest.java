package com.aluguel.nossa_bike.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aluguel.nossa_bike.models.*;
import com.aluguel.nossa_bike.models.Ciclista.*;
import com.aluguel.nossa_bike.repository.CiclistaRepository;


public class ValidationTest {

    @InjectMocks
    ValidationService cadastro = new ValidationService();

    @Mock
    CiclistaRepository dbCiclista;
    static Passaport passaport;
    static Ciclista ciclista;


    @AfterAll
    static void testt(){
        System.out.println("OFF");
    }
    
    @BeforeEach
	public void setUp() {
        MockitoAnnotations.openMocks(this);
	}

    @BeforeAll
	static public void setUpCiclista() {
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        passaport = new Passaport(1,1, "01/12/2001", "Brasil");
        ciclista = new Ciclista(id, "Thiago", Status.INATIVO, "23/02/2000", "473.296.280-77", passaport, Nacionalidade.BRASILEIRO, "teste@teste.com"
        , "https://teste.net");
	}

    @Test
    public void whenIsEmailValidThenReturnTrue(){
        List<Ciclista> listaVazia = new LinkedList<Ciclista>();
        doReturn(listaVazia).when(dbCiclista).findByEmailUser(anyString());
        //when(dbCiclista.findByEmailUser(anyString()).isEmpty()).thenReturn(true);

        String email = ciclista.getEmailUser();
        assertEquals(true,cadastro.isValidEmailUser(email));
    }

    @Test
    public void whenIsNameValidThenReturnTrue(){
        
        String name = ciclista.getNome();
        assertEquals(true,cadastro.isValidName(name));
    }

    @Test
    public void whenIsStatusValidThenReturnTrue(){
        
        Status status = ciclista.getStatus();
        assertEquals(true,cadastro.isInativeStatus(status));
    }

    @Test
    public void whenIsValidNascThenReturnTrue(){
        
        String nascimento = ciclista.getNascimento();
        assertEquals(true,cadastro.isValidDate(nascimento));
    }

    @Test
    public void whenIsValidValThenReturnTrue(){
        
        String validade = passaport.getValidade();
        assertEquals(true,cadastro.isValidDate(validade));
    }

    @Test
    public void whenIsValidCpfThenReturnTrue(){
        
        String cpf = ciclista.getCpf();
        assertEquals(true,cadastro.isValidCpf(cpf));
    }

    @Test
    public void whenIsValidPassaportThenReturnTrue(){
        
        Passaport passaportTest = ciclista.getPassaport();
        assertEquals(true,cadastro.isValidPassaport(passaportTest));
    }

    @Test
    public void whenIsValidNacThenReturnTrue(){
        
        Nacionalidade nacionalidade = ciclista.getNacionalidade();
        assertEquals(true,cadastro.isValidNac(nacionalidade));
    }

    @Test
    public void whenIsValidUrlThenReturnTrue(){
        
        String urlFotoDocumento = ciclista.getUrlFotoDocumento();
        assertEquals(true,cadastro.isValidUri(urlFotoDocumento));
    }

    @Test
    public void whenIsValidThenReturnTrue(){
        
        List<String> resposta = cadastro.isValid(ciclista);
        List<String> zeroErros = new LinkedList<>();
        assertEquals(zeroErros,resposta);
    }

    private void startCiclista(){
      
    }
}

