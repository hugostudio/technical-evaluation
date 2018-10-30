package com.hugo.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hugo.cursomc.domain.ItemPedido;
import com.hugo.cursomc.domain.PagamentoComBoleto;
import com.hugo.cursomc.domain.Pedido;
import com.hugo.cursomc.domain.enums.EstadoPagamento;
import com.hugo.cursomc.repositories.ItemPedidoRepository;
import com.hugo.cursomc.repositories.PagamentoRepository;
import com.hugo.cursomc.repositories.PedidoRepository;
import com.hugo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido buscar(Integer id) {		
		Optional<Pedido> obj = pedidoRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( 
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public List<Pedido> listar() {		
		List<Pedido> obj = pedidoRepo.findAll();
		if(obj == null) {
			 throw new ObjectNotFoundException( "Objeto não encontrado ! , Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto ) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = pedidoRepo.save(obj);
		
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.buscar(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	}
}
