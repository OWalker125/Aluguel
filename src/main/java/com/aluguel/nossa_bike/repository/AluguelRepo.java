package com.aluguel.nossa_bike.repository;

import java.util.LinkedList;
import java.util.ListIterator;

import com.aluguel.nossa_bike.models.Ciclista;


public class AluguelRepo{
    public void save(Ciclista client){
        LinkedList<Ciclista> lista = new LinkedList<>();
        lista.add(client);
    }
    public boolean isExistingEmail(String email){
        LinkedList<Ciclista> lista = new LinkedList<>();
        ListIterator<Ciclista> i = lista.listIterator(0);
        while(i.hasNext()){
            if(i.next().getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}
