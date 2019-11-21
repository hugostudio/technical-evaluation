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

import com.hugo.creditrating.domain.Cliente;
import com.hugo.creditrating.dto.ClienteDTO;
import com.hugo.creditrating.dto.ClienteNewDTO;
import com.hugo.creditrating.repositories.ClienteRepository;
import com.hugo.creditrating.services.exceptions.DataIntegrityException;
import com.hugo.creditrating.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired private ClienteRepository clienteRepo;
	
	public Cliente buscar(Integer id) {			
		Optional<Cliente> obj = clienteRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> listar() {		
		List<Cliente> obj = clienteRepo.findAll();
		if(obj == null) {
			 throw new ObjectNotFoundException( "Objeto não encontrado ! , Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {		
		obj.setId(null);
		obj = clienteRepo.save(obj);
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return clienteRepo.save(newObj);
	}
	

	public void delete(Integer id) {
		buscar(id);
		try {
			clienteRepo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível exclui um Cliente que possui Pedidos !");
		}		
	}
	
	public Page<Cliente> listPage(Integer page, Integer size, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return clienteRepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), null, null, null, null, null, null);
	}	
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {	
		Cliente cli = new Cliente(null, objDTO.getNome(), null, null, null, null, null, null);
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
	}
	
}
