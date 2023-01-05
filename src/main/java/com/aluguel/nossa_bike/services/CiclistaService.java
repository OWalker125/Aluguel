package com.aluguel.nossa_bike.services;

import com.aluguel.nossa_bike.models.ActivationLog;
import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Email;
import com.aluguel.nossa_bike.models.Ciclista.Status;
import com.aluguel.nossa_bike.repository.ActivationRepository;
import com.aluguel.nossa_bike.repository.CiclistaRepository;

import java.time.LocalDateTime;
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

    public List<String> cadastrar(Ciclista ciclista) {
        List<String> erros = validador.isValid(ciclista);
        if (!erros.isEmpty()) {
            return erros;
        } else {
            dbCiclista.save(ciclista);
            Email email = new Email(ciclista.getEmailUser(),
                    "A Nossa-Bike te dá as boas-vindas, para ativar sua conta acesse <link>, caso não tenha feito o registro ignore esse email.");
            // new RestTemplate().postForEntity("http://localhost:3000/", email, null);
            return erros;
        }
    }

    public Ciclista activateAccount(UUID idCiclista) {
        try {
            Ciclista ciclista = dbCiclista.findById(idCiclista).get(0);
            if (ciclista.getStatus() == Status.ATIVO) {
                return ciclista;
            } else {
                dbCiclista.updateStatusToActive(idCiclista);
                ciclista = dbCiclista.findById(idCiclista).get(0);
                registerActivation(ciclista);
                return ciclista;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void registerActivation(Ciclista ciclista) {
        ActivationLog log = new ActivationLog(LocalDateTime.now(), ciclista.getEmailUser());
        dbLogActivation.save(log);
    }

    public List<String> editAccount(UUID idCiclista, Ciclista ciclista) {
        Ciclista cicToUpdate = dbCiclista.getByid(idCiclista);
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
}
