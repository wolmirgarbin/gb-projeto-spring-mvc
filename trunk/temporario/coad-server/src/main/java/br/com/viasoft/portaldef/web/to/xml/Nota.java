package br.com.viasoft.portaldef.web.to.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@XmlRootElement(name = "nota")
public class Nota {

	private String identificacao;
	private List<Documento> documento;

}
