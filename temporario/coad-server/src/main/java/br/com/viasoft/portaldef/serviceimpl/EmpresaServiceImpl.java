package br.com.viasoft.portaldef.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.enumerations.TipoEmpresa;
import br.com.viasoft.portaldef.repositories.EmpresaRepository;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.xmls.PessoaNotaTO;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final long serialVersionUID = 1347481260417084073L;

	@Autowired
	private EmpresaRepository data;


	@Override
	public Empresa findOneJoinConfig() {
		final List<Empresa> findOneJoinConfig = data.findOne(TipoEmpresa.M, new PageRequest(0, 1));
		if( findOneJoinConfig != null ) {
			return findOneJoinConfig.get(0);
		}
		return null;
	}


	@Override
	public Empresa findOneByIdentificacao(String identificacao) {
		return data.findOneByIdentificacao(identificacao);
	}


	@Override
	public Empresa possuiVinculo(String emitIdentificacao, String destIdentificacao, List<PessoaNotaTO> envolvidos ) {
		
		List<String> identificadores = new ArrayList<String>();
		if( envolvidos != null ) {
			for (PessoaNotaTO pn : envolvidos) {
				identificadores.add(pn.getIdentificacao());
			}
		}
		
		List<Empresa> list = null;
		if( identificadores.size() > 0 ) {
			list = data.findAllByIdentificacaoAll(emitIdentificacao, destIdentificacao, identificadores);
		} else {
			list = data.findAllByIdentificacao(emitIdentificacao, destIdentificacao);
		}
		
		if( list != null && list.size() > 0 )
			return list.get(0);

		return null;
	}


	@Override
	public List<Empresa> listaEmpresas(String[] conjuntoIdentificacao) {
		if( conjuntoIdentificacao != null )
			return data.listaEmpresas(Arrays.asList(conjuntoIdentificacao));
		else
			return null;
	}


	@Override
	public List<Empresa> findAll() {
		return data.findAll();
	}


	@Override
	public Empresa findOne(Long id) {
		return data.findOne(id);
	}


	@Override
	public Empresa save(Empresa empresa) {
		return data.save(empresa);
	}


	@Override
	public List<Empresa> findOneByContratante(Contratante contratante) {
		return data.findOneByContratante(contratante);
	}
}