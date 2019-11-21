package com.hugo.creditrating.dto;

import java.io.Serializable;

import com.hugo.creditrating.domain.Estado;

public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;

	public EstadoDTO() {
	}

	public EstadoDTO(Estado obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}