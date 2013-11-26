
package br.com.viasoft.portaldef.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="PESSOA")
@Getter @Setter
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="PESSOA_GENERATOR", allocationSize=1)
public class Pessoa implements EntityPattern {

	private static final long serialVersionUID = -3837608453032874511L;

	@Id
	@Column(name="IDPESSOA")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="NOME", length=60)
	private String nome;

	@Column(name="FANTASIA", length=80)
	private String fantasia;

	@Column(name="IDENTIFICACAO", length=20)
	private String identificacao;

	@Column(name="IE", length=30)
	private String ie;

	@OneToMany(fetch=FetchType.EAGER, mappedBy="pessoa")
	private Set<PessoaEmpresa> lsPesEmp;

	@Column(name="EMAILPRINC", length=120)
	private String emailPrincipal;

}