package br.com.viasoft.portaldef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.enumerations.TipoEmpresa;

@Entity
@Table(name="EMPRESA")
@Getter @Setter
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="EMPRESA_GENERATOR", allocationSize=1)
public class Empresa implements EntityPattern {

	private static final long serialVersionUID = -1515740354141095465L;

	@Id
	@Column(name="IDEMPRESA")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDEMPMATRIZ")
	private Empresa empresaMatriz;

	@Enumerated(EnumType.STRING)
	@Column(name="TIPO", length=1)
	private TipoEmpresa tipo;

	@Enumerated(EnumType.STRING)
	@Column(name="ATIVA", length=1)
	private SimNao ativa;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDCONFIG")
	private Config config;

	@Column(name="QTDMES")
	private Integer qtdMes;

}