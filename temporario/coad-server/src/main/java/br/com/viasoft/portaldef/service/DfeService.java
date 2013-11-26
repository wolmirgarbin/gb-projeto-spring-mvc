package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.web.to.EmpresaTO;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.FilterDownloadGrupoTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;

public interface DfeService extends Serializable  {

	List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter);

	boolean permiteDownload(String identificacao, Long id);

	Dfe findOneAndDocs(Long id);

	List<Documentos> listDocDownload(FilterDownloadGrupoTO to);

	List<EmpresaTO> relConsumoMes(Integer mes, Integer ano, Long contratante);

	List<Dfe> findByRestIntegracao(String cnpjDestinatario);

	List<Dfe> findByRestIntegracao(String cnpjDestinatario, String cnpjEmitente);

	Dfe findOneByChaveJoinNfe(String chave);

	Dfe save(Dfe dfe);
}