package com.hugo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hugo.cursomc.domain.Pedido;
import com.hugo.cursomc.repositories.PedidoRepository;
import com.hugo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository dedidoRepo;
	
	public Pedido buscar(Integer id) {		
		Optional<Pedido> obj = dedidoRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public List<Pedido> listar() {		
		List<Pedido> obj = dedidoRepo.findAll();
		if(obj == null) {
			 throw new ObjectNotFoundException( "Objeto não encontrado ! , Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
}
