package br.com.viasoft.portaldef.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.enumerations.SimNao;

public interface PessoaRepository extends BaseRepository<Pessoa, Long> {
	
	Pessoa findByIdentificacao(String identificacao);
	
	List<Pessoa> findByEmailIsNullOrderByNomeAsc();
	
	List<Pessoa> findByEmailIsNotNullOrderByNomeAsc();
	
	List<Pessoa> findByAtivoOrderByNomeAsc(SimNao ativo, Pageable pageable);
	
	@Query("select count(pessoa) from Pessoa pessoa where pessoa.email is null")
	Long registrosSemEmail();
	
	@Query("select count(pessoa) from Pessoa pessoa where pessoa.ativo = :ativo")
	Long count(@Param("ativo") SimNao ativo);

	@Query("select pessoa from Pessoa pessoa where pessoa.ativo = ?1 and pessoa.nome like ?2 and pessoa.identificacao = ?3")
	List<Pessoa> buscar( SimNao ativo, String nome, String identificacao, Pageable pageable);
}