package br.com.viasoft.portaldef.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.repositories.EmpresaRepository;
import br.com.viasoft.portaldef.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final long serialVersionUID = 1347481260417084073L;
	
	@Autowired
	private EmpresaRepository data;

	@Override
	public Empresa findOneJoinConfig() {
		// A empresa será sempre a numero 1
		return data.findOneJoinConfig(1L);
	}
		
}
