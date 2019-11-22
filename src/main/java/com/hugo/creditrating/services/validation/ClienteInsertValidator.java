package com.hugo.creditrating.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hugo.creditrating.domain.Cliente;
import com.hugo.creditrating.dto.ClienteNewDTO;
import com.hugo.creditrating.repositories.ClienteRepository;
import com.hugo.creditrating.resources.exception.FieldMessage;
import com.hugo.creditrating.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(!BR.isValidCpf(objDto.getCpf())) {
			list.add(new FieldMessage("cpf", "CPF Inválido"));
		}
		
		Cliente cliAux = clienteRepository.findByCpf(objDto.getCpf());
		if(cliAux != null) {
			list.add(new FieldMessage("cpf", "CPF já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}