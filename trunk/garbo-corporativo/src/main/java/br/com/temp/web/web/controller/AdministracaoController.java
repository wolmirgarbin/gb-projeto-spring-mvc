package br.com.viasoft.portaldef.web.controller;

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

import br.com.viasoft.portaldef.entities.Config;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Gerenciadores;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.enumerations.TipoEmpresa;
import br.com.viasoft.portaldef.service.ConfigService;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.GerenciadoresService;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioEmpresaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.ResultParam;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


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
	
	
	

	@RequestMapping(value = "/"+ URL_ADMIN_LOGIN, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Acesso administrativo")
	public String login(Locale locale, Model model) {
		
		return BASE_FOLDER +"login";
	}

	
	@RequestMapping(value = "/"+ URL_ADMIN_LOGIN, method = RequestMethod.POST)
	@ConfigPage(title=NOME_PORTAL +" | Manutenção de empresas")
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
		
		model.addAttribute("lsEmpresa", empresaService.findAll());
		
		if( id != null )
			model.addAttribute("empresa", empresaService.findOne(id));
		
		if( msg != null )
			addMensagem(model, msg);
		
		return BASE_FOLDER +"empresas";
	}
	
	
	@RequestMapping(value = "/"+ URL_ADMIN_ACTION_SAVE, method = RequestMethod.POST)
	public String save(Locale locale, Model model, 
		@RequestParam(value="id", required=false) final Long id,
		@RequestParam(value="nome", required=false) final String nome,
		@RequestParam(value="email", required=false) final String email,
		@RequestParam(value="identificacao", required=true) final String identificacao,
		@RequestParam(value="cidade", required=false) final String cidade,
		@RequestParam(value="uf", required=false) final String uf,
		@RequestParam(value="tipo") final String tipo,
		@RequestParam(value="matriz", required=false) final Long matriz,
		@RequestParam(value="ativa") final String ativa
	) {
		
		if( ! gerenciadoresService.isLogado() )
			return Results.redirect( URL_ADMIN_LOGIN );
		
		final Empresa validaParaCadastrar = empresaService.findOneByIdentificacao(identificacao);
		if( validaParaCadastrar != null && id == null )
			return Results.redirect( URL_ADMIN_HOME, new ResultParam("msg", "Já tem uma empresa cadastrada com este CPF / CNPJ") );
		
		
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
		
		config.setEmailNotificacao(email);
		config = configService.save(config);
		
		empresa.setNome(nome);
		empresa.setAtiva( SimNao.valueOf(ativa) );
		empresa.setCidade(cidade);
		empresa.setConfig( config );
		if( matriz != null ) 
			empresa.setEmpresaMatriz( empresaService.findOne(matriz) );
		else 
			empresa.setEmpresaMatriz( null );
		empresa.setTipo(TipoEmpresa.valueOf(tipo));
		empresa.setUf(uf);
		empresa = empresaService.save(empresa);
		
		
		// insere a pessoa pelo cnpj caso não tenha cadastrada
		Pessoa pessoa = pessoaSevice.findByIdentificacao(identificacao);
		
		if( pessoa == null ) {
			pessoa = new Pessoa();
			pessoa.setEmailPrincipal(email);
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
			usuario = usuarioService.saveNoSession(usuario);
			
			// grava vinculo com a empresa
			final UsuarioEmpresa ue = new UsuarioEmpresa();
			ue.setEmpresa(empresa);
			ue.setUsuario(usuario);
			ue.setPadrao(SimNao.S);
			usuarioEmpresaService.save(ue);
		}
		
		return Results.redirect( URL_ADMIN_HOME );
	}
	
	
	@RequestMapping(value = "/"+ URL_ADMIN_LOGOUT, method = RequestMethod.GET)
	public String logout() {
		gerenciadoresService.logout();
		return Results.redirect( URL_ADMIN_LOGIN );
	}
}