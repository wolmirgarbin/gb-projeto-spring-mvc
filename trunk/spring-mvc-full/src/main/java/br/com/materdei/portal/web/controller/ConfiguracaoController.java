package br.com.viasoft.portaldef.web.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.viasoft.portaldef.entities.Config;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.service.ConfigService;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.MailLoadService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
public class ConfiguracaoController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracaoController.class);
	
	public final static String BASE_FOLDER = "configuracao/";
	
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private MailLoadService mailLoadService;
	
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA_CONFIGURACAO, method = RequestMethod.GET)
	@ConfigPage(title="Minha aréa | Configuração | "+ NOME_PORTAL, addEmpresa=true, menuPage="configuracao", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String form(Locale locale, Model model) {
		
		return BASE_FOLDER +"configuracao";
	}
	
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA_CONFIGURACAO, method = RequestMethod.POST)
	@ConfigPage(title="Minha aréa | Configuração | "+ NOME_PORTAL, addEmpresa=true, menuPage="configuracao", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String usuarios(Model model, Config config) {
		
		addConfirmacao(model, "Configuração salva com sucesso!");
		
		final Empresa emp = empresaService.findOneJoinConfig();
		config.setId( emp.getConfig().getId() );
		configService.save(config);
		
		return BASE_FOLDER +"configuracao";
	}
	
	
	
	@RequestMapping(value = "/"+ URL_MINHA_AREA_ACTION_SAVE, method = RequestMethod.POST)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String saveEndereco(Model model, Empresa empresa) {
		
		addConfirmacao(model, "Configuração salva com sucesso!");
		
		final Empresa emp = empresaService.findOneJoinConfig();
		empresa.setId( emp.getId() );
		
		//empresaService.sa
		
		return Results.redirect( URL_MINHA_AREA_CONFIGURACAO );
	}
	
	
	
	@RequestMapping(value = "/test/load-mail", method = RequestMethod.GET)
	public String testMailLoad(Locale locale, Model model) {
		mailLoadService.loadMail();
		return Results.JSON;
	}
	
}