package com.hugo.creditrating.domain.enums;

public enum SituacaoCredito {    
	PENDENTE(1, "Pendente"),  
	NEGADO(2, "Negado"),  
	NEGADO_RB(3, "Negado renda baixa"),  
	REPROVADO(4, "Reprovado pela política de crédito"),  	
	APROVADO(5, "Aprovado"); 
	 
	 private int cod;  
	 private String descricao;    
	 
	 private SituacaoCredito(int cod, String descricao) {   
		 this.cod = cod;
		 this.descricao = descricao;
	 } 
	 	 
	 public int getCod() {   
		 return cod;  
	 }

	 public String getDescricao() {   
		 return descricao;
	 }
	 
	 public static SituacaoCredito toEnum(Integer id) {                  
		 if (id == null) {             
			 return null;         
		 }           
		 for (SituacaoCredito x : SituacaoCredito.values()) {             
			 if (id.equals(x.getCod())) {                 
				 return x;             
			 }        
		 }         		 
		 throw new IllegalArgumentException("Id inválido para o enum EstadoPagamento : " + id);     
	 }
}