package br.com.viasoft.portaldef.web.controller;

import java.util.Locale;

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

import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.service.ContratanteService;
import br.com.viasoft.portaldef.service.GerenciadoresService;
import br.com.viasoft.portaldef.util.ResultParam;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AdmContratanteController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdmContratanteController.class);

	@Autowired
	private GerenciadoresService gerenciadoresService;

	@Autowired
	private ContratanteService contratanteService;


	@RequestMapping(value = "/"+ URL_ADMIN_CONTRATANTE, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Manutenção de empresas")
	public String home(Locale locale, Model model, @RequestParam(value="id", required=false) final Long id, @RequestParam(value="msg", required=false) final String msg) {

		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );

		if( id != null )
			model.addAttribute("contratante", contratanteService.findOne(id));

		if( msg != null )
			addMensagem(model, msg);

		return AdministracaoController.BASE_FOLDER +"contratante";
	}


	@RequestMapping(value = "/"+ URL_ADMIN_CONTRATANTE_SAVE, method = RequestMethod.POST)
	public String save(Locale locale, Model model, Contratante contratante) {

		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );

		contratanteService.save(contratante);

		return Results.redirect( URL_ADMIN_CONTRATANTE, new ResultParam("msg", "Salvo com sucesso") );
	}

}