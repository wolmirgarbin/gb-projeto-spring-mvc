package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.PessoaEmpresa;
import br.com.viasoft.portaldef.repositories.PessoaEmpresaRepository;
import br.com.viasoft.portaldef.service.PessoaEmpresaService;

@Service
public class PessoaEmpresaServiceImpl implements PessoaEmpresaService {

	private static final long serialVersionUID = 1347481260417084073L;
	
	@Autowired
	private PessoaEmpresaRepository data;

	@Override
	public PessoaEmpresa save(PessoaEmpresa pesEmp) {
		return data.save(pesEmp);
	}

	@Override
	public PessoaEmpresa findByPessoaAndEmpresa(Pessoa pessoa, Empresa empresa) {
		return data.findByPessoaAndEmpresa(pessoa, empresa);
	}
	
	@Override
	public List<PessoaEmpresa> findByEmpresa(Empresa empresa, Pageable pageable) {
		return data.findByEmpresa(empresa, pageable);
	}
	
	@Override
	public List<PessoaEmpresa> findByEmpresaAndEmailIsNull(Empresa empresa) {
		return data.findByEmpresaAndEmailIsNull(empresa);
	}
	
	@Override
	public PessoaEmpresa findOne(Long id) {
		return data.findOne(id);
	}
}