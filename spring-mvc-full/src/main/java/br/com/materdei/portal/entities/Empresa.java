package br.com.viasoft.portaldef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="EMPRESA")
@Getter @Setter
@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="EMPRESA_GENERATOR", allocationSize=1)
public class Empresa implements EntityPattern {

	private static final long serialVersionUID = -1515740354141095465L;

	@Id
	@Column(name="IDEMPRESA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	private Long id;
	
	@JoinColumn(name="IDCONFIG", columnDefinition="IDCONFIG")
	@ManyToOne(fetch=FetchType.LAZY)
	private Config config;
	
	@Column(name="NOME", length=80)
	private String nome;
	
	@Column(name="IDENTIFICACAO", length=20)
	private String identificacao;
	
	@Column(name="UF", length=2)
	private String uf;
	
	@Column(name="CIDADE", length=100)
	private String cidade;
	
	@Column(name="BAIRRO", length=60)
	private String bairro;
	
	@Column(name="CEP", length=10)
	private String cep;
	
	@Column(name="RUA", length=60)
	private String rua;
	
}
