package br.com.viasoft.portaldef.repositories.custom;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.web.to.FiltroPessoaTO;

public interface PessoaRepositoryCustom extends Serializable {
	
	List<Pessoa> buscar(FiltroPessoaTO to);
	
	Long count(FiltroPessoaTO to); 
	
}