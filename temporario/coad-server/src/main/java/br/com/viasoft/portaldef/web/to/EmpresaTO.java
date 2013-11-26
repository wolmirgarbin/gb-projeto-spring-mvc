package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmpresaTO implements Serializable {

	private static final long serialVersionUID = -1875152128579919050L;

	private String nome;
	private String identificacao;
	private String uf;
	private String cidade;
	private Integer quantidade;

}