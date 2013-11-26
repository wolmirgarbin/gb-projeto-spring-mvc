package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Utc;

public interface UtcService extends Serializable {

	List<Utc> findAll();

	Date dateForUTC(String utc);

	Date dateForUTCEmpresa(Empresa empresa);

}