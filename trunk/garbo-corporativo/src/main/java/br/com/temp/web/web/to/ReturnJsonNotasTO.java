package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReturnJsonNotasTO implements Serializable {

	private static final long serialVersionUID = -6735004571694942404L;

	private Long id;
	private String numero;
	private String dtEmissao;
	private String carregamento;
	private String mailPrincipal;
	private String status;
	private String motivo;
	private String destinatario;
	private String identificacao;
	private String situacao;
	private Integer idCliente;
	private Integer idDanf;
	private Integer idNfe;
	
	private List<EventoNotasTO> eventos;
	
}