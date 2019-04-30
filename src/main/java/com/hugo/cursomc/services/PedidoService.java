package com.hugo.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hugo.cursomc.domain.Cliente;
import com.hugo.cursomc.domain.ItemPedido;
import com.hugo.cursomc.domain.PagamentoComBoleto;
import com.hugo.cursomc.domain.Pedido;
import com.hugo.cursomc.domain.enums.EstadoPagamento;
import com.hugo.cursomc.repositories.ItemPedidoRepository;
import com.hugo.cursomc.repositories.PagamentoRepository;
import com.hugo.cursomc.repositories.PedidoRepository;
import com.hugo.cursomc.security.UserSS;
import com.hugo.cursomc.services.exceptions.AuthorizationException;
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
	
	@Autowired private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
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
	
	@Transactional
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
			ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		List<ItemPedido> lstItemPedido = itemPedidoRepository.saveAll(obj.getItens());
		obj.setItens(lstItemPedido.stream().collect(Collectors.toSet()));
		obj.setCliente(clienteService.buscar(obj.getCliente().getId()));
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	


	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.buscar(user.getId());
		return pedidoRepo.findByCliente(cliente, pageRequest);
	}
	
}
