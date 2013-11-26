package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.repositories.UsuarioEmpresaRepository;
import br.com.viasoft.portaldef.service.UsuarioEmpresaService;

@Service
public class UsuarioEmpresaServiceImpl implements UsuarioEmpresaService {

	private static final long serialVersionUID = 1347481260417084073L;

	@Autowired
	private UsuarioEmpresaRepository data;



	@Override
	public UsuarioEmpresa save(UsuarioEmpresa usuarioEmpresa) {
		return data.save(usuarioEmpresa);
	}

	@Override
	public void deleteVinculo(Long empresa, Long usuario) {
		data.deleteVinculo(empresa, usuario);
	}

	@Override
	public List<UsuarioEmpresa> findByEmpresa(Long empresa) {
		return data.findByEmpresa(empresa);
	}

	@Override
	public List<UsuarioEmpresa> findByEmpresaAndUsuario(Long empresa, Long usuario) {
		return data.findByEmpresaAndUsuario(empresa, usuario);
	}
}
