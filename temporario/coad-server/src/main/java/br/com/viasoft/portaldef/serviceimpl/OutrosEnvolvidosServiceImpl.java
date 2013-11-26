package br.com.viasoft.portaldef.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.OutrosEnvolvidos;
import br.com.viasoft.portaldef.repositories.OutrosEnvolvidosRepository;
import br.com.viasoft.portaldef.service.OutrosEnvolvidosService;

@Service
public class OutrosEnvolvidosServiceImpl implements OutrosEnvolvidosService {

	private static final long serialVersionUID = 1347481260417084073L;

	@Autowired
	private OutrosEnvolvidosRepository data;


	@Override
	public OutrosEnvolvidos save(OutrosEnvolvidos outrosEnvolvidos) {
		return data.save(outrosEnvolvidos);
	}
}