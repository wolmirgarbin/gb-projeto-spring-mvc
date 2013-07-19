package br.com.viasoft.portaldef.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.repositories.UsuarioRepository;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.util.CriptoUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final long serialVersionUID = 1347481260417084073L;
	
	private static final String KEY_USUARIO_SESSION = "key.usuario.session.portalnfe";
	
	@Autowired
	private UsuarioRepository data;
	
	
	@Override
	public Usuario login(Usuario usuario) {
		
		final String senhaCrip = CriptoUtil.criptografar(usuario.getSenha());
		usuario = data.findByUsuarioAndSenha(usuario.getUsuario(), senhaCrip);
		
		if( usuario != null ) {
			setSessionUser(usuario);
		}
		
		return usuario;
	}

	@Override
	public void logout() {
		RequestContextHolder.getRequestAttributes().removeAttribute(KEY_USUARIO_SESSION, RequestAttributes.SCOPE_SESSION);
	}

	@Override
	public Usuario getUsuario() {
		return (Usuario)RequestContextHolder.getRequestAttributes().getAttribute(KEY_USUARIO_SESSION, RequestAttributes.SCOPE_SESSION);
	}
	
	@Override
	public boolean isLogado() {
		return getUsuario() != null;
	}

	@Override
	public boolean verificaRole(String role) {
		final Usuario usuario = getUsuario();
		if( usuario != null && ( role.equals( usuario.getRole().name() ) || role.equals("ALL") ) ) {
			return true;
		}
		return false;
	}
	
	@Override
	public Usuario save(Usuario usuario) {
		setSessionUser(usuario);
		return data.save(usuario);
	}
	
	private void setSessionUser(Usuario usuario) {
		RequestContextHolder.getRequestAttributes().setAttribute(KEY_USUARIO_SESSION, usuario, RequestAttributes.SCOPE_SESSION);
	}
	
	@Override
	public Usuario findByUsuario(String usuario) {
		return data.findByUsuario(usuario);
	}
	
	@Override
	public Usuario saveNoSession(Usuario usuario) {
		return data.save(usuario);
	}
}
