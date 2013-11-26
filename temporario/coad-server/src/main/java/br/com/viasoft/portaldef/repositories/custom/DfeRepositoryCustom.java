package br.com.viasoft.portaldef.repositories.custom;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.web.to.EmpresaTO;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.FilterDownloadGrupoTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;

public interface DfeRepositoryCustom extends Serializable {

	List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter);

	void updateDanf(Documentos danf, Dfe dfe);

	Dfe findOne(String chaveNFe, String chaveCTe);

	Dfe findOne(String chaveUnica);

	Dfe findOneAndDocs(Long id);

	boolean permiteDownload(String identificacao, Long id);

	Dfe merge(Dfe dfe);

	List<Documentos> listDocDownload(FilterDownloadGrupoTO to);

	/**
	 * Retorna a lista para o relatorio  de quantidade utilizada por Contratante
	 *
	 * - Usado na listagem da minha área e para enviar por més para o responsavél do cliente
	 *
	 * @param mes
	 * @param ano
	 * @param contratante
	 * @return
	 */
	List<EmpresaTO> relConsumoMes(Integer mes, Integer ano, Long contratante);

	List<Dfe> findByRestIntegracao(String cnpjDestinatario);

	List<Dfe> findByRestIntegracao(String cnpjDestinatario, String cnpjEmitente);

	Dfe findOneByChaveJoinNfe(String chave);

}