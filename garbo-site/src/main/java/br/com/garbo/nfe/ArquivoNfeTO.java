package br.com.garbo.nfe;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArquivoNfeTO implements Serializable {

	private static final long serialVersionUID = -3866833809484392563L;

	private String nfeChave;
	
	private String nfeNatOp;
	private String nfeSerie;
	private String nfeNumeroNnf;
	private String nfeDtEmissao;
	private String nfeDtSaida;
	private String nfeTipoNf;
	private String nfeTpAmb;
	private String nfeVnf;
	
	private String emitIdentificacao; // cnpj ou cpf
	private String emitNome;
	private String emitFantasia;
	private String emitEmail;
	private String emitIE;
	
	private String destIdentificacao; // cnpj ou cpf
	private String destNome;
	private String destFantasia;
	private String destEmail;
	private String destIE;
	
	private String retStat;
	private String retMotivo;
	private String retDtAutoriza;
	private String retProtocolo;
}