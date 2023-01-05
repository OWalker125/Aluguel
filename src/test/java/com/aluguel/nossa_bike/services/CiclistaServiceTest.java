package com.aluguel.nossa_bike.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Passaport;
import com.aluguel.nossa_bike.models.Ciclista.Nacionalidade;
import com.aluguel.nossa_bike.models.Ciclista.Status;
import com.aluguel.nossa_bike.repository.ActivationRepository;
import com.aluguel.nossa_bike.repository.CiclistaRepository;

public class CiclistaServiceTest{
    @InjectMocks
    CiclistaService ciclistaFunctions;

    @Mock
    CiclistaService ciclistaFunctionsMock;
    @Mock
    CiclistaRepository dbCiclista;
    @Mock
    ActivationRepository dbLogActivation;
    @Mock
    ValidationService validator;

    static Ciclista ciclistaValid, ciclistaInvalid;
    static Passaport passaport;

    static void startCiclista() {
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        passaport = new Passaport(1, 1, "01/12/2001", "Brasil");
        ciclistaValid = new Ciclista(id, "Thiago", Status.INATIVO, "23/02/2000", "473.296.280-77", passaport,
                Nacionalidade.BRASILEIRO, "teste@teste.com", "https://teste.net");
        ciclistaInvalid = new Ciclista(id, "01Thiago", Status.ATIVO, "teste", "473.296.280-99", passaport,
                Nacionalidade.ESTRANGEIRO, "@teste@teste.com", "@[]");
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        startCiclista();
    }

    @Test
    public void whenAllValidThenReturnAEmptyList() {
        List<String> listaVazia = new LinkedList<String>();
        when(validator.isValid(ciclistaValid)).thenReturn(listaVazia);

        assertEquals(listaVazia, ciclistaFunctions.cadastrar(ciclistaValid));
    }

    @Test
    public void whenInvalidThenReturnAErrorList() {
        List<String> erro = new LinkedList<String>();
        erro.add("Formato de CPF inválido");
        when(validator.isValid(ciclistaValid)).thenReturn(erro);

        assertEquals(erro, ciclistaFunctions.cadastrar(ciclistaValid));
    }

    @Test
    public void whenStatusActiveOnlyReturn() {
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        when(dbCiclista.getByid(id)).thenReturn(ciclistaInvalid);
        assertEquals(ciclistaInvalid, ciclistaFunctions.activateAccount(id));
    }

    @Test
    public void whenNullOrValidNoError() {
        List<String> listaVazia = new LinkedList<>();
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        Ciclista ciclistaMix = new Ciclista(id, "Thiago", Status.ATIVO, "teste", "473.296.280-77", passaport,
                Nacionalidade.BRASILEIRO, "@teste@teste.com", "https://teste.net");
        when(dbCiclista.getByid(id)).thenReturn(ciclistaInvalid);
        when(validator.isValidORNull(ciclistaValid)).thenReturn(listaVazia);
        when(dbCiclista.save(ciclistaMix)).thenReturn(null);

        assertEquals(listaVazia, ciclistaFunctions.editAccount(id, ciclistaValid));
    }

    @Test
    public void whenNotNullAndInvalidError() {
        List<String> erro = new LinkedList<>();
        erro.add("Formato de CPF Inválido");
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        Ciclista ciclistaMix = new Ciclista(id, "Thiago", Status.ATIVO, "teste", "473.296.280-77", passaport,
                Nacionalidade.BRASILEIRO, "@teste@teste.com", "https://teste.net");
        when(dbCiclista.getByid(id)).thenReturn(ciclistaInvalid);
        when(validator.isValidORNull(ciclistaValid)).thenReturn(erro);
        when(dbCiclista.save(ciclistaMix)).thenReturn(null);

        assertEquals(erro, ciclistaFunctions.editAccount(id, ciclistaValid));
    }
}
