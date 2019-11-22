package com.hugo.creditrating.domain.enums;

public enum EstadoCivil {
	SOLTEIRO(1, "Solteiro(a)"), 
	CASADO(2, "Casado(a)"),
	DIVORCIADO(3, "Divorciado(a)"),
	VIUVO(4, "Viúvo(a)"),
	SEPARADO(5, "Separado(a)");
	
	private Integer id;
	private String descricao;

	private EstadoCivil(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoCivil toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (EstadoCivil x : EstadoCivil.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}
		throw new IllegalArgumentException("ID inválido para o enum EstadoCivil : " + id);
	}

}