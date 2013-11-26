package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.enumerations.TipoEmpresa;


public interface EmpresaRepository extends BaseRepository<Empresa, Long> {

	@Query("select emp from Empresa emp where emp.tipo = :tipo")
	List<Empresa> findOne(@Param("tipo") final TipoEmpresa tipo, Pageable pageable);

	Empresa findOneByIdentificacao(String identificacao);

	@Query("select emp from Empresa emp where emp.identificacao = :identificacao01 or emp.identificacao = :identificacao02 or emp.identificacao in :envolvidos ")
	List<Empresa> findAllByIdentificacaoAll(@Param("identificacao01") String identificacao01, @Param("identificacao02") String identificacao02, @Param("envolvidos") List<String> envolvidos);
	
	@Query("select emp from Empresa emp where emp.identificacao = :identificacao01 or emp.identificacao = :identificacao02 ")
	List<Empresa> findAllByIdentificacao(@Param("identificacao01") String identificacao01, @Param("identificacao02") String identificacao02);

	@Query("select emp from Empresa emp where emp.identificacao in (?1)")
	List<Empresa> listaEmpresas(List<String> conjuntoIdentificacao);

	List<Empresa> findOneByContratante(Contratante contratante);

}