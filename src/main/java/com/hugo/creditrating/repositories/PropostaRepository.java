package com.hugo.creditrating.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hugo.creditrating.domain.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Integer> {
	@Transactional(readOnly=true)
	Proposta findByCpf(String cpf);
}
