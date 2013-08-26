package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

@Getter @Setter
public class AtualizaSenhaTO implements Serializable {

	private static final long serialVersionUID = -7312201269204814079L;

	@NotBlank(message="{atualizaSenha.senhaAtual.vazio}") 
	@Size(min=3, max=30, message="{atualizaSenha.senhaAtual.tamanho}")
	private String senhaAtual;
	
	@NotBlank(message="{atualizaSenha.senhaNova.vazio}") 
	@Size(min=3, max=30, message="{atualizaSenha.senhaNova.tamanho}")
	private String senhaNova;
	
	@NotBlank(message="{atualizaSenha.senhaConfirma.vazio}") 
	@Size(min=3, max=30, message="{atualizaSenha.senhaConfirma.tamanho}")
	private String senhaConfirma;
	
}