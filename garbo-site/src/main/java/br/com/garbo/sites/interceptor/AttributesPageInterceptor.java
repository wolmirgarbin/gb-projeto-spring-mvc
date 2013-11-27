package br.com.garbo.sites.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import br.com.garbo.sites.annotations.AttributesPage;
import br.com.garbo.sites.util.RequestUtils;

public class AttributesPageInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 6326155958015360304L;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}


	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if( handler != null && modelAndView != null ) {
			final String viewName = modelAndView.getViewName();
			if( viewName != null && !viewName.startsWith("redirect:") ){

				// inicia o model para colocar os retornos
				final ModelMap model = modelAndView.getModelMap();

				// pega as configuracoes das annotations do metodos
				final AttributesPage attributesPage = BaseInterceptor.findAnnotation(handler, AttributesPage.class);
				if( attributesPage != null ) {
					model.addAttribute("title", attributesPage.title());
					model.addAttribute("menuPage", attributesPage.menuPage());
					model.addAttribute("isCapa", attributesPage.isCapa());
					model.addAttribute("applicationPath", RequestUtils.path(request));
					
					model.addAttribute("groupNameCss", attributesPage.groupResources());
					model.addAttribute("groupNameJs", attributesPage.groupResources());
				}
			}
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { }
	
}