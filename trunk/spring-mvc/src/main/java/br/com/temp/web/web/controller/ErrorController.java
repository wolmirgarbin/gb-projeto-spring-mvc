package br.com.viasoft.portaldef.web.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.web.anotations.ConfigPage;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ErrorController extends BaseController {
	
	private static final String BASE_FOLDER = "/";

	
	@RequestMapping(value = "/"+ URL_PAGINA_NAO_ENCONTRADA, method = RequestMethod.GET)
	@ConfigPage(title="Pagina não encontrada | Portal DFE", addEmpresa=true, isCapa=true, addUsuario=false)
	public String paginaNaoEncontrada(Model model) {
		
		model.addAttribute("paginaNaoEncontrada", Boolean.TRUE);
		
		return BASE_FOLDER +"index";
	}
	
	
	
	@RequestMapping(value = "/"+ URL_ACESSO_RESTRITO, method = RequestMethod.GET)
	@ConfigPage(title="Acesso restrito | Portal DFE", addEmpresa=true, menuPage="senhas", isCapa=true)
	public String acessoRestrito(Model model) {
		
		model.addAttribute("acessoRestrito", Boolean.TRUE);
		
		return BASE_FOLDER +"index";
	}
}
