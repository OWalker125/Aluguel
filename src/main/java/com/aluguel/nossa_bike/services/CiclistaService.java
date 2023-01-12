package com.aluguel.nossa_bike.services;

import com.aluguel.nossa_bike.models.*;
import com.aluguel.nossa_bike.models.Ciclista.*;
import com.aluguel.nossa_bike.models.Devolucoes.StatusFatura;
import com.aluguel.nossa_bike.models.dtos.*;
import com.aluguel.nossa_bike.models.dtos.BicicletaDTO.StatusBic;
import com.aluguel.nossa_bike.repository.ActivationRepository;
import com.aluguel.nossa_bike.repository.AlugueisRepository;
import com.aluguel.nossa_bike.repository.CartaoRepository;
import com.aluguel.nossa_bike.repository.CiclistaRepository;
import com.aluguel.nossa_bike.repository.DevolucoesRepository;
import com.aluguel.nossa_bike.repository.RentRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiclistaService {

    @Autowired
    private ValidationService validador;

    @Autowired
    private ActivationRepository dbLogActivation;

    @Autowired
    private CiclistaRepository dbCiclista;

    @Autowired
    private RentRepository dbRent;

    @Autowired
    private CartaoRepository dbCartao;

    @Autowired
    private AlugueisRepository dbAlugueis;

    @Autowired
    private DevolucoesRepository dbDevolucoes;

    public List<String> cadastrar(CicCartDTO cicCart) {
        Ciclista ciclista = cicCart.getCiclista();
        List<String> erros = validador.isValidCic(ciclista);
        Cartao cartao = cicCart.getCartao();
        cartao.setCiclista(ciclista);
        erros.addAll(validador.isValidCart(cartao));
        if (!erros.isEmpty()) {
            return erros;
        } else {
            dbCiclista.save(ciclista);
            dbCartao.save(cartao);
            AluguelStatus initRent = new AluguelStatus(ciclista);
            dbRent.save(initRent);
            Email email = new Email(ciclista.getEmailUser(),
                    "A Nossa-Bike te dá as boas-vindas, para ativar sua conta acesse <link>, caso não tenha feito o registro ignore esse email.");
            // new RestTemplate().postForEntity("http://localhost:3000/", email, null);
            return erros;
        }
    }

    public Ciclista activateAccount(UUID idCiclista) {
        try {
            Ciclista ciclista = dbCiclista.getByUuid(idCiclista);
            if (ciclista.getStatus() == AccountStatus.ATIVO) {
                return ciclista;
            } else {
                dbCiclista.updateStatusToActive(idCiclista);
                ciclista = dbCiclista.getByUuid(idCiclista);
                registerActivation(ciclista);
                return ciclista;
            }
        } catch (Exception e) {
            return null;
        }
    }

    void registerActivation(Ciclista ciclista) {
        ActivationLog log = new ActivationLog(LocalDateTime.now(), ciclista.getEmailUser());
        dbLogActivation.save(log);
    }

    public List<String> editAccount(UUID idCiclista, Ciclista ciclista) {
        Ciclista cicToUpdate = dbCiclista.getByUuid(idCiclista);
        List<String> erros = validador.isValidORNull(ciclista);
        if (!erros.isEmpty()) {
            return erros;
        } else {
            if (ciclista.getNome() != null) {
                cicToUpdate.setNome(ciclista.getNome());
            }
            if (ciclista.getCpf() != null) {
                cicToUpdate.setCpf(ciclista.getCpf());
            }
            if (ciclista.getNacionalidade() != null) {
                cicToUpdate.setNacionalidade(ciclista.getNacionalidade());
            }
            if (ciclista.getPassaport() != null) {
                cicToUpdate.setPassaport(ciclista.getPassaport());
            }
            if (ciclista.getUrlFotoDocumento() != null) {
                cicToUpdate.setUrlFotoDocumento(ciclista.getUrlFotoDocumento());
            }
            cicToUpdate.setId(idCiclista);

            dbCiclista.save(cicToUpdate);
            Email email = new Email(ciclista.getEmailUser(),
                    "Os dados da sua conta foram alterados com sucesso");
            // new RestTemplate().postForEntity("http://localhost:3000/", email, null);
            return erros;
        }
    }

    public List<String> editarCartao(UUID idCiclista, Cartao cartao) {
        Ciclista ciclista = dbCiclista.getByUuid(idCiclista);
        List<String> erros = new LinkedList<>();
        if (ciclista != null) {
            cartao.setCiclista(ciclista);
            if (validador.isValidCart(cartao).isEmpty()) {
                dbCartao.save(cartao);
            }
            return erros;
        }
        erros.add("Não encontrado");
        return erros;
    }

    public String gerarMenssagemAluguel(AluguelDTO aluguelDto, LocalDateTime hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String horaFormat = hora.format(formatter);
        String nome = dbCiclista.getByUuid(aluguelDto.getCiclista()).getNome();
        return "O(A) Sr(a). " + nome +
                " alugou com sucesso um bicicleta hoje às " + horaFormat +
                " pelo valor de R$10,00, isso lhe dá direito a usar a bicicleta por duas horas, lembre-se que em caso de atraso na entrega será cobrado um valor adicional.";
    }

    public String alugar(AluguelDTO aluguelDto) {
        String erro = validador.isValidToRent(aluguelDto);
        UUID idTranca = aluguelDto.getTrancaInicio();
        UUID idCiclista = aluguelDto.getCiclista();
        BicicletaDTO bicicleta = temporarioBicicleta();
        // BicicletaDTO bicicleta = new RestTemplate()
        // .getForEntity("http://localhost:8080/tranca/" + idTranca.toString() +
        // "/bicicleta", BicicletaDTO.class).getBody();
        UUID idBicicleta = bicicleta.getId();
        if (erro == null) {
            Cartao cartao = dbCartao.getByCiclista_Id(idCiclista);
            Ciclista ciclista = dbCiclista.getByUuid(idCiclista);
            // new RestTemplate().postForEntity("http://localhost:8080/tranca/" +
            // idTranca.toString() + "/destrancar", idBicicleta.toString(),
            // BicicletaDTO.class);
            LocalDateTime hora = LocalDateTime.now();
            // new RestTemplate().postForEntity("http://localhost:8080/enviarEmail/",
            // gerarMenssagemAluguel(aluguelDto, hora), null);
            Alugueis dadosAluguel = new Alugueis(null, hora, idTranca, idBicicleta, cartao, ciclista);
            dbAlugueis.save(dadosAluguel);
            return null;
        }
        return erro;
    }

    public String devolver(DevolucaoDTO devolucaoDto) {
        StatusFatura statusFatura = StatusFatura.PAGA;
        LocalDateTime devHora = LocalDateTime.now();
        String erro;
        UUID idTranca = devolucaoDto.getIdTranca();
        UUID idBicicleta = devolucaoDto.getIdBicicleta();
        Alugueis aluguel = dbAlugueis.getNewestById(devolucaoDto.getIdBicicleta());
        Ciclista ciclista = aluguel.getCiclista();
        Duration duracao = Duration.between(aluguel.getDataHora(), devHora);

        if (/*
             * new RestTemplate().getForEntity("/bicicleta/" + idBicicleta.toString(),
             * BicicletaDTO.class)
             * .getStatusCode() == HttpStatus.OK
             */true) {
            long taxas = taxCalc(duracao);
            if (taxas > 0) {
                NovaCobrancaDTO cobranca = new NovaCobrancaDTO(taxas, ciclista.getId());
                if (false/*
                          * new RestTemplate().postForEntity("/cobranca", cobranca, CobrancaDTO.class)
                          * .getStatusCode() != HttpStatus.OK
                          */) {
                    statusFatura = StatusFatura.ABERTA;
                } else {
                    taxas = 0;
                }
            }
            Devolucoes devolucao = new Devolucoes(idBicicleta, devHora, aluguel.getDataHora(), taxas, statusFatura,
                    idBicicleta, idTranca, aluguel.getCartao(), ciclista);
            dbDevolucoes.save(devolucao);
            return null;
        } else {
            erro = "bicicleta não encontrada";
            return erro;
        }
    }

    public long taxCalc(Duration tempo) {
        long resultado = 0;
        Duration duasHoras = Duration.ofHours(2);
        Duration meiaHora = Duration.ofMinutes(30);
        if (tempo.compareTo(duasHoras) > 0) {
            Duration passado = tempo.minus(duasHoras);
            long multasQntd = passado.dividedBy(meiaHora);
            resultado = multasQntd * 5;
        }
        return resultado;
    }

    public BicicletaDTO temporarioBicicleta() {
        BicicletaDTO bicicletaDTO = new BicicletaDTO(null, "Caloy", "Sei lá", "10/10/2010", 1, StatusBic.DISPONIVEL);
        return bicicletaDTO;
    }
}
