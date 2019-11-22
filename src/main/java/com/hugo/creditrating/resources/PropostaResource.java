package com.hugo.creditrating.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hugo.creditrating.domain.Proposta;
import com.hugo.creditrating.dto.PropostaDTO;
import com.hugo.creditrating.dto.PropostaNewDTO;
import com.hugo.creditrating.services.PropostaService;

@RestController
@RequestMapping(value="/propostas")
public class PropostaResource {

	@Autowired
	private PropostaService PropostaService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Proposta> find(@PathVariable Integer id) {
		Proposta obj = PropostaService.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ResponseEntity<List<Proposta>> get() {
		List<Proposta> obj = PropostaService.listar();
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PropostaNewDTO objDTO) {
		Proposta obj = PropostaService.fromDTO(objDTO);
		obj = PropostaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody PropostaDTO objDTO, @PathVariable Integer id) {
		Proposta obj = PropostaService.fromDTO(objDTO);
		obj.setId(id);
		obj = PropostaService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		PropostaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PropostaDTO>> findAll() {
		List<Proposta> list = PropostaService.listar();
		List<PropostaDTO> listDto = list.stream().map(obj -> new PropostaDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<PropostaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="24") Integer size,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction ) {
		Page<Proposta> lista = PropostaService.listPage(page, size, orderBy, direction);
		Page<PropostaDTO> listaDTO = lista.map(obj -> new PropostaDTO(obj));
		return ResponseEntity.ok().body(listaDTO);
	}

}
