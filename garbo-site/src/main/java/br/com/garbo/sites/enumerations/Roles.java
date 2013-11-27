package br.com.garbo.sites.enumerations;

import lombok.Getter;

public enum Roles {

	ROLE_SUPERVISOR(1), 
	ROLE_ADMINISTRADOR(2), 
	ROLE_CLIENTE(3);

	@Getter
	private int prioridade;

	private Roles(int prioridade) {
		this.prioridade = prioridade;
	}
	
}