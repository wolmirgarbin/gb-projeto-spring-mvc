package br.com.viasoft.portaldef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CONFIG")
@Getter @Setter
@SequenceGenerator(name="SEQ_CONFIG", sequenceName="CONFIG_GENERATOR", allocationSize=1)
public class Config implements EntityPattern {

	private static final long serialVersionUID = -5705493416108419873L;
	
	@Id
	@Column(name="IDCONFIG")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CONFIG")
	private Long id;
	
	@Column(name="LOGO")
	private String logo;
	
	@Column(name="SITE")
	private String site;
	
	@Column(name="EMAILNOTIFICACAO", length=200)
	private String emailNotificacao;
	
}