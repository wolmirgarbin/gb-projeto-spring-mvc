package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.viasoft.portaldef.entities.Documentos;

public interface DocumentosRepository extends BaseRepository<Documentos, Long> {
	
	Documentos findByNome(String nome);
	
	@Query("select doc from Documentos doc where doc.id in ?1")
	List<Documentos> findByIdIn(Long[] id);
	
}