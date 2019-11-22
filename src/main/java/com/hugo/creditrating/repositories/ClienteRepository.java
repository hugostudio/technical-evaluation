package com.hugo.creditrating.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hugo.creditrating.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	@Transactional(readOnly=true)
	Cliente findByCpf(String cpf);
}
