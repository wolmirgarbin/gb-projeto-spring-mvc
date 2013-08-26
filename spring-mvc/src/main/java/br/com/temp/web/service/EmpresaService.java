package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Empresa;

/**
 * 
 * @author wolmir
 *
 */
public interface EmpresaService extends Serializable {
	
	Empresa findOneJoinConfig();
	
	Empresa findOneByIdentificacao(String identificacao);
	
	List<Empresa> listaEmpresas(String[] conjuntoIdentificacao);

	boolean possuiVinculo(String emitIdentificacao, String destIdentificacao);
	
	Empresa findOne(Long id);
	
	List<Empresa> findAll();
	
	Empresa save(Empresa empresa);
	
}