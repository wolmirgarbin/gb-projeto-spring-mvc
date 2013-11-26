package br.com.viasoft.portaldef.web.controller;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.entities.Config;
import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.service.ConfigService;
import br.com.viasoft.portaldef.service.ContratanteService;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.service.UtcService;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
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
	private UtcService utcService;

	@Autowired
	private ContratanteService contratanteService;



	@RequestMapping(value = "/"+ URL_MINHA_AREA_CONFIGURACAO, method = RequestMethod.GET)
	@ConfigPage(title="Minha área | Configuração | "+ NOME_PORTAL, addEmpresa=true, menuPage="configuracao", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String form(Locale locale, Model model) {
		addRetornos(model);
		return BASE_FOLDER +"configuracao";
	}



	@RequestMapping(value = "/"+ URL_MINHA_AREA_CONFIGURACAO_CONF, method = RequestMethod.POST)
	@ConfigPage(title="Minha área | Configuração | "+ NOME_PORTAL, addEmpresa=true, menuPage="configuracao", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String configuracao(Model model, Config configNew) {

		final String emailNotificacao = configNew.getEmailNotificacao();

		addConfirmacao(model, "Configuração salva com sucesso!");

		configNew.setId( usuarioService.getUsuario().getEmpresaBase().getConfig().getId() );
		configNew = configService.save(configNew);
		usuarioService.getUsuario().getEmpresaBase().setConfig(configNew);

		if( StringUtils.isNotBlank(emailNotificacao) ) {
			final Contratante contratante = usuarioService.getUsuario().getContratante();
			contratante.setEmail(emailNotificacao);
			contratanteService.save(contratante);
		}

		addRetornos(model);

		return BASE_FOLDER +"configuracao";
	}


	@RequestMapping(value = "/"+ URL_MINHA_AREA_CONFIGURACAO_FUSO, method = RequestMethod.POST)
	@ConfigPage(title="Minha área | Configuração | "+ NOME_PORTAL, addEmpresa=true, menuPage="configuracao", usaRoleAdm=true  )
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String fuso(Model model, @RequestParam(required=true, value="fusoHorario") String fusoHorario) {

		Empresa empresa = usuarioService.getUsuario().getEmpresaBase();
		empresa.setUtc( fusoHorario );
		empresa = empresaService.save(empresa);
		usuarioService.getUsuario().setEmpresaBase(empresa);

		addRetornos(model);

		addConfirmacao(model, "Fuso horário atualizado com sucesso!");

		return BASE_FOLDER +"configuracao";
	}


	private void addRetornos(Model model){
		if( usuarioService.getUsuario().getEmpresaBase() != null ) {
			model.addAttribute("config", usuarioService.getUsuario().getEmpresaBase().getConfig());
		} else {
			model.addAttribute("alterarVisualizacao", true);
		}
		model.addAttribute("lsFusoHorario", utcService.findAll());
		/*model.addAttribute("lsTiposProtocolo", ConfigMailEnum.all );*/
	}

	/*@RequestMapping(value = "/"+ URL_MINHA_AREA_ACTION_SAVE, method = RequestMethod.POST)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String saveEndereco(Model model, Empresa empresa) {

		addConfirmacao(model, "Configuração salva com sucesso!");

		final Empresa emp = empresaService.findOneJoinConfig();
		empresa.setId( emp.getId() );

		//empresaService.sa

		return Results.redirect( URL_MINHA_AREA_CONFIGURACAO );
	}*/

}