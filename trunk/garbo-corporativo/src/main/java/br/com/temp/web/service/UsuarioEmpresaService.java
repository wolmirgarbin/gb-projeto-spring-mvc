package br.com.viasoft.portaldef.service;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.UsuarioEmpresa;

public interface UsuarioEmpresaService extends Serializable {
	
	UsuarioEmpresa save(UsuarioEmpresa usuarioEmpresa);
	
}