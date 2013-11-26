package br.com.viasoft.portaldef.enumerations;

import lombok.Getter;

@Getter
public enum OrdenacaoCapaEnum {

	CHAVE_DE_ACESSO_ASC("Menor chave de acesso", false, "CHAVE asc"),
	CHAVE_DE_ACESSO_DESC("Maior chave de acesso", false, "CHAVE desc"),
	EMISSAO_ASC("Menor data de emissão", false, "DTEMISSAO asc"),
	EMISSAO_DESC("Maior data de emissão", true, "DTEMISSAO desc"),
	CARREGAMENTO_ASC("Menor data de carregamento", false, "DTHRCARREGADO asc"),
	CARREGAMENTO_DESC("Maior data de carregamento", false, "DTHRCARREGADO desc"),
	NUMERO_ASC("Menor número", false, "NUMERO asc"),
	NUMERO_DESC("Maior número", false, "NUMERO desc");
	
	private String label;
	private boolean padrao;
	private String field;

	private OrdenacaoCapaEnum(String label, boolean padrao, String field) {
		this.label = label;
		this.padrao = padrao;
		this.field = field;
	}
	
}
