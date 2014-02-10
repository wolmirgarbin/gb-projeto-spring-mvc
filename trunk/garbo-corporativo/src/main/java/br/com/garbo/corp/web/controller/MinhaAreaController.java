package br.com.garbo.corp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.garbo.corp.service.UsuarioSessionService;
import br.com.garbo.corp.web.configure.TilesNames;
import br.com.garbo.sites.annotations.AttributesPage;
import br.com.garbo.sites.annotations.ConfigAcessoUsuario;

@Controller
public class MinhaAreaController extends BaseController {
	

	@Autowired
	private UsuarioSessionService usuarioSessionService;
	
	
	@RequestMapping(value=URL_MINHA_AREA, method=RequestMethod.GET)
	@AttributesPage( title="Portal Garbo CRP", groupResources="home" )
	@ConfigAcessoUsuario
	public String home(Model model){
		
		messageSucess("Teste", model);
		
		return TilesNames.SISTEMA_HOME;
	}
	
}
