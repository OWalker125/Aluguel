package com.aluguel.nossa_bike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aluguel.nossa_bike.models.Devolucoes;

@Repository
public interface DevolucoesRepository extends JpaRepository<Devolucoes, Integer>{
    
}
