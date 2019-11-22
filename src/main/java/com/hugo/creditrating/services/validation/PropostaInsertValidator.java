package com.hugo.creditrating.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hugo.creditrating.domain.Proposta;
import com.hugo.creditrating.dto.PropostaNewDTO;
import com.hugo.creditrating.repositories.PropostaRepository;
import com.hugo.creditrating.resources.exception.FieldMessage;
import com.hugo.creditrating.services.validation.utils.BR;

public class PropostaInsertValidator implements ConstraintValidator<PropostaInsert, PropostaNewDTO> {
	
	@Autowired
	private PropostaRepository PropostaRepository;
	
	@Override
	public void initialize(PropostaInsert ann) {
	}

	@Override
	public boolean isValid(PropostaNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(!BR.isValidCpf(objDto.getCpf())) {
			list.add(new FieldMessage("cpf", "CPF Inválido"));
		}
		
		Proposta cliAux = PropostaRepository.findByCpf(objDto.getCpf());
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