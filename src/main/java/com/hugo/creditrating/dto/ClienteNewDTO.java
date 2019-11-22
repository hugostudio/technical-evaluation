package com.hugo.creditrating.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.hugo.creditrating.services.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	@NotEmpty(message="Preenchimento obrigatório")
	private String cpf;
	private Integer idade;
	private String sexo;
	private Integer estadoCivil;
	private String estado;
	private Integer qtdDependentes;
	private Double vlRenda;
	
	
	public ClienteNewDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(Integer estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getQtdDependentes() {
		return qtdDependentes;
	}

	public void setQtdDependentes(Integer qtdDependentes) {
		this.qtdDependentes = qtdDependentes;
	}

	public Double getVlRenda() {
		return vlRenda;
	}

	public void setVlRenda(Double vlRenda) {
		this.vlRenda = vlRenda;
	}

}
