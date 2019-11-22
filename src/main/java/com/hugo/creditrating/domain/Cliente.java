package com.hugo.creditrating.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hugo.creditrating.domain.enums.EstadoCivil;
import com.hugo.creditrating.domain.enums.TipoSexo;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	@Column(unique=true)
	private String cpf;
	private Integer idade;
	private String sexo;
	private Integer estadoCivil;
	private String estado;
	private Integer qtdDependentes;
	private Double vlRenda;
	
	public Cliente() {
	}
	
	public Cliente(Integer id, String nome, String cpf, Integer idade, TipoSexo sexo, EstadoCivil estadoCivil, String estado,
			Integer qtdDependentes, Double vlRenda) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.idade = idade;
		this.sexo = (sexo == null) ? null : sexo.getSigla();
		this.estadoCivil = (estadoCivil == null) ? null : estadoCivil.getId();
		this.estado = estado;
		this.qtdDependentes = qtdDependentes;
		this.vlRenda = vlRenda;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return TipoSexo.toEnum(sexo).getDescricao();
	}

	public void setTipo(TipoSexo sexo) {
		this.sexo = sexo.getSigla();
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEstadoCivil() {
		return EstadoCivil.toEnum(estadoCivil).getDescricao();
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil.getId();
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

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}