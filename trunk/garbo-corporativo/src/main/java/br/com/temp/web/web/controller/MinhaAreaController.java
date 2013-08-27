package br.com.viasoft.portaldef.web.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.entities.PessoaEmpresa;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.service.CaptchaService;
import br.com.viasoft.portaldef.service.PessoaEmpresaService;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class MinhaAreaController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MinhaAreaController.class);
	
	public static final String BASE_FOLDER = "minhaarea/";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PessoaService pessoaSevice;
	
	@Autowired
	private PessoaEmpresaService pessoaEmpresaService;
	
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
		if( ! usuarioService.getUsuario().getRole().equals(Roles.ROLE_CLIENTE) ) {
			model.addAttribute("recebidos", false);
		} else {
			model.addAttribute("recebidos", true);
		}
		quantidadeEmailCad(model);
		
		// logica que mostra a ajuda para usuário no primeiro acesso
		if( SimNao.S.equals( usuarioService.getUsuario().getAjuda() ) ) {
			// informa o .jsp para mostrar na tela
			model.addAttribute("mostrarOrientacao", true);
			// atualiza para não mostrar mais
			final Usuario usuario = usuarioService.getUsuario();
			usuario.setAjuda(SimNao.N);
			usuarioService.save(usuario);
		} else 
			model.addAttribute("mostrarOrientacao", false);
		
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
		List<PessoaEmpresa> list = null;
		boolean linkVerTodos = false;
		
		// Apenas o supervisor pode ver todos os clientes ou os administradores podem ver os clientes lidados a eles que não possuem e-mail cadastrado no sistema
		if( usuarioService.verificaRole( Roles.ROLE_SUPERVISOR.name() ) ) {
			model.addAttribute("mostrarLink", true);
			if( "all".equals(mostrar) ) {
				list = pessoaEmpresaService.findByEmpresa(usuarioService.getUsuario().getEmpresaBase(), new PageRequest(0, 20));
			} else if( "ajustar-email".equals(mostrar) ) {
				list = pessoaEmpresaService.findByEmpresaAndEmailIsNull(usuarioService.getUsuario().getEmpresaBase());
				linkVerTodos = true;
			}
		} else {
			list = pessoaEmpresaService.findByEmpresaAndEmailIsNull(usuarioService.getUsuario().getEmpresaBase());
		}
		model.addAttribute("countPessoas", pessoaSevice.count());
		model.addAttribute("listPessoas", list);
		model.addAttribute("linkVerTodos", linkVerTodos);
		quantidadeEmailCad(model);
		return BASE_FOLDER +"usuarios";
	}

}