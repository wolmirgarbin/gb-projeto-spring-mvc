package br.com.viasoft.portaldef.repositories.custom;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.FilterDownloadGrupoTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;

public interface DfeRepositoryCustom extends Serializable {
	
	List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter);
	
	void updateDanf(Documentos danf, Dfe dfe);
	
	Dfe findOne(String chave);
	
	Dfe findOneAndDocs(Long id);
	
	boolean permiteDownload(String identificacao, Long id);
	
	Dfe merge(Dfe dfe);

	List<Documentos> listDocDownload(FilterDownloadGrupoTO to);
}