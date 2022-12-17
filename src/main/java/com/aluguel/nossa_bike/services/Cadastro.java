package com.aluguel.nossa_bike.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Passaport;
import com.aluguel.nossa_bike.models.Ciclista.*;
import com.aluguel.nossa_bike.repository.AluguelRepo;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Service
public class Cadastro {

    AluguelRepo dataBase = new AluguelRepo();

    public boolean isValidEmail(String email){
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches() && !dataBase.isExistingEmail(email)){
                    return true;
            }
        }
        return false;
    }
    public boolean isValidName(String nome){
        if (nome != null && nome.length() > 0) {
            String expression = "[a-z]{1,15}\s|[a-z]{1,15}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nome);
            if (matcher.matches()) {
                return true;
            }
        }
            return false;
    }

    public boolean isValidDate(String date){
        if (date != null && date.length() > 0) {
            String expression = "^([0-2]\\d|(3)[0-1])(\\/)(((0)\\d)|((1)[0-2]))(\\/)\\d{4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(date);
            if (matcher.matches()) {
                return true;
            }
        }
            return false;
    }
    public boolean isValidCpf(String cpf){
        CPFValidator cpfValidator = new CPFValidator(); 
        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
        if(!erros.isEmpty()){
            return false;
        } 

        return true;
    }
    public boolean isValidUri(String uri){
        UrlValidator urlValidator = new UrlValidator();
        if(urlValidator.isValid(uri)){
            return true;
        }
        return false;
    }
    public boolean isValidNac(Nacionalidade nac){
        if(!nac.equals(Nacionalidade.BRASILEIRO) && !nac.equals(Nacionalidade.ESTRANGEIRO)){
            return false;
        } else {
            return true;
        }
    }
    public boolean isInativeStatus(Status nac){
        if(!nac.equals(Status.INATIVO)){
            return false;
        } else {
            return true;
        }
    }
    public boolean isValidPassaport(Passaport passaport){
        if(!isValidDate(passaport.getValidade())){
            return false;
        } else if(!isValidName(passaport.getPais())){
            return false;
        } else {
        return true;
        }
    }
    public boolean isValidCreditCard(){
        return true;
    }

    public List<String> isValid(Ciclista ciclista){
        LinkedList<String> erros = new LinkedList<>();
        if(!isValidEmail(ciclista.getEmail())){
            erros.add("Email inválido");
        } 
        if(!isValidName(ciclista.getNome())){
            erros.add("Formato de nome inválido");
        } 
        if(!isValidCpf(ciclista.getCpf())){
            erros.add("Formato de CPF inválido");
        } 
        if(!isValidUri(ciclista.getUrlFotoDocumento())){
            erros.add("URL de foto inválida");
        } 
        if(!isValidDate(ciclista.getNascimento())){
            erros.add("Data de nascimento inválida");
        } 
        if(!isValidNac(ciclista.getNacionalidade())){
            erros.add("Nacionalidade desconhecida");
        } 
        if(!isValidPassaport(ciclista.getPassaport())){
            erros.add("Passaport inválido");
        } 
        if(!isInativeStatus(ciclista.getStatus())){
            erros.add("Status só pode ser INATIVO");
        } 
        if(!isValidCreditCard()){
            erros.add("Apresente um cartão válido");
        }
        return erros;
    }

    public void sendVerificationMail(String email){
        Random aleatorio = new Random();

        //fornece um  codigo aleatório entre 100.000 e 200.000
        Long codigo = 100000 + aleatorio.nextLong(100001);
        return;
    }
}
