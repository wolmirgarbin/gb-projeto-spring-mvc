package br.com.viasoft.portaldef.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import br.com.viasoft.portaldef.configure.ApplicationProperties;
import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;

public class AttributesPageInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 2735681995262700084L;
	
	
	@Autowired @Qualifier("applicationProperties")
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		if( handler != null && modelAndView != null ) {
			final String viewName = modelAndView.getViewName();
			if( viewName != null && !viewName.startsWith("redirect:") ){
				
				// inicia o model para colocar os retornos
				final ModelMap model = modelAndView.getModelMap();
				
				
				// pega as configurações das annotations do metodos
				final ConfigPage configPage = BaseInterceptor.findAnnotation(handler, ConfigPage.class);
				if( configPage != null ) {
					model.addAttribute("title", configPage.title());
					model.addAttribute("menuPage", configPage.menuPage());
					model.addAttribute("isCapa", configPage.isCapa());
					
					if( configPage.addEmpresa() ) {
						model.addAttribute("empresa", empresaService.findOneJoinConfig());
					}
					
					if( configPage.addUsuario() ) {
						model.addAttribute("usuarioLogado", usuarioService.getUsuario() );
					}
					
					if( configPage.usaRoleAdm() ) {
						model.addAttribute("roleDoAdministrador", Roles.ROLE_ADMINISTRADOR);  // envia a role de adm para a tela
					}
					
					// propriedades do sistema
					ConfigureApp.getInstance().updatePath(request);
					model.addAttribute("applicationPath", ConfigureApp.getInstance().getApplicationPath() );
					
					
					// propriedades da app
					model.addAttribute("applicationProperties", applicationProperties);
					
				}
			}
		}
	}
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}