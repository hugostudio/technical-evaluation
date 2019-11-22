package com.hugo.creditrating.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hugo.creditrating.domain.Proposta;
import com.hugo.creditrating.domain.enums.EstadoCivil;
import com.hugo.creditrating.domain.enums.TipoSexo;
import com.hugo.creditrating.dto.PropostaDTO;
import com.hugo.creditrating.dto.PropostaNewDTO;
import com.hugo.creditrating.repositories.PropostaRepository;
import com.hugo.creditrating.services.exceptions.DataIntegrityException;
import com.hugo.creditrating.services.exceptions.ObjectNotFoundException;

@Service
public class PropostaService {
	
	@Autowired private PropostaRepository PropostaRepo;
	
	public Proposta buscar(Integer id) {			
		Optional<Proposta> obj = PropostaRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Proposta.class.getName()));
	}
	
	public List<Proposta> listar() {		
		List<Proposta> obj = PropostaRepo.findAll();
		if(obj == null) {
			 throw new ObjectNotFoundException( "Objeto não encontrado ! , Tipo: " + Proposta.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Proposta insert(Proposta obj) {		
		obj.setId(null);
		obj = PropostaRepo.save(obj);
		return obj;
	}
	
	public Proposta update(Proposta obj) {
		Proposta newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return PropostaRepo.save(newObj);
	}
	

	public void delete(Integer id) {
		buscar(id);
		try {
			PropostaRepo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível exclui um Proposta que possui referências !");
		}		
	}
	
	public Page<Proposta> listPage(Integer page, Integer size, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return PropostaRepo.findAll(pageRequest);
	}
	
	public Proposta fromDTO(PropostaDTO objDTO) {
		return new Proposta(objDTO.getId(), objDTO.getNome(), null, null, null, null, null, null, null);
	}	
	
	public Proposta fromDTO(PropostaNewDTO objDTO) {	
		Proposta cli = new Proposta(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getIdade(), 
							TipoSexo.toEnum(objDTO.getSexo()), EstadoCivil.toEnum(objDTO.getEstadoCivil()), 
							objDTO.getEstado(), objDTO.getQtdDependentes(), objDTO.getVlRenda());
		return cli;
	}
	
	private void updateData(Proposta newObj, Proposta obj) {
		newObj.setNome(obj.getNome());
	}
	
}
