package br.com.viasoft.portaldef.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.service.PessoaSevice;
import br.com.viasoft.portaldef.service.UsuarioService;

@Controller
public class BaseController {

	public static final String NOME_PORTAL = "Portal DF-e";
	
	// error
	public static final String URL_PAGINA_NAO_ENCONTRADA = "error/pagina-nao-encontrada";
	public static final String URL_ACESSO_RESTRITO = "error/acesso-restrito";
	
	// usuario
	public static final String URL_USUARIO_ENVIAR_SENHA = "usuario/enviar-senha";
	public static final String URL_USUARIO_LOGOUT = "usuario/logout";
	
	// minha area
	public static final String URL_MINHA_AREA = "minha-area";
	public static final String URL_MINHA_AREA_RECEBIDOS = "minha-area/recebidos";
	public static final String URL_MINHA_AREA_SENHA = "minha-area/senhas";
	public static final String URL_MINHA_AREA_USUARIOS = "minha-area/usuarios/{mostrar}";

	// configuracao
	public static final String URL_MINHA_AREA_CONFIGURACAO = "minha-area/configuracao";
	public static final String URL_MINHA_AREA_ACTION_SAVE = "minha-area/configuracao/endereco";
	
	
	@Autowired
	private PessoaSevice pessoaSevice;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	/**
	 * Adiciona mensagens a tela chamada
	 * @param model
	 */
	protected void addMensagem(Model model, final String mensagem) {
		model.addAttribute("mensagem", mensagem);
	}
	
	/**
	 * Adiciona mensagens a tela chamada
	 * @param model
	 */
	protected void addConfirmacao(Model model, final String mensagem) {
		addMensagem(model, mensagem);
		model.addAttribute("mensagemSucesso", true);
	}
	
	/**
	 * Retorna quando administrador do sistema a quantidade de usuários que tem sem email cadastrado
	 * 
	 * @param model
	 */
	protected void quantidadeEmailCad(Model model) {
		if( usuarioService.getUsuario().getRole().equals( Roles.ROLE_ADMINISTRADOR ) ) {
			model.addAttribute("quantidadeUsuarioSemEmail", pessoaSevice.registrosSemEmail());
		}
	}
}