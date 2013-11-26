package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.PessoaEmpresa;

public interface PessoaEmpresaService extends Serializable {
	
	PessoaEmpresa save(PessoaEmpresa pesEmp);

	PessoaEmpresa findByPessoaAndEmpresa(Pessoa pessoa, Empresa empresa);
	
	List<PessoaEmpresa> findByEmpresa(Empresa empresa, Pageable pageable);
	
	List<PessoaEmpresa> findByEmpresaAndEmailIsNull(Empresa empresa);

	PessoaEmpresa findOne(Long id);
}