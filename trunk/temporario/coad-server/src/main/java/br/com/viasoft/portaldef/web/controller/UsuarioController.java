package br.com.viasoft.portaldef.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.service.ConfigService;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.MailSendService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.anotations.ConfigPage;
import br.com.viasoft.portaldef.web.to.AtualizaSenhaTO;
import br.com.viasoft.util.CriptoUtil;
import br.com.viasoft.util.PassUtil;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UsuarioController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ConfigService configService;




	@RequestMapping(value = "/", method = {RequestMethod.POST})
	@ConfigPage(title="Login | Portal DFE", addEmpresa=true, isCapa=true, usaRoleAdm=true)
	public String acessar(Model model, @Valid Usuario usuario, BindingResult result) {

		if(result.hasFieldErrors("usuario") || result.hasFieldErrors("senha")) {
			return "index";
		}

		final Usuario usuarioBanco = usuarioService.login(usuario);

		if( usuarioBanco != null ) {
			LOGGER.info("Acessou com o usuário: "+ usuario.getUsuario() );
			if( usuarioBanco.getMudouSenha().equals(SimNao.N) ) {
				return Results.redirect( URL_MINHA_AREA_SENHA );
			} else {
				return Results.redirect( URL_MINHA_AREA );
			}
		} else {
			addMensagem(model, "Usuário ou senha não conferem");
			model.addAttribute("cnpj", usuario.getUsuario() );
			return "index";
		}
	}


	@RequestMapping(value = "/"+ URL_USUARIO_ENVIAR_SENHA, method = RequestMethod.GET)
	public String enviarSenha(Model model) {
		return Results.redirect("/");
	}


	@RequestMapping(value = "/"+ URL_USUARIO_ALTERAR_EMPRESA, method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ALL")
	public String alterarDeEmpresa(Model model, @PathVariable("empresa") Long empresa) {
		usuarioService.alterarDeEmpresa(empresa);
		return Results.redirect( URL_MINHA_AREA );
	}


	@RequestMapping(value = "/"+ URL_USUARIO_MINHAS_NOTAS, method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ALL")
	public String minhasNotas(Model model) {
		final Usuario usuario = usuarioService.getUsuario();
		usuario.setEmpresaBase(null);
		usuarioService.saveInSession(usuario);

		return Results.redirect( URL_MINHA_AREA );
	}


	@RequestMapping(value = "/"+ URL_USUARIO_ENVIAR_SENHA, method = RequestMethod.POST)
	@ConfigPage(title="Envio de senha | Portal DFE", addEmpresa=true, isCapa=true, usaRoleAdm=true)
	public String enviarSenha(Model model, @Valid Usuario usuario, BindingResult result) {

		// para funcionar a validação apenas do campo usuario
		model.addAttribute("validSenha", false);

		if(result.hasFieldErrors("usuario")) {
			return "index";
		} else {

			Usuario userBd = usuarioService.findByUsuario(usuario.getUsuario());
			if( userBd == null ) {
				addMensagem(model, "Usuario não encontrado, verifique seu cpf/cnpj");
			} else {
				// envia o email com a nova senha
				String emailPrincipal = null;

				// todo usuário sempre terá uma pessoa
				if( userBd.getContratante() != null ) {
					emailPrincipal = userBd.getContratante().getEmail();
				} else if( userBd.getPessoa() != null ) {
					emailPrincipal = userBd.getPessoa().getEmailPrincipal();
				}

				if(emailPrincipal != null ) {

					// gera senha aleatoria
					final String senha = PassUtil.getRandomPass(8);

					// gera uma senha de 8 digitos aleatoria
					userBd.setSenha(CriptoUtil.criptografar(senha));
					userBd.setMudouSenha(SimNao.N);

					// atualiza o cadastro do usuario com a nova senha
					userBd = usuarioService.saveNoSession(userBd);

					// envia o email com a nova senha
					final StringBuilder conteudo = new StringBuilder();
					conteudo.append( "<br>------------------------------------------------" );
					conteudo.append( "<br>Sua nova senha para acesso do portal DF-e é : <b>" ).append( senha ).append( "</b>" );
					conteudo.append( "<br>------------------------------------------------" );
					mailSendService.sendMailSenha(new String[]{ emailPrincipal }, ConfigureApp.getInstance().getEmailPadrao(), "Envio de senha", conteudo.toString());

					// mostra a mensagem na tela
					addConfirmacao(model, "Enviamos para seu e-mail ("+ emailPrincipal +") uma senha temporaria para acessar o sistema.<br>Acesse seu e-mail, copie sua senha e informe no formulário.");
				} else {
					// enviar email para a empresa responsável
					final String[] para = { "wolmirgarbin@gmail.com" };
					final StringBuilder conteudo = new StringBuilder();
					conteudo.append( "<br>------------------------------------------------" );
					conteudo.append( "<br>O portal DF-e teve uma solicitação de acesso do cpf/cnpj : <b>" ).append( usuario.getUsuario() ).append( "</b>" );
					conteudo.append( "<br>Para permitir o acesso ao portal, o cliente precisa ter um endereço de e-mail valido para receber sua senha.");
					conteudo.append( "<br>Acesse com usuário administrador e cadastre o e-mail deste cliente.");
					conteudo.append( "<br>------------------------------------------------" );
					mailSendService.sendMailSenha(para, ConfigureApp.getInstance().getEmailPadrao(), "Solicitação de acesso", conteudo.toString());

					addMensagem(model, "Endereço de e-mail não cadastrado! <br>Notificamos o responsável para entrar em contato!");
				}
			}

			model.addAttribute("cnpj", usuario.getUsuario() );
			return "index";
		}
	}


	@RequestMapping(value = "/"+ URL_USUARIO_LOGOUT, method={RequestMethod.GET,RequestMethod.POST})
	public String logout(Model model) {
		usuarioService.logout();
		return Results.redirect("/");
	}


	@RequestMapping(value = "/"+ URL_MINHA_AREA_SENHA, method = RequestMethod.POST)
	@ConfigPage(title="Alterar Senha | Portal DFE", addEmpresa=true, menuPage="senhas", usaRoleAdm=true)
	@ConfigAcessoUsuario(role="ALL")
	public String alterarSenha(Model model, @Valid AtualizaSenhaTO atualizarSenhaTO, BindingResult result) {

		final boolean primeiroAcesso = SimNao.S.equals( usuarioService.getUsuario().getMudouSenha() );

		if(result.hasFieldErrors("senhaAtual") || result.hasFieldErrors("senhaNova") || result.hasFieldErrors("senhaConfirma") ) {
			addMensagem(model, "Ajuste os erros no formulário e salve novamente");
		} else {
			if( CriptoUtil.criptografar(atualizarSenhaTO.getSenhaAtual()).equals( usuarioService.getUsuario().getSenha() ) ) {
				if( atualizarSenhaTO.getSenhaNova().equals( atualizarSenhaTO.getSenhaConfirma() ) ) {
					final Usuario usuario = usuarioService.getUsuario();
					usuario.setSenha( CriptoUtil.criptografar(atualizarSenhaTO.getSenhaNova()) );
					usuario.setMudouSenha(SimNao.S);
					usuarioService.saveInSession(usuario);
					addConfirmacao(model, "Senha alterada com sucesso");
				} else {
					addMensagem(model, "Confirmação da senha não confere com a nova senha");
				}
			} else {
				addMensagem(model, "Senha atual informada não confere com a senha cadastrada");
			}
		}
		if( primeiroAcesso )
			return Results.redirect("minha-area/configuracao");
		else
			return MinhaAreaController.BASE_FOLDER +"senhas";
	}

}