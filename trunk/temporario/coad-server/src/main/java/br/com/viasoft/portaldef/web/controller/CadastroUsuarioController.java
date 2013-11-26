package br.com.viasoft.portaldef.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.service.CaptchaService;
import br.com.viasoft.portaldef.service.PessoaEmpresaService;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioEmpresaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.ResultParam;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CadastroUsuarioController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CadastroUsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaEmpresaService pessoaEmpresaService;

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private UsuarioEmpresaService usuarioEmpresaService;




	@RequestMapping(value = "/"+ URL_CADASTRO_USUARIO, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Cadastro de usuário", addEmpresa=true, menuPage="usuarios", usaRoleAdm=true)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String cadastro(Model model, @RequestParam(value="msg", required=false) String msg) {
		model.addAttribute("identificacao", true);
		addConfirmacao(model, msg);
		if( usuarioService.getUsuario().getEmpresaBase() != null ) {
			model.addAttribute("lsUsuarioEmpresa", usuarioEmpresaService.findByEmpresa(usuarioService.getUsuario().getEmpresaBase().getId()));
		} else {
			model.addAttribute("alterarVisualizacao", true );
		}
		return MinhaAreaController.BASE_FOLDER +"cadastro";
	}


	@RequestMapping(value = "/"+ URL_CADASTRO_USUARIO_IDENTIFICACAO, method = RequestMethod.GET)
	@ConfigPage(title=NOME_PORTAL +" | Cadastro de usuário", addEmpresa=true, menuPage="usuarios", usaRoleAdm=true)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String usuario(Model model, @PathVariable("identificacao") final String identificacao) {

		final Usuario usuario = usuarioService.findByUsuario(identificacao);
		if( usuario != null ) {
			final List<UsuarioEmpresa> lsUserEmp = usuarioEmpresaService.findByEmpresaAndUsuario(usuarioService.getUsuario().getEmpresaBase().getId(), usuario.getId());
			if( lsUserEmp != null && lsUserEmp.size() > 0 ) {
				return Results.redirect(URL_CADASTRO_USUARIO, new ResultParam("msg", "Este usuário já pode acessar as notas desta empresa!"));
			}
		}

		model.addAttribute("identificacao", false);
		model.addAttribute("usuario", usuario);
		model.addAttribute("identificacaoCampo", identificacao);
		return MinhaAreaController.BASE_FOLDER +"cadastro";
	}


	@RequestMapping(value = "/"+ URL_CADASTRO_USUARIO_DESVINCULAR, method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String desvincular(Model model, @PathVariable("id") final Long id) {

		final Usuario usuarioDB = usuarioService.findOne(id);
		// para não remover o vinculo com o usuário que tem o mesma identificação
		if( ! usuarioDB.getUsuario().equals( usuarioService.getUsuario().getEmpresaBase().getIdentificacao() ) ) {
			// se o usuário saiu do sistema e estava acessando esta empresa retira o vinculo
			if( usuarioDB.getEmpresaBase() != null && usuarioDB.getEmpresaBase().getId().equals( usuarioService.getUsuario().getEmpresaBase().getId() ) ) {
				usuarioDB.setEmpresaBase(null);
				usuarioService.saveNoSession(usuarioDB);
			}
			// depois apenas desvincula
			usuarioEmpresaService.deleteVinculo(usuarioService.getUsuario().getEmpresaBase().getId(), id);

			// redireciona para a tela anterior
			return Results.redirect(URL_CADASTRO_USUARIO, new ResultParam("msg", "Usuário desvinculado desta empresa!"));
		} else {
			return Results.redirect(URL_CADASTRO_USUARIO, new ResultParam("msg", "Não pode desvincular este usuário!"));
		}
	}


	@RequestMapping(value = "/"+ URL_CADASTRO_USUARIO_VINCULAR, method = RequestMethod.POST)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String vincular(Model model, @RequestParam(value="usuario",required=true) final Long usuario) {
		final UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setEmpresa( usuarioService.getUsuario().getEmpresaBase() );
		usuarioEmpresa.setPadrao(SimNao.S);
		usuarioEmpresa.setUsuario(usuarioService.findOne(usuario));
		usuarioEmpresaService.save(usuarioEmpresa);
		return Results.redirect(URL_CADASTRO_USUARIO, new ResultParam("msg", "O usuário está vinculado a empresa!"));
	}


	@RequestMapping(value = "/"+ URL_CADASTRO_USUARIO_SAVE, method = RequestMethod.POST)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String save(Model model,
		@RequestParam(value="identificacao",required=true) final String identificacao,
		@RequestParam(value="nome",required=true) final String nome,
		@RequestParam(value="email",required=true) final String email,
		@RequestParam(value="fantasia",required=true) final String fantasia
	) {

		final Usuario usuarioBD = usuarioService.findByUsuario(identificacao);
		if( usuarioBD != null ) {
			return Results.redirect(URL_CADASTRO_USUARIO, new ResultParam("msg", "Esté CPF ou CNPJ já está cadastrado!"));
		}
		// salvo a pessoa
		Pessoa pessoa = new Pessoa();
		pessoa.setEmailPrincipal(email);
		pessoa.setFantasia(fantasia);
		pessoa.setIdentificacao(identificacao);
		pessoa.setNome(nome);
		pessoa = pessoaService.save(pessoa);

		// salvo o usuário e vinculo a pessoa
		Usuario usuario = new Usuario();
		usuario.setAjuda(SimNao.S);
		usuario.setAtivo(SimNao.S);
		usuario.setMudouSenha(SimNao.N);
		usuario.setPessoa(pessoa);
		usuario.setRole(Roles.ROLE_CLIENTE);
		usuario.setSenha("*****");
		usuario.setUsuario(identificacao);
		usuario = usuarioService.saveNoSession(usuario);

		// salvo vinculo com a empresa
		final UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setEmpresa( usuarioService.getUsuario().getEmpresaBase() );
		usuarioEmpresa.setPadrao(SimNao.S);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresaService.save(usuarioEmpresa);

		return Results.redirect(URL_CADASTRO_USUARIO, new ResultParam("msg", "Usuário cadastrado com sucesso e vinculado a empresa!"));
	}
}