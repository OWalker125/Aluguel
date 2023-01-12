package com.aluguel.nossa_bike.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aluguel.nossa_bike.models.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findByEmailUser(String email);

    Funcionario getByMatricula(UUID matricula);

    void deleteByMatricula(UUID matricula);
}
