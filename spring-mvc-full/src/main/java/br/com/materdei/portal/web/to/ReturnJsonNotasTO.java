package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReturnJsonNotasTO implements Serializable {

	private static final long serialVersionUID = -6735004571694942404L;

	private Long id;
	private String numero;
	private String dtEmissao;
	private String destinatario;
	private String identificacao;
	private String situacao;
	private String email;
	private Integer codDocNfe;
	private Integer codDocDanf;
	private Integer idCliente;
	private Integer idDanf;
	private Integer idNfe;
	
}