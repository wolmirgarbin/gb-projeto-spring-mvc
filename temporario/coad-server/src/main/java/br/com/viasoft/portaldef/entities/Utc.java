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
@Table(name="UTC")
@Getter @Setter
public class Utc implements EntityPattern {

	private static final long serialVersionUID = 599876220256078169L;

	@Id
	@Column(name="IDUTC")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="VALOR", length=6)
	private String valor;

	@Column(name="DESCRICAO", length=50)
	private String descricao;

}