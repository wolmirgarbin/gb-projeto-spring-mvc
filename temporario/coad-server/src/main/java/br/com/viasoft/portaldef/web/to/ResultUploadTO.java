package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultUploadTO implements Serializable {

	private static final long serialVersionUID = -3049852193668578857L;
	
	private String codigo;
	private String mensagem;
	
	public ResultUploadTO() {  }

	public ResultUploadTO(String codigo, String mensagem) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
	}
	
}