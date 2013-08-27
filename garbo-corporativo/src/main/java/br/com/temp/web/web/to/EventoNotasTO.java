package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EventoNotasTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	
}