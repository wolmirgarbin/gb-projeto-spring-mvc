package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.repositories.UsuarioEmpresaRepository;
import br.com.viasoft.portaldef.repositories.UsuarioRepository;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.util.CriptoUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final long serialVersionUID = 1347481260417084073L;
	
	private static final String KEY_USUARIO_SESSION = "key.usuario.session.portalnfe";
	
	@Autowired
	private UsuarioRepository data;
	
	@Autowired
	private UsuarioEmpresaRepository userEmpRep;
	
	
	
	@Override
	public Usuario login(Usuario usuario) {
		
		final String senhaCrip = CriptoUtil.criptografar(usuario.getSenha());
		usuario = data.findByUsuarioAndSenha(usuario.getUsuario(), senhaCrip);
		
		if( usuario == null ) 
			return null;
		
		// se não for usuario de cliente pega a primeira empresa padrão
		if( usuario.getPessoa() == null && usuario.getEmpresaBase() == null ) {
			final List<UsuarioEmpresa> findByUsuarioEmpresaPadrao = userEmpRep.findByUsuarioEmpresaPadrao(usuario.getId(), SimNao.S);
			if( findByUsuarioEmpresaPadrao != null && findByUsuarioEmpresaPadrao.size() > 0 ) {
				usuario.setEmpresaBase( findByUsuarioEmpresaPadrao.get(0).getEmpresa() );
				save(usuario);
			}
		}
		
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
		
		if( usuario == null )
			return false;
		
		if( role.equals("ALL") ) {
			return true;
		} else {
			// sempre que tiver role de cliente, todos pode acessar
			if( role.equals( Roles.ROLE_CLIENTE.name() ) ) {
				return true;
			
			// se tem role de administrador, adm e supervisor podem acessar
			} else if( role.equals( Roles.ROLE_ADMINISTRADOR.name() ) && ( usuario.getRole().equals( Roles.ROLE_ADMINISTRADOR ) || usuario.getRole().equals( Roles.ROLE_SUPERVISOR ) ) ) {
				return true;
			
			// se tem role de supervisor apenas ele pode acessar
			} else if( role.equals( Roles.ROLE_SUPERVISOR.name() ) && usuario.getRole().equals( Roles.ROLE_SUPERVISOR ) ) {
				return true;
			}
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
	
	@Override
	public void alterarDeEmpresa(Long empresa) {
		
		final Usuario usuario = getUsuario();
		
		final Set<UsuarioEmpresa> lsUsuarioEmpresa = usuario.getLsUsuarioEmpresa();
		for (final UsuarioEmpresa usuarioEmpresa : lsUsuarioEmpresa) {
			if( usuarioEmpresa.getEmpresa().getId().equals(empresa) ) {
				usuario.setEmpresaBase( usuarioEmpresa.getEmpresa() );
				// salva para quando abrir mostrar com a empresa selecionada
				save(usuario);
				// adiciona na sessão para manter os trabalhos
				setSessionUser(usuario);
			}
		}
	}
}
