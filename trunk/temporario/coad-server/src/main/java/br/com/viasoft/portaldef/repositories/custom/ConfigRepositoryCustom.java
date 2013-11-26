package br.com.viasoft.portaldef.repositories.custom;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.Config;

public interface ConfigRepositoryCustom extends Serializable {

	Config getNextMailLeitura();

	void updateDtHrLeitura(Config email);

}