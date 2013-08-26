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

@Entity
@Table(name="USUARIOEMPRESA")
@Getter @Setter
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="USUARIOEMPRESA_GENERATOR", allocationSize=1)
public class UsuarioEmpresa implements EntityPattern {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="IDUSUEMP")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDEMPRESA")
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDUSUARIO")
	private Usuario usuario;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PADRAO", length=1)
	private SimNao padrao;

}