package br.com.garbo.corp.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.garbo.corp.service.UsuarioSessionService;
import br.com.garbo.corp.web.configure.TilesNames;
import br.com.garbo.sites.annotations.AttributesPage;
import br.com.garbo.sites.cookie.CookieUtils;
import br.com.garbo.sites.redirecionamento.Results;
import br.com.garbo.util.CriptoDESUtils;

@Controller
public class IndexController extends BaseController {
	
	public static final String NOME_COOKIE_LOGIN = "garbo-cookie-login-acesso";
	private static final String TITULO_INDEX = "Portal Garbo CRP, o portal feito para sua empresa | Login ou acesso ao sistema";

	@Autowired
	private UsuarioSessionService usuarioSessionService;
	
	
	
	@RequestMapping(value=URL_BASE, method=RequestMethod.GET)
	@AttributesPage(title=TITULO_INDEX, groupResources="capa")
	public String index(Model model){
		if( usuarioSessionService.isConectado() )
			return Results.redirect( URL_MINHA_AREA );
		
		return TilesNames.SISTEMA_INDEX;
	}
	
	
	
	@RequestMapping(value=URL_BASE, method=RequestMethod.POST)
	@AttributesPage(title=TITULO_INDEX, groupResources="capa")
	public String login(
		HttpServletResponse response, 
		Model model,
		@RequestParam(value="usuario", required=true) String usuario,
		@RequestParam(value="senha", required=true) String senha,
		@RequestParam(value="manterConectado", required=false) String manterConectado
	){
		if( usuario.equals("cesar") ) {
			if( manterConectado != null ) {
				try {
					response.addCookie(CookieUtils.createCookie(NOME_COOKIE_LOGIN, CriptoDESUtils.encrypta( usuario )));
				} catch (Exception e) { }
			}
			
			// faz o login
			usuarioSessionService.login(usuario, senha);
			
			// envia para minha area
			return Results.redirect(URL_MINHA_AREA);
		} else {
			messageAlert("Usuário ou senha errado!", model);
			return TilesNames.SISTEMA_INDEX;
		}
	}
	
	
	
	@RequestMapping(value=URL_LOGOUT, method=RequestMethod.GET)
	public String logout(Model model, HttpServletResponse response){
		usuarioSessionService.logout();
		try {
			response.addCookie(CookieUtils.createCookie(NOME_COOKIE_LOGIN, ""));
		} catch (Exception e) { }
		return Results.redirect(URL_BASE);
	}
	
	
	
	@RequestMapping(value=URL_ERRO, method=RequestMethod.GET)
	@AttributesPage(title=TITULO_INDEX, groupResources="error")
	public String error(Model model){
		return TilesNames.SISTEMA_ERROR;
	}
	
}
