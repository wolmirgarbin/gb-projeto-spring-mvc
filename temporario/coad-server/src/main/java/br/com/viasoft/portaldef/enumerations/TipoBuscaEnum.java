package br.com.viasoft.portaldef.enumerations;

import lombok.Getter;

@Getter
public enum TipoBuscaEnum {

	ENVIADAS("destinatário", "enviadas"),
	RECEBIDAS("emissor", "recebidos"),
	OUTRAS_NOTAS("emissor", "recebidos");
	
	private String labelField;
	private String nomeFiltro;
	
	private TipoBuscaEnum(String labelField, String nomeFiltro) {
		this.labelField = labelField;
		this.nomeFiltro = nomeFiltro;
	}
	
	
}
