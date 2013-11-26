package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import br.com.viasoft.portaldef.enumerations.OrdenacaoCapaEnum;
import br.com.viasoft.portaldef.enumerations.TipoBuscaEnum;

@Getter @Setter
public class FilterDfeTO implements Serializable {

	private static final long serialVersionUID = 5440729554626822791L;

	private Integer apartirDe;
	private String dataInicio;
	private String dataFim;
	private String situacao;
	private String nome;
	private String identificacao;
	private TipoBuscaEnum tipoBusca;
	private String chave;
	private String dataInicialCarregamento;
	private String dataFinalCarregamento;
	private String status;
	// quantidade de registros por pagina
	private Integer qtdRegistros;
	// ordem dos registros na tabela
	public OrdenacaoCapaEnum ordenacao;
	

}