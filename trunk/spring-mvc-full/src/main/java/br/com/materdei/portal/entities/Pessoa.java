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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import br.com.viasoft.portaldef.enumerations.SimNao;


@Entity
@Table(name="PESSOA")
@Getter @Setter
@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="PESSOA_GENERATOR", allocationSize=1)
public class Pessoa implements EntityPattern {

	private static final long serialVersionUID = -3837608453032874511L;

	@Id
	@Column(name="IDPESSOA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	private Long id;
	
	@JoinColumn(name="IDEMPRESA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Empresa empresa;
	
	@Column(name="NOME", length=60)
	private String nome;
	
	@Column(name="FANTASIA", length=80)
	private String fantasia;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ATIVO", length=1)
	private SimNao ativo;
	
	@Column(name="IDENTIFICACAO", length=20)
	private String identificacao;

	@Column(name="EMAIL", length=100)
	private String email;
	
	@Column(name="IE", length=30)
	private String ie;
}
