package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.repositories.PessoaRepository;
import br.com.viasoft.portaldef.repositories.custom.PessoaRepositoryCustom;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.web.to.FiltroPessoaTO;

@Service
public class PessoaServiceImpl implements PessoaService {

	private static final long serialVersionUID = 2658580997391184486L;
	
	@Autowired
	private PessoaRepository data;
	
	@Autowired
	private PessoaRepositoryCustom custom;
	
	@Autowired
	private UsuarioService usuarioService;
	
	

	@Override
	public List<Pessoa> findByEmailIsNullOrderByNomeAsc() {
		return data.findEmailIsNullOrderByNomeAsc(usuarioService.getUsuario().getEmpresaBase());
	}
	
	@Override
	public List<Pessoa> findByEmailIsNotNullOrderByNomeAsc() {
		return data.findEmailIsNotNullOrderByNomeAsc(usuarioService.getUsuario().getEmpresaBase());
	}
	
	@Override
	public Long registrosSemEmail() {
		return data.registrosSemEmail(usuarioService.getUsuario().getEmpresaBase());
	}
	
	@Override
	public Long count() {
		return data.count(usuarioService.getUsuario().getEmpresaBase());
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
	public List<Pessoa> buscaPaginada(Pageable pageable) {
		return data.findPessoaOrderByNomeAsc(pageable);
	}
	
	@Override
	public List<Pessoa> buscar(FiltroPessoaTO to) {
		return custom.buscar(to);
	}
	
	@Override
	public Long count(FiltroPessoaTO to) {
		return custom.count(to);
	}
	
	@Override
	public Pessoa findByIdentificacao(String identificacao) {
		return data.findByIdentificacao(identificacao);
	}
}