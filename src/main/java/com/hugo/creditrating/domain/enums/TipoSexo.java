package com.hugo.creditrating.domain.enums;

public enum TipoSexo {
	FEMININO("F", "Feminino"), 
	MASCULINO("M", "Masculino");
	
	private String sigla;
	private String descricao;

	private TipoSexo(String sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoSexo toEnum(String sigla) {
		if (sigla == null) {
			return null;
		}
		for (TipoSexo x : TipoSexo.values()) {
			if (sigla.equals(x.getSigla())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Sigla inválida para o enum TipoSexo : " + sigla);
	}

}