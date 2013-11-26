package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.UsuarioEmpresa;

public interface UsuarioEmpresaService extends Serializable {

	UsuarioEmpresa save(UsuarioEmpresa usuarioEmpresa);

	void deleteVinculo(Long empresa, Long usuario);

	List<UsuarioEmpresa> findByEmpresa(Long empresa);

	List<UsuarioEmpresa> findByEmpresaAndUsuario(Long empresa, Long usuario);

}