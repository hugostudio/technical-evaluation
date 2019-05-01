package com.hugo.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hugo.cursomc.domain.Cidade;
import com.hugo.cursomc.domain.Cliente;
import com.hugo.cursomc.domain.Endereco;
import com.hugo.cursomc.domain.enums.Perfil;
import com.hugo.cursomc.domain.enums.TipoCliente;
import com.hugo.cursomc.dto.ClienteDTO;
import com.hugo.cursomc.dto.ClienteNewDTO;
import com.hugo.cursomc.repositories.ClienteRepository;
import com.hugo.cursomc.repositories.EnderecoRepository;
import com.hugo.cursomc.security.UserSS;
import com.hugo.cursomc.services.exceptions.AuthorizationException;
import com.hugo.cursomc.services.exceptions.DataIntegrityException;
import com.hugo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired private BCryptPasswordEncoder passworEncoder;
	@Autowired private ClienteRepository clienteRepo;
	@Autowired private EnderecoRepository enderecoRepo;
	@Autowired private S3Service s3Service;
	
	public Cliente buscar(Integer id) {		
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
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
		enderecoRepo.saveAll(obj.getEnderecos());
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}	
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), passworEncoder.encode(objDTO.getSenha()) );
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid );
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		return s3Service.uploadFile(multipartFile);
	}
}
