package com.buenoezandro.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buenoezandro.os.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	@Query(value = "SELECT p FROM Pessoa p WHERE p.cpf = :cpf")
	Pessoa findByCPF(@Param(value = "cpf") String cpf);

}
