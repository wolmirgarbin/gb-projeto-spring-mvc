package br.com.viasoft.portaldef.service;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.Gerenciadores;

public interface GerenciadoresService extends Serializable {
	
	void logout();
	
	Gerenciadores getGerenciadores();
	
	Gerenciadores login(Gerenciadores gerenciadores);
	
	boolean isLogado();
}