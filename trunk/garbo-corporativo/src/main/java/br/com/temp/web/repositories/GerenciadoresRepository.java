package br.com.viasoft.portaldef.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Gerenciadores;
import br.com.viasoft.portaldef.enumerations.SimNao;


public interface GerenciadoresRepository extends BaseRepository<Gerenciadores, Long> {
	
	@Query("select g from Gerenciadores g where g.usuario = :usuario and g.senha = :senha and g.ativo = :ativo")
	Gerenciadores findByUsuarioAndSenha(@Param("usuario") final String usuario, @Param("senha") final String senha, @Param("ativo") final SimNao ativo);

}