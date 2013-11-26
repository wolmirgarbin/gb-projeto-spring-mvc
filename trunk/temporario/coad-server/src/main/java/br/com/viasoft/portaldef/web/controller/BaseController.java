package br.com.viasoft.portaldef.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.service.PessoaService;
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
	public static final String URL_USUARIO_ALTERAR_EMPRESA = "usuario/alterar-empresa/{empresa}";
	public static final String URL_USUARIO_MINHAS_NOTAS = "usuario/ver-minhas-notas";

	// cadastro de usuário
	public static final String URL_CADASTRO_USUARIO = "cadastro/usuario";
	public static final String URL_CADASTRO_USUARIO_IDENTIFICACAO = "cadastro/usuario/{identificacao}";
	public static final String URL_CADASTRO_USUARIO_DESVINCULAR = "cadastro/desvincular/{id}";
	public static final String URL_CADASTRO_USUARIO_VINCULAR = "cadastro/vincular";
	public static final String URL_CADASTRO_USUARIO_SAVE = "cadastro/usuario-save";

	// minha area
	public static final String URL_MINHA_AREA = "minha-area";
	public static final String URL_MINHA_AREA_RECEBIDOS = "minha-area/recebidos";
	public static final String URL_MINHA_AREA_OUTRAS_NOTAS = "minha-area/outras-notas";
	public static final String URL_MINHA_AREA_SENHA = "minha-area/senhas";
	public static final String URL_MINHA_AREA_USUARIOS = "minha-area/usuarios/{mostrar}";
	public static final String URL_MINHA_AREA_ROBO = "minha-area/robo";

	// configuracao
	public static final String URL_MINHA_AREA_CONFIGURACAO = "minha-area/configuracao";
	public static final String URL_MINHA_AREA_CONFIGURACAO_FUSO = "minha-area/configuracao-fuso";
	public static final String URL_MINHA_AREA_CONFIGURACAO_CONF = "minha-area/configuracao-conf";
	public static final String URL_MINHA_AREA_ACTION_SAVE = "minha-area/configuracao/endereco";

	// administracao
	public static final String URL_ADMIN_LOGIN = "admin";
	public static final String URL_ADMIN_HOME = "admin/home";
	public static final String URL_ADMIN_LOGOUT = "admin/logout";
	public static final String URL_ADMIN_CONTRATANTE = "admin/contratante";
	public static final String URL_ADMIN_CONTRATANTE_SAVE = "admin/contratante-save";
	public static final String URL_ADMIN_EMPRESAS = "admin/empresas";
	public static final String URL_ADMIN_EMPRESAS_SAVE = "admin/empresas-save";
	public static final String URL_ADMIN_RELATORIO = "admin/relatorio";




	@Autowired
	private PessoaService pessoaSevice;

	@Autowired
	private UsuarioService usuarioService;


	/**
	 * Adiciona mensagens a tela chamada
	 * @param model
	 */
	protected void addMensagem(Model model, final String mensagem) {
		if( StringUtils.isNotBlank(mensagem) );
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
		if( usuarioService.getUsuario().getRole().equals( Roles.ROLE_ADMINISTRADOR ) || usuarioService.getUsuario().getRole().equals( Roles.ROLE_SUPERVISOR ) ) {
			model.addAttribute("quantidadeUsuarioSemEmail", pessoaSevice.registrosSemEmail());
		}
	}
}