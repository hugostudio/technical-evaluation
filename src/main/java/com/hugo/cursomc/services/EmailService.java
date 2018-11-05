package com.hugo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.hugo.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationMail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}