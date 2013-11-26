package br.com.viasoft.portaldef.service;

import java.io.Serializable;

import br.com.viasoft.portaldef.entities.Usuario;

/**
 *
 * @author wolmir
 *
 * Classe que controla a sessão do usuário
 *
 */
public interface UsuarioService extends Serializable {



	/**
	 * adicionar o usuário na sessão
	 *
	 * @param
	 */
	Usuario login(Usuario usuario);

	/**
	 * Remove o usuário da sessão
	 */
	void logout();

	/**
	 * Pegar o usuário e informações do usuário da sessão
	 *
	 * @return
	 */
	Usuario getUsuario();

	/**
	 * Retorna true se o usuario está logado
	 *
	 * @return
	 */
	boolean isLogado();

	/**
	 * verifica a role do usuário para possibilitar ou não acesso a pagina
	 *
	 * @param role
	 * @return
	 */
	boolean verificaRole(final String role);

	/**
	 * Atualizar o usuário no banco de dados
	 *
	 * @param usuario
	 * @return
	 */
	Usuario saveInSession(Usuario usuario);
	Usuario saveNoSession(Usuario usuario);

	/**
	 * Seleciona o usuário apenas pelo cpf-cnpj/usuario
	 *
	 * @param usuario
	 * @return
	 */
	Usuario findByUsuario(String usuario);

	/**
	 * Altera a empresa de visualização do usuário
	 *
	 * @param empresa
	 */
	void alterarDeEmpresa(Long empresa);

	Usuario findOne(Long id);
}