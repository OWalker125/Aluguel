package com.aluguel.nossa_bike.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aluguel.nossa_bike.models.Alugueis;

@Repository
public interface AlugueisRepository extends JpaRepository<Alugueis, Integer>{
   @Query("SELECT a FROM Alugueis a WHERE a.bicicleta = ?1 and dataHora = (SELECT MAX(dataHora) FROM Alugueis b WHERE b.bicicleta = ?1)")
   Alugueis getNewestById(UUID idBicicleta);
}
