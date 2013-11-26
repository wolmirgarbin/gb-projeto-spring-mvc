package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.SimNao;


public interface UsuarioEmpresaRepository extends BaseRepository<UsuarioEmpresa, Long> {

	@Query("select u from UsuarioEmpresa u where u.usuario.id = :usuario and u.padrao = :padrao")
	List<UsuarioEmpresa> findByUsuarioEmpresaPadrao(@Param("usuario") final Long usuario, @Param("padrao") final SimNao padrao);

	@Modifying
	@Transactional
	@Query("delete from UsuarioEmpresa u where u.empresa.id = :empresa and u.usuario.id = :usuario")
	void deleteVinculo(@Param("empresa") Long empresa, @Param("usuario") Long usuario);

	@Query("select u from UsuarioEmpresa u where u.empresa.id = :empresa")
	List<UsuarioEmpresa> findByEmpresa(@Param("empresa") Long empresa);

	@Query("select u from UsuarioEmpresa u where u.empresa.id = :empresa and u.usuario.id = :usuario")
	List<UsuarioEmpresa> findByEmpresaAndUsuario(@Param("empresa") Long empresa, @Param("usuario") Long usuario);

}