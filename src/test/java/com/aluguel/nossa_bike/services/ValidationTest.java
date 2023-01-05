package com.aluguel.nossa_bike.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.aluguel.nossa_bike.models.*;
import com.aluguel.nossa_bike.models.Ciclista.*;
import com.aluguel.nossa_bike.repository.CiclistaRepository;

@SpringBootTest
public class ValidationTest {

    @Mock
    CiclistaRepository dbCiclista;
    static Passaport passaport;
    static Ciclista ciclistaValid, ciclistaInvalid;

    @InjectMocks
    ValidationService cadastro = new ValidationService();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static public void setUpCiclista() {
        startCiclista();
    }

    @Test
    public void whenisEmailValidReturnTrue() {
        List<Ciclista> listaVazia = new LinkedList<Ciclista>();
        doReturn(listaVazia).when(dbCiclista).findByEmailUser(anyString());
        String email = ciclistaValid.getEmailUser();
        assertEquals(true, cadastro.isValidEmailUser(email));
    }

    @Test
    public void whenisEmailInvalidReturnFalse() {
        List<Ciclista> listaCheia = new LinkedList<Ciclista>();
        listaCheia.add(ciclistaValid);
        doReturn(listaCheia).when(dbCiclista).findByEmailUser(anyString());
        String email = ciclistaValid.getEmailUser();
        assertEquals(false, cadastro.isValidEmailUser(email));
    }

    @Test
    public void whenIsNameValidThenReturnTrue() {
        String name = ciclistaValid.getNome();
        assertEquals(true, cadastro.isValidName(name));
    }

    @Test
    public void whenIsNameInvalidThenReturnFalse() {
        String name = ciclistaInvalid.getNome();
        assertEquals(false, cadastro.isValidName(name));
    }

    @Test
    public void whenIsStatusValidThenReturnTrue() {
        Status status = ciclistaValid.getStatus();
        assertEquals(true, cadastro.isInativeStatus(status));
    }

    @Test
    public void whenIsStatusInvalidThenReturnFalse() {
        Status status = ciclistaInvalid.getStatus();
        assertEquals(false, cadastro.isInativeStatus(status));
    }

    @Test
    public void whenIsValidNascThenReturnTrue() {
        String nascimento = ciclistaValid.getNascimento();
        assertEquals(true, cadastro.isValidDate(nascimento));
    }

    @Test
    public void whenIsInvalidNascThenReturnFalse() {
        String nascimento = ciclistaInvalid.getNascimento();
        assertEquals(false, cadastro.isValidDate(nascimento));
    }

    @Test
    public void whenIsValidValThenReturnTrue() {
        String validade = passaport.getValidade();
        assertEquals(true, cadastro.isValidDate(validade));
    }

    @Test
    public void whenIsInvalidValThenReturnFalse() {
        String validade = "32/13/2099";
        assertEquals(false, cadastro.isValidDate(validade));
    }

    @Test
    public void whenIsValidCpfThenReturnTrue() {
        String cpf = ciclistaValid.getCpf();
        assertEquals(true, cadastro.isValidCpf(cpf));
    }

    @Test
    public void whenIsInvalidCpfThenReturnFalse() {
        String cpf = ciclistaInvalid.getCpf();
        assertEquals(false, cadastro.isValidCpf(cpf));
    }

    @Test
    public void whenIsValidPassaportThenReturnTrue() {
        Passaport passaportTest = ciclistaValid.getPassaport();
        assertEquals(true, cadastro.isValidPassaport(passaportTest));
    }

    @Test
    public void whenIsValidNacThenReturnTrue() {
        Nacionalidade nacionalidade = ciclistaValid.getNacionalidade();
        assertEquals(true, cadastro.isValidNac(nacionalidade));
    }

    @Test
    public void whenIsValidUrlThenReturnTrue() {
        String urlFotoDocumento = ciclistaValid.getUrlFotoDocumento();
        assertEquals(true, cadastro.isValidUri(urlFotoDocumento));
    }

    @Test
    public void whenIsInvalidUrlThenReturnFalse() {
        String urlFotoDocumento = ciclistaInvalid.getUrlFotoDocumento();
        assertEquals(false, cadastro.isValidUri(urlFotoDocumento));
    }

    @Test
    public void whenIsValidThenReturnNoError() {
        List<String> resposta = cadastro.isValid(ciclistaValid);
        List<String> zeroErros = new LinkedList<>();
        assertEquals(zeroErros, resposta);
    }

    @Test
    public void whenIsInvalidThenReturnError() {
        List<String> resposta = cadastro.isValid(ciclistaInvalid);
        List<String> erros = new LinkedList<>();
            erros.add("emailUser inválido");
            erros.add("Formato de nome inválido");
            erros.add("Formato de CPF inválido");
            erros.add("URL de foto inválida");
            erros.add("Data de nascimento inválida");
            erros.add("Status só pode ser INATIVO");
        assertEquals(erros, resposta);
    }

    static void startCiclista() {
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        passaport = new Passaport(1, 1, "01/12/2001", "Brasil");
        ciclistaValid = new Ciclista(id, "Thiago", Status.INATIVO, "23/02/2000", "473.296.280-77", passaport, Nacionalidade.BRASILEIRO, "teste@teste.com", "https://teste.net"); 

        ciclistaInvalid = new Ciclista(id, "01Thiago", Status.ATIVO, "teste", "473.296.280-99", passaport, Nacionalidade.ESTRANGEIRO, "@teste@teste.com", "@[]");
    }
}
