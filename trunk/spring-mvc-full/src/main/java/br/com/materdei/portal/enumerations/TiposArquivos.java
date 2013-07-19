package br.com.viasoft.portaldef.enumerations;

import lombok.Getter;

public enum TiposArquivos {

	NFE			("NFe"), 
	DANFE		("Danfe"), 
	CTE			("CTe"), 
	CC			("Carta de correção"), 
	CANC_NFE	("Cancelamento NFe"),
	OUTROS		("Outros arquivos não tratados no sistema");
	
	
	@Getter
	private String label;

	
	private TiposArquivos(String label) {
		this.label = label;
	}
}