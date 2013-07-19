package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.repositories.custom.DfeRepositoryCustom;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
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
	
}