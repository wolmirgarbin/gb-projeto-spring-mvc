package br.com.viasoft.portaldef.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Usuario;


public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
	
	@Query("select u from Usuario u where u.usuario = :usuario and u.senha = :senha and u.senha <> '*****'")
	Usuario findByUsuarioAndSenha(@Param("usuario") final String usuario, @Param("senha") final String senha);

	
	Usuario findByUsuario(String usuario);
}