package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.web.to.FiltroPessoaTO;

public interface PessoaSevice extends Serializable {

	List<Pessoa> findByEmailIsNullOrderByNomeAsc();
	
	List<Pessoa> findByEmailIsNotNullOrderByNomeAsc();
	
	List<Pessoa> findByAtivoOrderByNomeAsc(SimNao ativo,Pageable pageable);
	
	Long registrosSemEmail();
	
	Pessoa findOne(Long id);
	
	Pessoa save(Pessoa p);
	
	Long count();
	
	Long count(FiltroPessoaTO to);

	List<Pessoa> buscar(FiltroPessoaTO to);
}
