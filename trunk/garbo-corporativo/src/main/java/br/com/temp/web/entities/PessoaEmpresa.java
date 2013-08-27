package br.com.viasoft.portaldef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PESSOAEMPRESA")
@Getter @Setter
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="PESSOAEMPRESA_GENERATOR", allocationSize=1)
public class PessoaEmpresa implements EntityPattern {

	private static final long serialVersionUID = -6687246361028978365L;

	@Id
	@Column(name="IDPESSOAEMPRESA")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="IDEMPRESA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Empresa empresa;
	
	@JoinColumn(name="IDPESSOA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Pessoa pessoa;
	
	/*@Column(name="EMAIL", length=200)
	private String email;*/ 
	
}