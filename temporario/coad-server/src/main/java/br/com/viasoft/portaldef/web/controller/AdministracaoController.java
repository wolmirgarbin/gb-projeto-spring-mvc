package br.com.viasoft.portaldef.web.controller;

import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.entities.Gerenciadores;
import br.com.viasoft.portaldef.service.ConfigService;
import br.com.viasoft.portaldef.service.ContratanteService;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.GerenciadoresService;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioEmpresaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;
import br.com.viasoft.util.DateUtil;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AdministracaoController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdministracaoController.class);

	public static final String BASE_FOLDER = "admin/";

	@Autowired
	private GerenciadoresService gerenciadoresService;

	@Autowired
	private PessoaService pessoaSevice;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioEmpresaService usuarioEmpresaService;

	@Autowired
	private ContratanteService contratanteService;

	@Autowired
	private DfeService dfeService;




	@RequestMapping(value = "/"+ URL_ADMIN_LOGIN, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Acesso administrativo")
	public String login(Locale locale, Model model) {

		return BASE_FOLDER +"login";
	}


	@RequestMapping(value = "/"+ URL_ADMIN_LOGOUT, method = RequestMethod.GET)
	public String logout() {
		gerenciadoresService.logout();
		return Results.redirect( URL_ADMIN_LOGIN );
	}


	@RequestMapping(value = "/"+ URL_ADMIN_LOGIN, method = RequestMethod.POST)
	@ConfigPage(title=NOME_PORTAL +" | Home")
	public String acessar(Locale locale, Model model, @Valid Gerenciadores gerenciadores, BindingResult result) {

		if(result.hasFieldErrors("usuario") || result.hasFieldErrors("senha")) {
			return BASE_FOLDER +"login";
		}

		final Gerenciadores geren = gerenciadoresService.login(gerenciadores);

		if( geren != null )
			return Results.redirect( URL_ADMIN_HOME );
		else {
			addMensagem(model, "Usuário ou senha não encontrado");
			return BASE_FOLDER +"login";
		}
	}


	@RequestMapping(value = "/"+ URL_ADMIN_HOME, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Manutenção de empresas")
	public String home(Locale locale, Model model, @RequestParam(value="id", required=false) final Long id, @RequestParam(value="msg", required=false) final String msg) {

		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );

		model.addAttribute("lsContratante", contratanteService.findAll());

		if( msg != null )
			addMensagem(model, msg);

		return BASE_FOLDER +"home";
	}



	@RequestMapping(value = "/"+ URL_ADMIN_RELATORIO, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Relatório de contratante por quantidade més")
	public String relatorio(Model model,
		@RequestParam(value="contratante", required=false) final Long contratante,
		@RequestParam(value="mes", required=false) Integer mes,
		@RequestParam(value="ano", required=false) Integer ano
	) {

		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );

		model.addAttribute("contratante", contratanteService.findOne(contratante));

		if( mes == null || mes == 0 || mes > 12 )
			mes = DateUtil.month(new Date());

		if( ano == null || ano < 2012 || ano > 2080 )
			ano = DateUtil.year(new Date());

		model.addAttribute("lsEmpresaTO", dfeService.relConsumoMes(mes, ano, contratante));
		model.addAttribute("mes", mes);
		model.addAttribute("ano", ano);

		return BASE_FOLDER +"relatorio";
	}

}