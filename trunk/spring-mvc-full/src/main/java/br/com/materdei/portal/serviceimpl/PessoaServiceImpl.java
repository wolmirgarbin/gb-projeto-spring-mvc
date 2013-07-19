package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.repositories.PessoaRepository;
import br.com.viasoft.portaldef.repositories.custom.PessoaRepositoryCustom;
import br.com.viasoft.portaldef.service.PessoaSevice;
import br.com.viasoft.portaldef.web.to.FiltroPessoaTO;

@Service
public class PessoaServiceImpl implements PessoaSevice {

	private static final long serialVersionUID = 2658580997391184486L;
	
	@Autowired
	private PessoaRepository data;
	
	@Autowired
	private PessoaRepositoryCustom custom;
	

	@Override
	public List<Pessoa> findByEmailIsNullOrderByNomeAsc() {
		return data.findByEmailIsNullOrderByNomeAsc();
	}
	
	@Override
	public List<Pessoa> findByEmailIsNotNullOrderByNomeAsc() {
		return data.findByEmailIsNotNullOrderByNomeAsc();
	}
	
	@Override
	public Long registrosSemEmail() {
		return data.registrosSemEmail();
	}
	
	@Override
	public Long count() {
		return data.count(SimNao.S);
	}
	
	@Override
	public Pessoa findOne(Long id) {
		return data.findOne(id);
	}
	
	@Override
	public Pessoa save(Pessoa p) {
		return data.save(p);
	}
	
	@Override
	public List<Pessoa> findByAtivoOrderByNomeAsc(SimNao ativo, Pageable pageable) {
		return data.findByAtivoOrderByNomeAsc(ativo, pageable);
	}
	
	@Override
	public List<Pessoa> buscar(FiltroPessoaTO to) {
		return custom.buscar(to);
	}
	
	@Override
	public Long count(FiltroPessoaTO to) {
		return custom.count(to);
	}
}