package br.com.garbo.corp.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import br.com.viasoft.portaldef.enumerations.SimNao;

@Entity
@Table(name="GERENCIADORES")
@Getter @Setter
public class Gerenciadores implements EntityPattern {

	private static final long serialVersionUID = 5847339135265901604L;

	@Id
	@Column(name="IDGERENCIADORES")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="USUARIO", length=50, unique=true)
	private String usuario;
	
	@Column(name="SENHA", length=60)
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ATIVO", length=1)
	private SimNao ativo;
	
}
