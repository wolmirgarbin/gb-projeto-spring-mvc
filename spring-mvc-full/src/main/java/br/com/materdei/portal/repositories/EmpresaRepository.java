package br.com.viasoft.portaldef.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Empresa;


public interface EmpresaRepository extends BaseRepository<Empresa, Long> {
	
	@Query("select emp from Empresa emp inner join fetch emp.config where emp.id = :id")
	Empresa findOneJoinConfig(@Param("id") final Long id);
	
}