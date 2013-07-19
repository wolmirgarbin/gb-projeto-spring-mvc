package br.com.viasoft.portaldef.service;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.Empresa;

/**
 * 
 * @author wolmir
 *
 */
public interface EmpresaService extends Serializable {
	
	Empresa findOneJoinConfig();
	
}