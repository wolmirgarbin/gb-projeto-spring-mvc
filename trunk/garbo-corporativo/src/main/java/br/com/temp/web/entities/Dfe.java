package br.com.viasoft.portaldef.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="DFE")
@Getter @Setter
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="DFE_GENERATOR", allocationSize=1)
public class Dfe implements EntityPattern {

	private static final long serialVersionUID = -1626621025738624132L;

	@Id
	@Column(name="IDNOTA")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="IDNFE")
	@ManyToOne(fetch=FetchType.LAZY)
	private Documentos docNfe;
	
	@JoinColumn(name="IDDANF")
	@ManyToOne(fetch=FetchType.LAZY)
	private Documentos docDanf;
	
	@JoinColumn(name="IDPESSOAEMITENTE")
	@ManyToOne(fetch=FetchType.LAZY)
	private Pessoa emitente;
	
	@JoinColumn(name="IDPESSOADESTINATARIO")
	@ManyToOne(fetch=FetchType.LAZY)
	private Pessoa destinatario;
	
	@Column(name="CHAVE", length=60)
	private String chave;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTEMISSAO")
	private Date dtEmissao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTSAIDA")
	private Date dtSaida;
	
	@Column(name="NUMERO")
	private Integer numero;
	
	@Column(name="SERIE", length=3)
	private String serie;
	
	@Column(name="NATOP", length=60)
	private String natop;
	
	@Column(name="TIPOEMISSAO")
	private Integer tipoEmissao;
	
	@Column(name="TIPOAMB")
	private Integer tipoAmb;
	
	@Column(name="VNF")
	private Double vnf;
	
	@Column(name="RETSTAT")
	private Integer retStat;
	
	@Column(name="RETMOTIVO", length=100)
	private String retMotivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RETDTAUTORIZA")
	private Date retDtAutoriza;
	
	@Column(name="RETPROTOCOLO", length=40)
	private String retProtocolo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTHRCARREGADO", nullable=false)
	private Date dtHrCarregado;
	
	/*@OneToMany(cascade=CascadeType.ALL, mappedBy="dfe", orphanRemoval=true, fetch=FetchType.LAZY)
	private Set<Eventos> eventos;*/
}
