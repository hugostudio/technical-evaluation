package com.hugo.creditrating.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hugo.creditrating.domain.Categoria;
import com.hugo.creditrating.domain.Proposta;
import com.hugo.creditrating.domain.Estado;
import com.hugo.creditrating.domain.enums.EstadoCivil;
import com.hugo.creditrating.domain.enums.TipoSexo;
import com.hugo.creditrating.repositories.CategoriaRepository;
import com.hugo.creditrating.repositories.PropostaRepository;
import com.hugo.creditrating.repositories.EstadoRepository;

@Service
public class DBService {

	@Autowired private CategoriaRepository categoriaRepository;	
	@Autowired private EstadoRepository estadoRepository;	
	@Autowired private PropostaRepository PropostaRepository;	
	
	public DBService() {
	}
	
	public void instantiateDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Bricolagem");
		Categoria cat5 = new Categoria(null, "Farmacia");
		Categoria cat6 = new Categoria(null, "Infantil");
		Categoria cat7 = new Categoria(null, "Ferramentas");
		Categoria cat8 = new Categoria(null, "Decoração");
		Categoria cat9 = new Categoria(null, "Jardinagem");
		Categoria cat10 = new Categoria(null, "Perfumaria");			
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10));
		
		Estado est1 = new Estado("MG", "Minas Gerais");
		Estado est2 = new Estado("SP", "São Paulo");
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		Proposta cli1 = new Proposta(null, "Maria Silva", "641.798.245-98", 30, TipoSexo.FEMININO, EstadoCivil.SOLTEIRO, "SP", 0, 3700d);
		Proposta cli2 = new Proposta(null, "Hugo Leonardo", "070.112.747-31",43, TipoSexo.MASCULINO, EstadoCivil.DIVORCIADO, "RJ", 1, 6900d);		
		PropostaRepository.saveAll(Arrays.asList(cli1, cli2));		
		
	}

}
