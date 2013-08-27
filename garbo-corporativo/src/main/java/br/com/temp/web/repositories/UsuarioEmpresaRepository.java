package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.SimNao;


public interface UsuarioEmpresaRepository extends BaseRepository<UsuarioEmpresa, Long> {
	
	@Query("select u from UsuarioEmpresa u where u.usuario.id = :usuario and u.padrao = :padrao")
	List<UsuarioEmpresa> findByUsuarioEmpresaPadrao(@Param("usuario") final Long usuario, @Param("padrao") final SimNao padrao);

}