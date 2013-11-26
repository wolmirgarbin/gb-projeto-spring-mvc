
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
@Table(name="OUTROSENVOLVIDOS")
@Getter @Setter
public class OutrosEnvolvidos implements EntityPattern {

	private static final long serialVersionUID = -3837608453032874511L;

	@Id
	@Column(name="IDOUTROSENVOLVIDOS")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="TIPO", length=20)
	private String tipo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDPESSOA")
	private Pessoa pessoa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDNOTA")
	private Dfe dfe;

}