package br.com.viasoft.portaldef.web.to.xml;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@XmlRootElement(name = "documento")
public class Documento {

	private String chave;
	private String emitente;
	private Date emissao;
	private Integer numero;
	private String serie;

}
