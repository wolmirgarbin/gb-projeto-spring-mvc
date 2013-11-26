package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Contratante;

public interface ContratanteService extends Serializable {

	List<Contratante> findAll();

	Contratante findOne(Long id);

	Contratante save(Contratante contratante);

}