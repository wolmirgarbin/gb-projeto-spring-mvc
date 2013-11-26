package br.com.viasoft.portaldef.service;

import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.xmls.PessoaNotaTO;

/**
 *
 * @author wolmir
 *
 */
public interface EmpresaService extends Serializable {

	Empresa findOneJoinConfig();

	Empresa findOneByIdentificacao(String identificacao);

	List<Empresa> listaEmpresas(String[] conjuntoIdentificacao);

	Empresa possuiVinculo(String emitIdentificacao, String destIdentificacao, List<PessoaNotaTO> envolvidos);

	Empresa findOne(Long id);

	List<Empresa> findAll();

	Empresa save(Empresa empresa);

	List<Empresa> findOneByContratante(Contratante contratante);

}