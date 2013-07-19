package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;

public interface DfeService extends Serializable  {

	List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter);

	boolean permiteDownload(String identificacao, Long id);
	
	Dfe findOneAndDocs(Long id);

}