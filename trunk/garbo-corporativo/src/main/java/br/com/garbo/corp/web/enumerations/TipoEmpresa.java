package br.com.garbo.corp.web.enumerations;

import lombok.Getter;

@Getter
public enum TipoEmpresa {

	M("Matriz"), F("Filial");
	
	private String label;

	private TipoEmpresa(String label) {
		this.label = label;
	}
	
}