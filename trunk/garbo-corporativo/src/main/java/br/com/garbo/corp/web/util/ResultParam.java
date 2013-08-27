package br.com.garbo.corp.web.util;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultParam {
	
	public ResultParam() {}
	
	public ResultParam(String nome, Object valor) {
		this.nome = nome;
		this.valor = valor;
	}

	private String nome;
	private Object valor;
	
	@Override
	public String toString() {
		return nome +"="+ valor.toString();
	}

}
