package com.aluguel.nossa_bike.services;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aluguel.nossa_bike.models.*;
import com.aluguel.nossa_bike.models.Ciclista.*;
import com.aluguel.nossa_bike.models.dtos.*;
import com.aluguel.nossa_bike.models.dtos.BicicletaDTO.StatusBic;
import com.aluguel.nossa_bike.repository.CiclistaRepository;
import com.aluguel.nossa_bike.repository.FuncionarioRepository;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Service
public class ValidationService {
    @Autowired
    private CiclistaRepository dbCiclista;

    @Autowired
    private FuncionarioRepository dbFunc;

    public boolean isValidEmailCic(String emailUser) {
        if (emailUser != null && emailUser.length() > 0) {
            if (dbCiclista.findByEmailUser(emailUser).isEmpty()) {
                EmailValidator validator = EmailValidator.getInstance();
                if (validator.isValid(emailUser)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidEmailFunc(String emailUser) {
        if (emailUser != null && emailUser.length() > 0) {
            if (dbFunc.findByEmailUser(emailUser).isEmpty()) {
                EmailValidator validator = EmailValidator.getInstance();
                if (validator.isValid(emailUser)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidName(String nome) {
        if (nome != null && nome.length() > 0) {
            String expression = "^[A-Za-z]{1,15}(?:\\s[A-Za-z]{1,15})*$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nome);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidDate(String date) {
        if (date != null && date.length() > 0) {
            String expression = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(date);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
        if (!erros.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean isValidUri(String uri) {
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(uri)) {
            return true;
        }
        return false;
    }

    public boolean isValidNac(Nacionalidade nac) {
        if (!(nac.equals(Nacionalidade.BRASILEIRO) || nac.equals(Nacionalidade.ESTRANGEIRO))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInativeStatus(AccountStatus nac) {
        if (!nac.equals(AccountStatus.INATIVO)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidPassaport(Passaport passaport) {
        if (!isValidDate(passaport.getValidade())) {
            return false;
        } else if (!isValidName(passaport.getPais())) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidCardNumber(Long numero) {
        String numeroString = String.valueOf(numero);
        if (numeroString != null && numeroString.length() > 0) {
            String expression = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(numeroString);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidCardCvv(int cvv) {
        String cvvString = String.valueOf(cvv);
        if (cvvString != null && cvvString.length() > 0) {
            String expression = "^[0-9]{3,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(cvvString);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public List<String> isValidCart(Cartao cartao) {
        LinkedList<String> erros = new LinkedList<>();
        if (!isValidCardNumber(cartao.getNumero())) {
            erros.add("Número de cartão inválido");
        }
        if (!isValidCardCvv(cartao.getCvv())) {
            erros.add("Número de cvv");
        }
        if (!isValidDate(cartao.getValidade())) {
            erros.add("Data de validade inválida");
        }
        return erros;
    }

    // Já que não sei se deveria levar em consideração questões legais de minimo e
    // teto de idade apenas não deixei cadastrar idade negativa
    public boolean isValidAge(int idade) {
        return idade >= 0;
    }

    public List<String> isValidCic(Ciclista ciclista) {
        LinkedList<String> erros = new LinkedList<>();
        if (!isValidEmailCic(ciclista.getEmailUser())) {
            erros.add("EmailUser inválido ou existente");
        }
        if (!isValidName(ciclista.getNome())) {
            erros.add("Formato de nome inválido");
        }
        if (!isValidCpf(ciclista.getCpf())) {
            erros.add("Formato de CPF inválido");
        }
        if (!isValidUri(ciclista.getUrlFotoDocumento())) {
            erros.add("URL de foto inválida");
        }
        if (!isValidDate(ciclista.getNascimento())) {
            erros.add("Data de nascimento inválida");
        }
        if (!isValidNac(ciclista.getNacionalidade())) {
            erros.add("Nacionalidade desconhecida");
        }
        if (!isValidPassaport(ciclista.getPassaport())) {
            erros.add("Passaport inválido");
        }
        if (!isInativeStatus(ciclista.getStatus())) {
            erros.add("Status só pode ser INATIVO");
        }
        return erros;
    }

    // Para a Edição de dados apenas será informados os dados que devem ser
    // modificados, portanto apenas será gerado um erro se o dado existir e estiver
    // errado
    public List<String> isValidORNull(Ciclista ciclista) {
        LinkedList<String> erros = new LinkedList<>();
        if ((ciclista.getNome() != null)) {
            if (!isValidName(ciclista.getNome())) {
                erros.add("Formato de nome inválido");
            }
        }
        if (!(ciclista.getCpf() == null)) {
            if (!isValidCpf(ciclista.getCpf())) {
                erros.add("Formato de CPF inválido");
            }
        }
        if (!(ciclista.getUrlFotoDocumento() == null)) {
            if (!isValidUri(ciclista.getUrlFotoDocumento())) {
                erros.add("URL de foto inválida");
            }
        }
        if (!(ciclista.getNacionalidade() == null)) {
            if (!isValidNac(ciclista.getNacionalidade())) {
                erros.add("Nacionalidade desconhecida");
            }
        }
        if (!(ciclista.getPassaport() == null)) {
            if (!isValidPassaport(ciclista.getPassaport())) {
                erros.add("Passaport inválido");
            }
        }
        return erros;
    }

    public List<String> isValidFunc(Funcionario funcionario) {
        LinkedList<String> erros = new LinkedList<>();
        if (!isValidEmailFunc(funcionario.getEmailUser())) {
            erros.add("emailUser inválido");
        }
        if (!isValidName(funcionario.getNome())) {
            erros.add("Formato de nome inválido");
        }
        if (!isValidCpf(funcionario.getCpf())) {
            erros.add("Formato de CPF inválido");
        }
        if (!isValidAge(funcionario.getIdade())) {
            erros.add("Idade Inválida");
        }
        return erros;
    }

    public boolean isValidTranca(String idTranca) {
        try {
            return new RestTemplate().getForEntity("/tranca/" + idTranca, TrancaDTO.class) != null;
        } catch (Exception e) {
            return false;
        }
        // return true;
    }

    public boolean isBicAvailable(String idTranca) {
        ResponseEntity<BicicletaDTO> response = new RestTemplate()
                .getForEntity("/tranca/" + idTranca + "/bicicleta", BicicletaDTO.class);
        BicicletaDTO bicicleta = response.getBody();
        if (bicicleta != null) {
            if (bicicleta.getStatusBic() == StatusBic.DISPONIVEL) {
                return true;
            }
        }
        return false;
    }

    public String isValidToRent(AluguelDTO aluguelDTO) {
        String idTranca = aluguelDTO.getTrancaInicio().toString();
        UUID idCiclista = aluguelDTO.getCiclista();
        if (!isValidTranca(idTranca)) {
            return "Tranca inválida";
        }
        if (!isBicAvailable(idTranca)) {
            return "Bicicleta Indisponível ou inexistente";
        }
        if (!hasChargeCompleted(idCiclista)) {
            return "Houve algum erro durante a cobrança";
        }
        return null;
    }

    public boolean hasChargeCompleted(UUID idCiclista) {
        NovaCobrancaDTO cobranca = new NovaCobrancaDTO();
        cobranca.setValor(10);
        cobranca.setCiclista(idCiclista);
        return new RestTemplate().postForEntity("/cobranca", cobranca, CobrancaDTO.class)
                .getStatusCode() == HttpStatus.OK;
    }
}
