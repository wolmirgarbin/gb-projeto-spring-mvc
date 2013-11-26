package br.com.viasoft.portaldef.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import br.com.viasoft.portaldef.entities.Gerenciadores;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.repositories.GerenciadoresRepository;
import br.com.viasoft.portaldef.service.GerenciadoresService;

@Service
public class GerenciadoresServiceImpl implements GerenciadoresService {

	private static final long serialVersionUID = 1347481260417084073L;
	
	private static final String KEY_GERENCIADORES_SESSION = "key.gerenciadores.session.portal.dfe";
	
	@Autowired
	private GerenciadoresRepository data;
	
	
	@Override
	public Gerenciadores login(Gerenciadores gerenciadores) {
		final Gerenciadores geren = data.findByUsuarioAndSenha(gerenciadores.getUsuario(), gerenciadores.getSenha(), SimNao.S);
		
		if( geren != null )
			RequestContextHolder.getRequestAttributes().setAttribute(KEY_GERENCIADORES_SESSION, geren, RequestAttributes.SCOPE_SESSION);
		
		return geren;
	}
	
	
	@Override
	public void logout() {
		RequestContextHolder.getRequestAttributes().removeAttribute(KEY_GERENCIADORES_SESSION, RequestAttributes.SCOPE_SESSION);
	}

	
	@Override
	public Gerenciadores getGerenciadores() {
		return (Gerenciadores)RequestContextHolder.getRequestAttributes().getAttribute(KEY_GERENCIADORES_SESSION, RequestAttributes.SCOPE_SESSION);
	}
	
	
	@Override
	public boolean isLogado() {
		return getGerenciadores() != null;
	}
}
