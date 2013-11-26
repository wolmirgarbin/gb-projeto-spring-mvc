package br.com.viasoft.portaldef.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import br.com.viasoft.portaldef.enumerations.SimNao;

@Entity
@Table(name="CONFIG")
@Getter @Setter
//@SequenceGenerator(name="SEQ_CONFIG", sequenceName="CONFIG_GENERATOR", allocationSize=1)
public class Config implements EntityPattern {

	private static final long serialVersionUID = -5705493416108419873L;

	@Id
	@Column(name="IDCONFIG")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CONFIG")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="LOADEMAIL", length=120)
	private String loadEmail;

	@Column(name="LOADSENHA", length=40)
	private String loadSenha;

	@Column(name="LOADUSUARIO", length=50)
	private String loadUsuario;

	@Enumerated(EnumType.STRING)
	@Column(name="LOADAUTENTICTED", length=1)
	private SimNao loadAutenticted;

	@Column(name="LOADHOST", length=100)
	private String loadHost;

	@Column(name="LOADPROTOCOLO", length=4)
	private String loadProtocolo;

	@Column(name="LOADPORT", length=4)
	private Integer loadPort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOADDHLEITURA")
	private Date loadDhLeitura;

	@Enumerated(EnumType.STRING)
	@Column(name="LERTODAS", length=1)
	private SimNao lerTodas;

	@Transient
	private String emailNotificacao;

}