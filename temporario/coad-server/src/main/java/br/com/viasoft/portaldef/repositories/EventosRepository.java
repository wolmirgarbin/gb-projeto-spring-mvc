package br.com.viasoft.portaldef.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Eventos;

public interface EventosRepository extends BaseRepository<Eventos, Long> {
	
	@Query("select evento from Eventos evento where evento.sequencial = :sequencial and evento.chave = :chave")
	Eventos findOneBySequencialAndChave(@Param("sequencial") Integer sequencial, @Param("chave") String chave);
	
}