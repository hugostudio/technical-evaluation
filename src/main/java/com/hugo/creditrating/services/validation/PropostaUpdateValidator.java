package com.hugo.creditrating.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.hugo.creditrating.domain.Proposta;
import com.hugo.creditrating.dto.PropostaDTO;
import com.hugo.creditrating.repositories.PropostaRepository;
import com.hugo.creditrating.resources.exception.FieldMessage;

public class PropostaUpdateValidator implements ConstraintValidator<PropostaUpdate, PropostaDTO> {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private PropostaRepository PropostaRepository;
	
	@Override
	public void initialize(PropostaUpdate ann) {
	}

	@Override
	public boolean isValid(PropostaDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
			
		@SuppressWarnings("unchecked")
		Map <String, String> map = (Map <String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		Proposta cliAux = PropostaRepository.findByCpf(objDto.getCpf());
		if(cliAux != null && !cliAux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}