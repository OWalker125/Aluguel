package com.aluguel.nossa_bike.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aluguel.nossa_bike.models.Alugueis;

@Repository
public interface AlugueisRepository extends JpaRepository<Alugueis, Integer>{
   // @Query("SELECT * FROM Alugueis WHERE bicicleta = ?1 AND dataHora = (select max(this.dataHora) FROM Alugueis)")
   // Alugueis getNewestById(UUID idBicicleta);
}
