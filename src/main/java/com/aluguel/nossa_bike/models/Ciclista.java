package com.aluguel.nossa_bike.models;

public class Ciclista {
    

    public enum Status {ATIVO,INATIVO, AGUARDANDO_CONFIRMACAO}
    public enum Nacionalidade {BRASILEIRO, ESTRANGEIRO}

    private int id;
    private String nome;
    private Status status;
    private String nascimento;
    private String cpf;
    private Passaport passaport;
    private Nacionalidade nacionalidade;
    private String email;
    private String urlFotoDocumento;

    public Ciclista(int id, String nome, Status status, String nascimento, String cpf, Passaport passaport, Nacionalidade nacionalidade, String email, String urlFotoDocumento){
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.passaport = passaport;
        this.nacionalidade = nacionalidade;
        this.email = email;
        this.urlFotoDocumento = urlFotoDocumento;
    }
    
    public int getId(){return this.id;}

    public String getNome(){return this.nome;}

    public Status getStatus(){return this.status;}

    public String getNascimento(){return this.nascimento;}

    public String getCpf(){return this.cpf;}
    
    public Passaport getPassaport(){return this.passaport;}

    public Nacionalidade getNacionalidade(){return this.nacionalidade;}

    public String getEmail(){return this.email;}

    public String getUrlFotoDocumento(){return this.urlFotoDocumento;}

    
}