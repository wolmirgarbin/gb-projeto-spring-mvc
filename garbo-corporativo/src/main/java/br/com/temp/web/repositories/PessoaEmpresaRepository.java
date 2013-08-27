package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.PessoaEmpresa;

public interface PessoaEmpresaRepository extends BaseRepository<PessoaEmpresa, Long> {
	
	PessoaEmpresa findByPessoaAndEmpresa(Pessoa pessoa, Empresa empresa);
	
	@Query("select pessoaEmpresa from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.empresa = :empresa order by pessoaEmpresa.pessoa.nome asc")
	List<PessoaEmpresa> findByEmpresa(@Param("empresa") Empresa empresa, Pageable pageable);
	
	@Query("select pessoaEmpresa from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.empresa = :empresa and pessoaEmpresa.pessoa.emailPrincipal is null order by pessoaEmpresa.pessoa.nome asc")
	List<PessoaEmpresa> findByEmpresaAndEmailIsNull(@Param("empresa") Empresa empresa);
	
}