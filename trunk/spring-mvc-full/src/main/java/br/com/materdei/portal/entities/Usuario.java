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
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;

@Entity
@Table(name="USUARIO")
@Getter @Setter
@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="USUARIO_GENERATOR", allocationSize=1)
public class Usuario implements EntityPattern {

	private static final long serialVersionUID = 5847339135265901604L;

	@Id
	@Column(name="IDUSUARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	private Long id;
	
	@JoinColumn(name="IDPESSOA")
	@ManyToOne(fetch=FetchType.EAGER)
	private Pessoa pessoa;
	
	@Column(name="USUARIO", length=20, unique=true)
	@NotBlank(message="{usuario.usuario.vazio}") 
	@Size(min=11, max=14, message="{usuario.usuario.tamanho}")
	private String usuario;
	
	@Column(name="SENHA", length=100)
	@NotBlank(message="{usuario.senha.vazio}") 
	@Size(min=3, message="{usuario.senha.tamanho}")
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ATIVO", length=1)
	private SimNao ativo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="MUDOUSENHA", length=1)
	private SimNao mudouSenha;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROLE", length=20)
	private Roles role;
}
