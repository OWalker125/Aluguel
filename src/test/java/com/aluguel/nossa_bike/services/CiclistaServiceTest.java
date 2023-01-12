package com.aluguel.nossa_bike.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.aluguel.nossa_bike.models.Alugueis;
import com.aluguel.nossa_bike.models.Cartao;
import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Passaport;
import com.aluguel.nossa_bike.models.Ciclista.Nacionalidade;
import com.aluguel.nossa_bike.models.dtos.AluguelDTO;
import com.aluguel.nossa_bike.models.dtos.BicicletaDTO;
import com.aluguel.nossa_bike.models.dtos.CicCartDTO;
import com.aluguel.nossa_bike.models.dtos.DevolucaoDTO;
import com.aluguel.nossa_bike.models.Ciclista.AccountStatus;
import com.aluguel.nossa_bike.repository.ActivationRepository;
import com.aluguel.nossa_bike.repository.AlugueisRepository;
import com.aluguel.nossa_bike.repository.CartaoRepository;
import com.aluguel.nossa_bike.repository.CiclistaRepository;
import com.aluguel.nossa_bike.repository.DevolucoesRepository;
import com.aluguel.nossa_bike.repository.RentRepository;

public class CiclistaServiceTest {

    @Mock
    CiclistaRepository dbCiclista;
    @Mock
    ActivationRepository dbLogActivation;
    @Mock
    AlugueisRepository dbAlugueis;
    @Mock
    DevolucoesRepository dbDevolucoes;
    @Mock
    ValidationService validator;
    @Mock
    CartaoRepository dbCartao;
    @Mock
    RentRepository dbRent;
    @Mock
    BicicletaDTO bicicleta;

    @Spy
    CiclistaService ciclistaFunctionsSpy;

    @InjectMocks
    CiclistaService ciclistaFunctions;

   

    static Ciclista ciclistaValid, ciclistaInvalid;
    static Passaport passaport;
    static Cartao cartao;

    static void startCiclista() {
        cartao = new Cartao(null, "Thiago", 9999999, "10/05/2020", 123, ciclistaValid);
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        passaport = new Passaport(UUID.randomUUID(), 1, "01/12/2001", "Brasil");
        ciclistaValid = new Ciclista(id, "Thiago", AccountStatus.INATIVO, "23/02/2000", "473.296.280-77", passaport,
                Nacionalidade.BRASILEIRO, "teste@teste.com", "https://teste.net");
        ciclistaInvalid = new Ciclista(id, "01Thiago", AccountStatus.ATIVO, "teste", "473.296.280-99", passaport,
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
        when(validator.isValid(ciclistaValid)).thenReturn(listaVazia);
        when(dbCartao.save(any())).thenReturn(null);
        when(dbRent.save(any())).thenReturn(null);
        when(dbCiclista.save(any())).thenReturn(null);

        CicCartDTO cicCartDTO = new CicCartDTO();
        cicCartDTO.setCiclista(ciclistaValid);
        cicCartDTO.setCartao(cartao);
        assertEquals(listaVazia, ciclistaFunctions.cadastrar(cicCartDTO));
    }

    @Test
    public void whenInvalidThenReturnAErrorList() {
        List<String> erro = new LinkedList<String>();
        erro.add("Formato de CPF inválido");
        when(validator.isValid(ciclistaValid)).thenReturn(erro);

        CicCartDTO cicCartDTO = new CicCartDTO();
        cicCartDTO.setCiclista(ciclistaValid);
        cicCartDTO.setCartao(cartao);
        assertEquals(erro, ciclistaFunctions.cadastrar(cicCartDTO));
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
        Ciclista ciclistaMix = new Ciclista(id, "Thiago", AccountStatus.ATIVO, "teste", "473.296.280-77", passaport,
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
        Ciclista ciclistaMix = new Ciclista(id, "Thiago", AccountStatus.ATIVO, "teste", "473.296.280-77", passaport,
                Nacionalidade.BRASILEIRO, "@teste@teste.com", "https://teste.net");
        when(dbCiclista.getByid(id)).thenReturn(ciclistaInvalid);
        when(validator.isValidORNull(ciclistaValid)).thenReturn(erro);
        when(dbCiclista.save(ciclistaMix)).thenReturn(null);

        assertEquals(erro, ciclistaFunctions.editAccount(id, ciclistaValid));
    }

    @Test
    public void whenValidThanRent(){
        String idTranca = "d76fdc5b-8066-4bcf-8a29-4dc7a80ba436";
        AluguelDTO aluguelDTO = new AluguelDTO();
        aluguelDTO.setCiclista(ciclistaValid.getId());
        aluguelDTO.setTrancaInicio(UUID.fromString(idTranca));

        when(validator.isValidToRent(any())).thenReturn(null);
        when(bicicleta.getId()).thenReturn(UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436"));
        when(dbCartao.getByCiclista_Id(any())).thenReturn(cartao);
        when(dbCiclista.getByid(any())).thenReturn(ciclistaValid);
        when(dbAlugueis.save(any())).thenReturn(null);

        assertEquals(null, ciclistaFunctions.alugar(aluguelDTO));
    }

    @Test
    public void whenInvalidThanNoRent(){
        String erro = "erro";
        String idTranca = "d76fdc5b-8066-4bcf-8a29-4dc7a80ba436";
        AluguelDTO aluguelDTO = new AluguelDTO();
        aluguelDTO.setCiclista(ciclistaValid.getId());
        aluguelDTO.setTrancaInicio(UUID.fromString(idTranca));

        when(validator.isValidToRent(any())).thenReturn(erro);
        when(bicicleta.getId()).thenReturn(UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436"));
        when(dbCartao.getByCiclista_Id(any())).thenReturn(cartao);
        when(dbCiclista.getByid(any())).thenReturn(ciclistaValid);
        when(dbAlugueis.save(any())).thenReturn(null);

        assertEquals(erro, ciclistaFunctions.alugar(aluguelDTO));
    }

    @Test
    public void whenValidAndNoTaxThanDevolution(){
        UUID id = UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436");
        DevolucaoDTO devolucaoDTO = new DevolucaoDTO(id, id);
        LocalDateTime agora = LocalDateTime.now();
        Alugueis aluguel = new Alugueis(null, agora, UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436"), UUID.fromString("d76fdc5b-8066-4bcf-8a29-4dc7a80ba436"), cartao, ciclistaInvalid);

        Mockito.doReturn((long)10).when(ciclistaFunctionsSpy).taxCalc(any());
        when(dbDevolucoes.save(any())).thenReturn(null);
        when(dbAlugueis.getNewestById(any())).thenReturn(aluguel);
        assertEquals(null, ciclistaFunctions.devolver(devolucaoDTO));
    }
}
