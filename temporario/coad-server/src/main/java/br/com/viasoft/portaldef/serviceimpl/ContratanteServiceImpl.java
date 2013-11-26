package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.repositories.ContratanteRepository;
import br.com.viasoft.portaldef.service.ContratanteService;

@Service
public class ContratanteServiceImpl implements ContratanteService {

	private static final long serialVersionUID = 1347481260417084073L;

	@Autowired
	private ContratanteRepository data;


	@Override
	public List<Contratante> findAll() {
		return data.findAll();
	}

	@Override
	public Contratante findOne(Long id) {
		return data.findOne(id);
	}

	@Override
	public Contratante save(Contratante contratante) {
		return data.save(contratante);
	}

}