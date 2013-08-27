package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Pessoa;

public interface PessoaRepository extends BaseRepository<Pessoa, Long> {
	
	Pessoa findByIdentificacao(String identificacao);
	
	@Query("select pessoaEmpresa.pessoa from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.pessoa.emailPrincipal is null and pessoaEmpresa.empresa = :empresa")
	List<Pessoa> findEmailIsNullOrderByNomeAsc(@Param("empresa") Empresa empresa);
	
	@Query("select pessoaEmpresa.pessoa from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.pessoa.emailPrincipal is not null and pessoaEmpresa.empresa = :empresa")
	List<Pessoa> findEmailIsNotNullOrderByNomeAsc(@Param("empresa") Empresa empresa);
	
	@Query("select pessoa from Pessoa pessoa")
	List<Pessoa> findPessoaOrderByNomeAsc(Pageable pageable);
	
	@Query("select count(pessoaEmpresa) from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.pessoa.emailPrincipal is null and pessoaEmpresa.empresa = :empresa")
	Long registrosSemEmail(@Param("empresa") Empresa empresa);
	
	@Query("select count(pessoaEmpresa) from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.empresa = :empresa")
	Long count(@Param("empresa") Empresa empresa);

	@Query("select pessoaEmpresa.pessoa from PessoaEmpresa pessoaEmpresa where pessoaEmpresa.pessoa.nome like ?2 and pessoaEmpresa.pessoa.identificacao = ?3")
	List<Pessoa> buscar(String nome, String identificacao, Pageable pageable);
}