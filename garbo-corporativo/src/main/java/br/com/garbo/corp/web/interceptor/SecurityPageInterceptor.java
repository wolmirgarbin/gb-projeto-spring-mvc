package br.com.garbo.corp.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import br.com.garbo.corp.service.UsuarioSessionService;
import br.com.garbo.corp.web.controller.IndexController;
import br.com.garbo.sites.annotations.ConfigAcessoUsuario;
import br.com.garbo.sites.interceptor.BaseInterceptor;
import br.com.garbo.sites.util.RequestUtils;
import br.com.garbo.util.CriptoDESUtils;



public class SecurityPageInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -7241253833293391411L;

	@Autowired
	private UsuarioSessionService usuarioSessionService;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// verifica o cookie quando o usuário não esta conectado
		if( ! usuarioSessionService.isConectado() ) {
			Cookie[] cookies = request.getCookies();
			if( cookies != null ) {
				for (Cookie cookie : cookies) {
					if( cookie.getName().equals(IndexController.NOME_COOKIE_LOGIN) ){
						if( StringUtils.isNotEmpty( cookie.getValue() ) ) {
							try {
								String usuario = CriptoDESUtils.decrypta(cookie.getValue());
								usuarioSessionService.login(usuario, "");
							} catch (Exception e) {  }
						}
						break;
					}
				}
			}
		}
		
		
		// tratamento de acesso de roles/login
		if( handler != null ) {
			
			// pega as configurações das annotations do metodos
			final ConfigAcessoUsuario configAcessoUsuario  = BaseInterceptor.findAnnotation(handler, ConfigAcessoUsuario.class);
			
			// se não tem configurações retorna true para continuar a exibição
			if( configAcessoUsuario == null ) {
				return true;
			}
			
			if( usuarioSessionService.isConectado() ) {
				//if( ! usuarioService.verificaRole( configAcessoUsuario.role() )) {
					// redireciona para tela de acesso restrito.
					//final String redirect = ConfigureApp.getInstance().getApplicationPath() + BaseController.URL_MINHA_AREA;
					//response.sendRedirect(redirect);
					//return false;
				//}
			} else {
				// redireciona para tela de acesso restrito.
				response.sendRedirect(RequestUtils.path(request));
			}
			
			// se tem aceso redireciona para a tela
		}
		
		/*String code = request.getParameter("code");
		if(StringUtils.isNotEmpty(code)){
			String redirectUrl = (String) RequestContextHolder.getRequestAttributes().getAttribute(FACEBOOK_CALLBACK_URL_SESSION_PARAM, RequestAttributes.SCOPE_SESSION);

			if(StringUtils.isNotEmpty(redirectUrl)){
				boolean autenticou = usuarioService.autenticarUsuarioFaceBook(code, redirectUrl, request, response);
				
				if(autenticou){
					response.sendRedirect(redirectUrl);
					return false;
				}
			}
			
		}
		
		UsuarioLogadoTO usuario = usuarioService.getUsuarioLogado();
		if(usuario == null){
			String uri = this.urlPathHelper.getLookupPathForRequest(request);
			boolean needLogin = false;
			for(String pattern:listaPatternLoginRequired){
				if(matcher.match(pattern, uri)){
					needLogin = true;
					break;
				}
			}
			if(needLogin){
				String redirect = penseSitiesPropertiesTO.getApplicationDirPathControllers();
				if (redirect.equals("")) {
					redirect = "/";
				}
				response.sendRedirect(redirect);
				return false;
			}
		}
		
		*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

}