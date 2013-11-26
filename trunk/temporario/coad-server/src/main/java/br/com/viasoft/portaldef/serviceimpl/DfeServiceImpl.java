package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.repositories.custom.DfeRepositoryCustom;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.web.to.EmpresaTO;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.FilterDownloadGrupoTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;

@Service
public class DfeServiceImpl implements DfeService {

	private static final long serialVersionUID = 1347481260417084073L;

	@Autowired
	private DfeRepositoryCustom data;

	@Override
	public List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter) {
		return data.findByFilter(usuario, filter);
	}

	@Override
	public boolean permiteDownload(String identificacao, Long id) {
		return data.permiteDownload(identificacao, id);
	}

	@Override
	public Dfe findOneAndDocs(Long id) {
		return data.findOneAndDocs(id);
	}

	@Override
	public List<Documentos> listDocDownload(FilterDownloadGrupoTO to) {
		return data.listDocDownload(to);
	}

	@Override
	public List<EmpresaTO> relConsumoMes(Integer mes, Integer ano, Long contratante) {
		return data.relConsumoMes(mes, ano, contratante);
	}

	@Override
	public List<Dfe> findByRestIntegracao(String cnpjDestinatario) {
		return data.findByRestIntegracao(cnpjDestinatario);
	}

	@Override
	public List<Dfe> findByRestIntegracao(String cnpjDestinatario, String cnpjEmitente) {
		return data.findByRestIntegracao(cnpjDestinatario, cnpjEmitente);
	}

	@Override
	public Dfe findOneByChaveJoinNfe(String chave) {
		return data.findOneByChaveJoinNfe(chave);
	}

	@Override
	public Dfe save(Dfe dfe) {
		return data.merge(dfe);
	}

}