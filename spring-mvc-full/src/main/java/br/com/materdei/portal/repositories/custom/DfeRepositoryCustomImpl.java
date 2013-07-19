package br.com.viasoft.portaldef.repositories.custom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.viasoft.enumeration.FormatDate;
import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;
import br.com.viasoft.util.DateUtil;


@Repository
public class DfeRepositoryCustomImpl extends BaseRepositoryCustom<Dfe> implements DfeRepositoryCustom {
	
	private static final long serialVersionUID = -6079510077637832522L;

	@Override
	public List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter) {
		
		final boolean consultaParaCliente = Roles.ROLE_CLIENTE.equals( usuario.getRole() );
		
		final SimpleDateFormat formate = new SimpleDateFormat(FormatDate.DAY_MONTH_YEAR.getFormat());
		final List<ReturnJsonNotasTO> toReturn = new ArrayList<ReturnJsonNotasTO>();
		
		Query query = null;
		
		if( consultaParaCliente ) {
			query = getSqlCliente(usuario, filter);
		} else {
			query = getSqlAdministrador(usuario, filter);
		}
		
		final List<Object[]> list = query.getResultList();
		
		ReturnJsonNotasTO to = null;
		for (final Object[] dados : list) {
			to = new ReturnJsonNotasTO();
			
			to.setId( Long.valueOf(String.valueOf(dados[0])) );
			to.setNumero( String.valueOf(dados[1]) );
			to.setIdCliente( Integer.valueOf(String.valueOf(dados[2])) );
			to.setIdDanf( dados[3] != null ? Integer.valueOf(String.valueOf(dados[3])) : null );
			to.setIdNfe( dados[4] != null ? Integer.valueOf(String.valueOf(dados[4])) : null );
			to.setDtEmissao(formate.format(dados[5]));
			to.setSituacao(String.valueOf(dados[6]));
			to.setDestinatario( String.valueOf(dados[8]) );
			to.setIdentificacao(String.valueOf(dados[9]));
			to.setEmail(dados[10] != null ? String.valueOf(dados[10]) : "-");
			
			toReturn.add(to);
		}
		
		return toReturn;
	}
	
	
	private Query getSqlCliente(Usuario usuario, FilterDfeTO filter) {
		final List<Object> parans = new ArrayList<Object>();
		final StringBuilder sql = new StringBuilder();
		
		sql.append("select  DFE.IDNOTA, DFE.CHAVE, DFE.IDCLIENTE, DFE.IDDANF, DFE.IDNFE, DFE.DTEMISSAO, DFE.NUMERO, DFE.TIPOAMB, ");
		sql.append("PESMOSTRAR.NOME, PESMOSTRAR.IDENTIFICACAO, PESFILTRAR.EMAIL ");
		sql.append("from DFE ");
		
		sql.append("INNER JOIN PESSOA PESMOSTRAR ON PESMOSTRAR.IDPESSOA = DFE.IDEMITENTE ");
		sql.append("INNER JOIN PESSOA PESFILTRAR ON PESFILTRAR.IDPESSOA = DFE.IDCLIENTE ");
		
		sql.append("where 1=1 ");
		
		if( StringUtils.isNotBlank(filter.getDataInicio()) ) {
			DateUtil.isFormatoDate(filter.getDataInicio());
			final Date dataInicio = DateUtil.toDate(filter.getDataInicio(), FormatDate.DAY_MONTH_YEAR);
			if( dataInicio != null ) {
				sql.append("and DFE.DTEMISSAO >= ? ");
				parans.add( dataInicio );
			}
		}
		
		if( StringUtils.isNotBlank(filter.getDataFim()) ) {
			final Date dataFim = DateUtil.toDate(filter.getDataFim(), FormatDate.DAY_MONTH_YEAR);
			if( dataFim != null ) {
				sql.append("and DFE.DTEMISSAO <= ? ");
				parans.add( dataFim );
			}
		}
		
		sql.append("and PESFILTRAR.IDENTIFICACAO = ? ");
		parans.add( usuario.getPessoa().getIdentificacao() );
		
		sql.append("and DFE.TIPOAMB = 2 "); // ambiente produção-homologação
		sql.append("order by DFE.DTEMISSAO desc ");
		
		final Query query = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < parans.size(); i++) {
			query.setParameter( i + 1, parans.get(i));
		}
		return query;
	}
	
	
	private Query getSqlAdministrador(Usuario usuario, FilterDfeTO filter) {
		
		// visualizar as enviadas ou recebidas
		final boolean recebidas = filter.getTipoBusca().equals("recebidos");
		
		final List<Object> parans = new ArrayList<Object>();
		final StringBuilder sql = new StringBuilder();
		
		sql.append("select  DFE.IDNOTA, DFE.CHAVE, DFE.IDCLIENTE, DFE.IDDANF, DFE.IDNFE, DFE.DTEMISSAO, DFE.NUMERO, DFE.TIPOAMB, ");
		sql.append("PESMOSTRAR.NOME, PESMOSTRAR.IDENTIFICACAO, PESMOSTRAR.EMAIL ");
		sql.append("from DFE ");
		
		if( recebidas ) {
			sql.append("INNER JOIN PESSOA PESMOSTRAR ON PESMOSTRAR.IDPESSOA = DFE.IDEMITENTE ");
			sql.append("INNER JOIN PESSOA PESFILTRAR ON PESFILTRAR.IDPESSOA = DFE.IDCLIENTE ");
		} else {
			sql.append("INNER JOIN PESSOA PESMOSTRAR ON PESMOSTRAR.IDPESSOA = DFE.IDCLIENTE ");
			sql.append("INNER JOIN PESSOA PESFILTRAR ON PESFILTRAR.IDPESSOA = DFE.IDEMITENTE ");
		}
		
		sql.append("where 1=1 ");
			
		if( StringUtils.isNotBlank(filter.getDataInicio()) ) {
			DateUtil.isFormatoDate(filter.getDataInicio());
			final Date dataInicio = DateUtil.toDate(filter.getDataInicio(), FormatDate.DAY_MONTH_YEAR);
			if( dataInicio != null ) {
				sql.append("and DFE.DTEMISSAO >= ? ");
				parans.add( dataInicio );
			}
		}
		
		if( StringUtils.isNotBlank(filter.getDataFim()) ) {
			final Date dataFim = DateUtil.toDate(filter.getDataFim(), FormatDate.DAY_MONTH_YEAR);
			if( dataFim != null ) {
				sql.append("and DFE.DTEMISSAO <= ? ");
				parans.add( dataFim );
			}
		}

		if( StringUtils.isNotBlank(filter.getSituacao()) ) {
			sql.append("and DFE.NUMERO = ? ");
			parans.add( filter.getSituacao() );
		}
		
		if( StringUtils.isNotBlank(filter.getNome()) ) {
			sql.append("and ( upper(PESMOSTRAR.NOME) like upper(?) or upper(PESMOSTRAR.FANTASIA) like upper(?) )");
			parans.add( "%"+ filter.getNome() +"%" );
			parans.add( "%"+ filter.getNome() +"%" );
		}
		
		if( StringUtils.isNotBlank(filter.getIdentificacao()) ) {
			sql.append("and PESMOSTRAR.IDENTIFICACAO = ? ");
			parans.add( filter.getIdentificacao() );
		}
		
		sql.append("and PESFILTRAR.IDENTIFICACAO = ? ");
		parans.add( usuario.getPessoa().getIdentificacao() );
		
		sql.append("and DFE.TIPOAMB = 2 "); // ambiente produção-homologação
		sql.append("order by DFE.DTEMISSAO desc ");
		
		final Query query = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < parans.size(); i++) {
			query.setParameter( i + 1, parans.get(i));
		}
		return query;
	}


	@Override
	@Transactional
	public void updateDanf(Documentos danf, Dfe dfe) {
		dfe.setDocDanf(danf);
		entityManager.merge(dfe);
	}
	
	
	@Override
	public Dfe findOne(String chave) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe inner join dfe.cliente where dfe.chave = :chave ");
		
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("chave", chave);
		
		try {
			return (Dfe) query.getSingleResult();
		} catch(final Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Dfe findOneAndDocs(Long id) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe left join fetch dfe.docDanf left join fetch dfe.docNfe where dfe.id = :id ");
		
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("id", id);
		
		try {
			return (Dfe) query.getSingleResult();
		} catch(final Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean permiteDownload(String identificacao, Long id) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe ");
		sql.append("inner join dfe.cliente ");
		sql.append("where dfe.cliente.identificacao = :identificacao and ( dfe.docDanf.id = :documento or dfe.docNfe.id = :documento ) ");
		
		final TypedQuery<Dfe> query = entityManager.createQuery(sql.toString(), Dfe.class);
		query.setParameter("identificacao", identificacao);
		query.setParameter("documento", id);
		
		try {
			return query.getSingleResult() != null;
		} catch(final Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	@Transactional
	public Dfe merge(Dfe dfe) {
		return entityManager.merge(dfe);
	}

}
