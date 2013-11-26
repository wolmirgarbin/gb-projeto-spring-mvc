package br.com.viasoft.portaldef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="DOCUMENTOS")
@Getter @Setter
@XmlRootElement(name = "documento")
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="DOCUMENTOS_GENERATOR", allocationSize=1)
public class Documentos implements EntityPattern {

	private static final long serialVersionUID = -8559080241478164107L;

	@Id
	@Column(name="IDDOCUMENTOS")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="ARQUIVO")
	private byte[] arquivo;

	@Column(name="NOME", length=200)
	private String nome;

	@Column(name="EXTENCAO", length=8)
	private String extencao;
}