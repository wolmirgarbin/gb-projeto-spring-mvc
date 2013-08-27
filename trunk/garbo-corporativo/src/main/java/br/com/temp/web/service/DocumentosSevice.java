package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Documentos;

public interface DocumentosSevice extends Serializable {

	Documentos findOne(Long id);
	
	Documentos findOne(String nome);

	List<Documentos> findByIdIn(Long[] id);
}
