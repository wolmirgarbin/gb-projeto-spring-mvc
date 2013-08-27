package br.com.garbo.corp.web.entities;

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
@Table(name="EVENTOS")
@Getter @Setter
//@SequenceGenerator(name="SEQ_GENERATOR", sequenceName="EVENTOS_GENERATOR", allocationSize=1)
public class Eventos implements EntityPattern {

	private static final long serialVersionUID = -6056231662393057766L;

	@Id
	@Column(name="IDEVENTOS")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="IDDOCUMENTOS")
	@ManyToOne(fetch=FetchType.LAZY)
	private Documentos documento;
	
	@JoinColumn(name="IDNOTA")
	@ManyToOne(fetch=FetchType.LAZY)
	private Dfe dfe;
	
	@Column(name="DESCRICAO", length=20)
	private String descricao;
	
	@Column(name="SEQUENCIAL", length=20)
	private Integer sequencial;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTEVENTO")
	private Date dtEvento;
	
	@Column(name="TIPO", length=20)
	private String tipo;
	
	@Column(name="CHAVE", length=60)
	private String chave;
	
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
}
