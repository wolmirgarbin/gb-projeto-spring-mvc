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

import br.com.viasoft.portaldef.entities.Config;
import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.enumerations.TipoEmpresa;
import br.com.viasoft.portaldef.service.ConfigService;
import br.com.viasoft.portaldef.service.ContratanteService;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.GerenciadoresService;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioEmpresaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.service.UtcService;
import br.com.viasoft.portaldef.util.ResultParam;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AdmEmpresasController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdmEmpresasController.class);

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
	private UtcService utcService;



	@RequestMapping(value = "/"+ URL_ADMIN_EMPRESAS, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Manutenção de empresas")
	public String home(Locale locale, Model model, @RequestParam(value="id", required=false) final Long id, @RequestParam(value="contratante", required=false) final Long contratante, @RequestParam(value="msg", required=false) final String msg) {

		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );

		final Contratante contratanteBD = contratanteService.findOne(contratante);
		if( contratanteBD == null )
			return Results.redirect( URL_ADMIN_HOME );

		model.addAttribute("contratante", contratanteBD);
		model.addAttribute("lsEmpresa", empresaService.findOneByContratante(contratanteBD));
		model.addAttribute("lsFusoHorario", utcService.findAll());

		if( id != null )
			model.addAttribute("empresa", empresaService.findOne(id));

		if( msg != null )
			addMensagem(model, msg);

		return AdministracaoController.BASE_FOLDER +"empresas";
	}


	@RequestMapping(value = "/"+ URL_ADMIN_EMPRESAS_SAVE, method = RequestMethod.POST)
	public String save(Locale locale, Model model,
		@RequestParam(value="contratante", required=false) final Long contratante,
		@RequestParam(value="id", required=false) final Long id,
		@RequestParam(value="nome", required=false) final String nome,
		@RequestParam(value="identificacao", required=true) final String identificacao,
		@RequestParam(value="cidade", required=false) final String cidade,
		@RequestParam(value="uf", required=false) final String uf,
		@RequestParam(value="utc", required=false) final String utc,
		@RequestParam(value="tipo") final String tipo,
		@RequestParam(value="matriz", required=false) final Long matriz,
		@RequestParam(value="ativa") final String ativa
	) {

		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );

		final Contratante contratanteBD = contratanteService.findOne(contratante);
		if( contratanteBD == null )
			return Results.redirect( URL_ADMIN_HOME );

		final Empresa validaParaCadastrar = empresaService.findOneByIdentificacao(identificacao);
		if( validaParaCadastrar != null && id == null )
			return Results.redirect( URL_ADMIN_EMPRESAS, new ResultParam("msg", "Já tem uma empresa cadastrada com este CPF / CNPJ"), new ResultParam("contratante", contratante) );

		Empresa empresa = null;
		Config config = null;

		if( id != null)
			empresa = empresaService.findOne(id);

		// empresa nova (INSERT)
		if( empresa == null ) {
			empresa = new Empresa();
			empresa.setIdentificacao(identificacao);

			config = new Config();
		// empresa ja cadastrada ( UPDATE )
		} else {
			config = empresa.getConfig();
		}

		config = configService.save(config);

		empresa.setNome(nome);
		empresa.setContratante(contratanteBD);
		empresa.setAtiva( SimNao.valueOf(ativa) );
		empresa.setCidade(cidade);
		empresa.setConfig( config );
		if( matriz != null )
			empresa.setEmpresaMatriz( empresaService.findOne(matriz) );
		else
			empresa.setEmpresaMatriz( null );
		empresa.setTipo(TipoEmpresa.valueOf(tipo));
		empresa.setUf(uf);
		empresa.setUtc(utc);
		empresa = empresaService.save(empresa);


		// insere a pessoa pelo cnpj caso não tenha cadastrada
		Pessoa pessoa = pessoaSevice.findByIdentificacao(identificacao);

		if( pessoa == null ) {
			pessoa = new Pessoa();
			pessoa.setEmailPrincipal(contratanteBD.getEmail());
			pessoa.setFantasia(nome);
			pessoa.setIdentificacao(identificacao);
			pessoa.setNome(nome);
			pessoa = pessoaSevice.save(pessoa);

			Usuario usuario = new Usuario();
			usuario.setAjuda(SimNao.S);
			usuario.setAtivo(SimNao.S);
			usuario.setEmpresaBase(empresa);
			usuario.setMudouSenha(SimNao.N);
			usuario.setPessoa(pessoa);
			usuario.setRole( Roles.ROLE_ADMINISTRADOR );
			usuario.setSenha("*****");
			usuario.setUsuario(identificacao);
			usuario.setContratante(contratanteBD);
			usuario = usuarioService.saveNoSession(usuario);

			// grava vinculo com a empresa
			final UsuarioEmpresa ue = new UsuarioEmpresa();
			ue.setEmpresa(empresa);
			ue.setUsuario(usuario);
			ue.setPadrao(SimNao.S);
			usuarioEmpresaService.save(ue);
		} else {
			// seleciona o usuário e muda a role do mesmo
			final Usuario usuario = usuarioService.findByUsuario( identificacao );
			if( usuario != null ) {
				usuario.setRole( Roles.ROLE_ADMINISTRADOR );
				usuario.setContratante(contratanteBD);
				usuario.setEmpresaBase(empresa);

				usuarioService.saveNoSession(usuario);
			}
		}

		return Results.redirect( URL_ADMIN_HOME );
	}

}