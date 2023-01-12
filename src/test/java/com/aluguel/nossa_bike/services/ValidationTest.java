package com.aluguel.nossa_bike.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aluguel.nossa_bike.models.*;
import com.aluguel.nossa_bike.models.Ciclista.Nacionalidade;
import com.aluguel.nossa_bike.models.Ciclista.AccountStatus;
import com.aluguel.nossa_bike.repository.CiclistaRepository;

public class ValidationTest {

    @Mock
    CiclistaRepository dbCiclista;
    @Mock
    Ciclista ciclistaMock;
    @Mock
    ValidationService validatorMock;
    static Passaport passaport;
    static Ciclista ciclistaValid, ciclistaInvalid;

    @InjectMocks
    ValidationService validator = new ValidationService();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static public void setUpCiclista() {
        startCiclista();
    }

    static void startCiclista() {
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        passaport = new Passaport(UUID.randomUUID(), 1, "01/12/2001", "Brasil");
        ciclistaValid = new Ciclista(id, "Thiago", AccountStatus.INATIVO, "23/02/2000", "473.296.280-77", passaport,
                Nacionalidade.BRASILEIRO, "teste@teste.com", "https://teste.net");
        ciclistaInvalid = new Ciclista(id, "01Thiago", AccountStatus.ATIVO, "teste", "473.296.280-99", passaport,
                Nacionalidade.ESTRANGEIRO, "@teste@teste.com", "@[]");
    }

    @Test
    public void whenisEmailValidReturnTrue() {
        List<Ciclista> listaVazia = new LinkedList<Ciclista>();
        doReturn(listaVazia).when(dbCiclista).findByEmailUser(anyString());
        String email = ciclistaValid.getEmailUser();
        assertEquals(true, validator.isValidEmailCic(email));
    }

    @Test
    public void whenisEmailInvalidReturnFalse() {
        List<Ciclista> listaCheia = new LinkedList<Ciclista>();
        listaCheia.add(ciclistaValid);
        doReturn(listaCheia).when(dbCiclista).findByEmailUser(anyString());
        String email = ciclistaValid.getEmailUser();
        assertEquals(false, validator.isValidEmailCic(email));
    }

    @Test
    public void whenIsNameValidThenReturnTrue() {
        String name = ciclistaValid.getNome();
        assertEquals(true, validator.isValidName(name));
    }

    @Test
    public void whenIsNameInvalidThenReturnFalse() {
        String name = ciclistaInvalid.getNome();
        assertEquals(false, validator.isValidName(name));
    }

    @Test
    public void whenIsStatusValidThenReturnTrue() {
        AccountStatus status = ciclistaValid.getStatus();
        assertEquals(true, validator.isInativeStatus(status));
    }

    @Test
    public void whenIsStatusInvalidThenReturnFalse() {
        AccountStatus status = ciclistaInvalid.getStatus();
        assertEquals(false, validator.isInativeStatus(status));
    }

    @Test
    public void whenIsValidNascThenReturnTrue() {
        String nascimento = ciclistaValid.getNascimento();
        assertEquals(true, validator.isValidDate(nascimento));
    }

    @Test
    public void whenIsInvalidNascThenReturnFalse() {
        String nascimento = ciclistaInvalid.getNascimento();
        assertEquals(false, validator.isValidDate(nascimento));
    }

    @Test
    public void whenIsValidValThenReturnTrue() {
        String validade = passaport.getValidade();
        assertEquals(true, validator.isValidDate(validade));
    }

    @Test
    public void whenIsInvalidValThenReturnFalse() {
        String validade = "32/13/2099";
        assertEquals(false, validator.isValidDate(validade));
    }

    @Test
    public void whenIsValidCpfThenReturnTrue() {
        String cpf = ciclistaValid.getCpf();
        assertEquals(true, validator.isValidCpf(cpf));
    }

    @Test
    public void whenIsInvalidCpfThenReturnFalse() {
        String cpf = ciclistaInvalid.getCpf();
        assertEquals(false, validator.isValidCpf(cpf));
    }

    @Test
    public void whenIsValidPassaportThenReturnTrue() {
        Passaport passaportTest = ciclistaValid.getPassaport();
        assertEquals(true, validator.isValidPassaport(passaportTest));
    }

    @Test
    public void whenIsValidNacThenReturnTrue() {
        Nacionalidade nacionalidade = ciclistaValid.getNacionalidade();
        assertEquals(true, validator.isValidNac(nacionalidade));
    }

    @Test
    public void whenIsValidUrlThenReturnTrue() {
        String urlFotoDocumento = ciclistaValid.getUrlFotoDocumento();
        assertEquals(true, validator.isValidUri(urlFotoDocumento));
    }

    @Test
    public void whenIsInvalidUrlThenReturnFalse() {
        String urlFotoDocumento = ciclistaInvalid.getUrlFotoDocumento();
        assertEquals(false, validator.isValidUri(urlFotoDocumento));
    }

    @Test
    public void whenIsValidThenReturnNoError() {
        List<String> resposta = validator.isValidCic(ciclistaValid);
        List<String> zeroErros = new LinkedList<>();
        assertEquals(zeroErros, resposta);
    }

    @Test
    public void whenIsInvalidThenReturnError() {
        List<String> resposta = validator.isValidCic(ciclistaInvalid);
        List<String> erros = new LinkedList<>();
        erros.add("EmailUser inválido ou existente");
        erros.add("Formato de nome inválido");
        erros.add("Formato de CPF inválido");
        erros.add("URL de foto inválida");
        erros.add("Data de nascimento inválida");
        erros.add("Status só pode ser INATIVO");
        assertEquals(erros, resposta);
    }

    @Test
    public void whenAllNullThenZeroErrors() {
        List<String> erros = new LinkedList<>();
        when(ciclistaMock.getNome()).thenReturn(null);
        when(ciclistaMock.getCpf()).thenReturn(null);
        when(ciclistaMock.getNacionalidade()).thenReturn(null);
        when(ciclistaMock.getPassaport()).thenReturn(null);
        when(ciclistaMock.getUrlFotoDocumento()).thenReturn(null);

        assertEquals(erros, validator.isValidORNull(ciclistaMock));
    }

    @Test
    public void whenAllValidThenZeroErrors() {
        List<String> erros = new LinkedList<>();
        assertEquals(erros, validator.isValidORNull(ciclistaValid));
    }

    @Test
    public void whenAllInvalidAndNotNullThenError() {
        List<String> erros = new LinkedList<>();
        erros.add("Formato de nome inválido");
        erros.add("Formato de CPF inválido");
        erros.add("URL de foto inválida");

        assertEquals(erros, validator.isValidORNull(ciclistaInvalid));
    }
}
