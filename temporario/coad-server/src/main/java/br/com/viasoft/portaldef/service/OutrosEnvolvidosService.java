package br.com.viasoft.portaldef.service;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.OutrosEnvolvidos;

public interface OutrosEnvolvidosService extends Serializable {
	
	OutrosEnvolvidos save(OutrosEnvolvidos outrosEnvolvidos);
	
}