package com.aluguel.nossa_bike.repository;

import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Ciclista.Status;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface CiclistaRepository extends JpaRepository<Ciclista, Integer> {
    List<Ciclista> findByNome(String nome);

    List<Ciclista> findByEmailUser(String email);

    List<Ciclista> findById(UUID id);

    Ciclista getByid(UUID id);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("UPDATE Ciclista c SET c.status = com.aluguel.nossa_bike.models.Ciclista$Status.ATIVO where c.id = ?1")
    int updateStatusToActive(UUID id);
}
