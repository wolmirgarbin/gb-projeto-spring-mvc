package br.com.viasoft.portaldef.service;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.Config;

public interface ConfigService extends Serializable {
	
	Config save(Config config);
	
}