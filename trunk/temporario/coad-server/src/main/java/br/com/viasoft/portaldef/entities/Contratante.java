package br.com.viasoft.portaldef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CONTRATANTE")
@Getter @Setter
public class Contratante implements EntityPattern {

	private static final long serialVersionUID = -5705493416108419873L;

	@Id
	@Column(name="IDCONTRATANTE")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="NOME", length=100)
	private String nome;

	@Column(name="EMAIL", length=100)
	private String email;

	@Column(name="FONEDDD", length=3)
	private String foneDDD;

	@Column(name="FONENUMERO", length=9)
	private String foneNumero;

	@Column(name="CELUDDD", length=3)
	private String celuDDD;

	@Column(name="CELUNUMERO", length=9)
	private String celuNumero;

	@Column(name="PACOTE")
	private Integer pacote;

	@Column(name="COMPLEMENTO", length=100)
	private String complemento;
}