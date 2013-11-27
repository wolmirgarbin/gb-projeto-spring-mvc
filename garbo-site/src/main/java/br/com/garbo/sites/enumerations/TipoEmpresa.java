package br.com.garbo.sites.enumerations;

import lombok.Getter;

@Getter
public enum TipoEmpresa {

	M("Matriz"), F("Filial");
	
	private String label;

	private TipoEmpresa(String label) {
		this.label = label;
	}
	
}