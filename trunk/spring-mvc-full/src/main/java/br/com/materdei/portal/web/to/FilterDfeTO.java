package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilterDfeTO implements Serializable {

	private static final long serialVersionUID = 5440729554626822791L;
	
	private Integer apartirDe;
	private String dataInicio;
	private String dataFim;
	private String situacao;
	private String nome;
	private String identificacao;
	private String tipoBusca;
}
