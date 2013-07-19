package br.com.viasoft.portaldef.web.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.service.CaptchaService;
import br.com.viasoft.portaldef.service.PessoaSevice;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
public class MinhaAreaController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MinhaAreaController.class);
	
	public static final String BASE_FOLDER = "minhaarea/";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PessoaSevice pessoaSevice;
	
	@Autowired
	private CaptchaService captchaService;
	
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | AGRO VIASOFT", addEmpresa=true, isCapa=true)
	public String index(Locale locale, Model model) {
		if( usuarioService.isLogado() ) {
			return Results.redirect( URL_MINHA_AREA );
		}
		return "index";
	}
	
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA, method = RequestMethod.GET)
	@ConfigPage(title="Minha aréa | "+ NOME_PORTAL, addEmpresa=true, menuPage="home", usaRoleAdm=true )
	@ConfigAcessoUsuario(role="ALL")
	public String home(Locale locale, Model model) {
		if( usuarioService.getUsuario().getRole().equals(Roles.ROLE_ADMINISTRADOR) ) {
			model.addAttribute("recebidos", false);
		} else {
			model.addAttribute("recebidos", true);
		}
		quantidadeEmailCad(model);
		return BASE_FOLDER +"home";
	}
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA_RECEBIDOS, method = RequestMethod.GET)
	@ConfigPage(title="Minha aréa | "+ NOME_PORTAL, addEmpresa=true, menuPage="recebidos", usaRoleAdm=true )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String homeRecebidos(Locale locale, Model model) {
		model.addAttribute("recebidos", true);
		quantidadeEmailCad(model);
		return BASE_FOLDER +"home";
	}
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA_SENHA, method = RequestMethod.GET)
	@ConfigPage(title="Minha aréa | Alterar senha | "+ NOME_PORTAL, addEmpresa=true, menuPage="senhas", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ALL")
	public String form(Locale locale, Model model) {
		return BASE_FOLDER +"senhas";
	}
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA_USUARIOS, method = RequestMethod.GET)
	@ConfigPage(title="Minha aréa | Manutenção de usuários | "+ NOME_PORTAL, addEmpresa=true, menuPage="usuarios", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String usuarios(Locale locale, Model model, @PathVariable("mostrar") String mostrar) {
		List<Pessoa> list = null;
		boolean linkVerTodos = false;
		if( "all".equals(mostrar) ) {
			list = pessoaSevice.findByAtivoOrderByNomeAsc(SimNao.S, new PageRequest(0, 20));
		} else if( "ajustar-email".equals(mostrar) ) {
			list = pessoaSevice.findByEmailIsNullOrderByNomeAsc();
			linkVerTodos = true;
		}
		model.addAttribute("countPessoas", pessoaSevice.count());
		model.addAttribute("listPessoas", list);
		model.addAttribute("linkVerTodos", linkVerTodos);
		quantidadeEmailCad(model);
		return BASE_FOLDER +"usuarios";
	}

}