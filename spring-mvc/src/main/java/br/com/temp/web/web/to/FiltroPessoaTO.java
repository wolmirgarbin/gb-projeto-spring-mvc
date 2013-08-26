package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FiltroPessoaTO implements Serializable {

	private static final long serialVersionUID = 1778944357465263024L;
	
	private Integer apartirDe;
	private String nome;
	private String identificacao;
	
}