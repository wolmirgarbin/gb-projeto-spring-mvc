package br.com.viasoft.portaldef.repositories.custom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.viasoft.enumeration.FormatDate;
import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.enumerations.TipoBuscaEnum;
import br.com.viasoft.portaldef.enumerations.TipoDocumento;
import br.com.viasoft.portaldef.web.to.EmpresaTO;
import br.com.viasoft.portaldef.web.to.EventoNotasTO;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.FilterDownloadGrupoTO;
import br.com.viasoft.portaldef.web.to.ReturnJsonNotasTO;
import br.com.viasoft.util.DateUtil;


@Repository
public class DfeRepositoryCustomImpl extends BaseRepositoryCustom<Dfe> implements DfeRepositoryCustom {

	private static final long serialVersionUID = -6079510077637832522L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DfeRepositoryCustomImpl.class);


	@Override
	public List<ReturnJsonNotasTO> findByFilter(Usuario usuario, FilterDfeTO filter) {

		final SimpleDateFormat formate = new SimpleDateFormat(FormatDate.DAY_MONTH_YEAR.getFormat());
		final SimpleDateFormat formateCompleto = new SimpleDateFormat(FormatDate.DAY_MONTH_YEAR_HOUR_MINUTE_SECOND.getFormat());
		final List<ReturnJsonNotasTO> toReturn = new ArrayList<ReturnJsonNotasTO>();

		// caso não seja passado o tipo da busca valido retorna vazio
		if( filter.getTipoBusca() == null )
			return new ArrayList<ReturnJsonNotasTO>();
		
		Query query = null;

		query = getSqlAdministrador(usuario, filter);

		final List<Object[]> list = query.getResultList();

		ReturnJsonNotasTO to = null;
		for (final Object[] dados : list) {
			to = new ReturnJsonNotasTO();

			to.setId( Long.valueOf(String.valueOf(dados[0])) );
			to.setNumero( String.valueOf(dados[1]) );
			to.setIdCliente( Integer.valueOf(String.valueOf(dados[2])) );
			to.setIdDanf( dados[3] != null ? Integer.valueOf(String.valueOf(dados[3])) : null );
			to.setIdNfe( dados[4] != null ? Integer.valueOf(String.valueOf(dados[4])) : null );
			to.setDtEmissao( dados[5] != null ? formate.format(dados[5]) : "-" );
			to.setSituacao(String.valueOf(dados[6]));
			to.setDestinatario( String.valueOf(dados[8]));
			to.setIdentificacao(String.valueOf(dados[9]));
			to.setCarregamento(formateCompleto.format(dados[10]));
			to.setStatus(String.valueOf(dados[11]));
			to.setMotivo(String.valueOf(dados[12]));
			to.setMailPrincipal( dados[13] != null ? String.valueOf(dados[13]) : "" );

			toReturn.add(to);
		}

		return adicionaEventosInList(toReturn);
	}


	private Query getSqlAdministrador(Usuario usuario, FilterDfeTO filter) {

		final List<Object> parans = new ArrayList<Object>();
		final StringBuilder sql = new StringBuilder();

		sql.append("select  DFE.IDNOTA, DFE.CHAVE, DFE.IDPESSOADESTINATARIO, DFE.IDDANF, DFE.IDNFE, DFE.DTEMISSAO, DFE.NUMERO, DFE.TIPOAMB, ");
		sql.append("PESMOSTRAR.NOME, PESMOSTRAR.IDENTIFICACAO, DFE.DTHRCARREGADO, DFE.RETSTAT, DFE.RETMOTIVO, PESMOSTRAR.EMAILPRINC ");
		sql.append("from DFE ");

		// se for pra mostrar as recebidas adiciona os inners corretamente
		if( TipoBuscaEnum.RECEBIDAS.equals(filter.getTipoBusca()) ) {
			sql.append("INNER JOIN PESSOA PESMOSTRAR ON PESMOSTRAR.IDPESSOA = DFE.IDPESSOAEMITENTE ");
			sql.append("INNER JOIN PESSOA PESFILTRAR ON PESFILTRAR.IDPESSOA = DFE.IDPESSOADESTINATARIO ");
			
		// se for para mostrar as enviadas, monta os inners corretos
		} else if( TipoBuscaEnum.ENVIADAS.equals(filter.getTipoBusca()) ) {
			sql.append("INNER JOIN PESSOA PESMOSTRAR ON PESMOSTRAR.IDPESSOA = DFE.IDPESSOADESTINATARIO ");
			sql.append("INNER JOIN PESSOA PESFILTRAR ON PESFILTRAR.IDPESSOA = DFE.IDPESSOAEMITENTE ");
		
		// o mesmo para as outras notas
		} else if( TipoBuscaEnum.OUTRAS_NOTAS.equals(filter.getTipoBusca()) ) {
			sql.append("INNER JOIN OUTROSENVOLVIDOS ENVOLVIDOS ON ENVOLVIDOS.IDNOTA = DFE.IDNOTA ");
			sql.append("INNER JOIN PESSOA PESFILTRAR ON PESFILTRAR.IDPESSOA = ENVOLVIDOS.IDPESSOA ");
			
			sql.append("INNER JOIN PESSOA PESMOSTRAR ON PESMOSTRAR.IDPESSOA = DFE.IDPESSOAEMITENTE ");
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
			final Date dataFim = DateUtil.toDate(filter.getDataFim() +" 23:59:59", FormatDate.DAY_MONTH_YEAR_HOUR_MINUTE_SECOND);
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

		if( StringUtils.isNotBlank(filter.getChave()) ) {
			sql.append("and DFE.CHAVE like ? ");
			parans.add( "%"+ filter.getChave() );
		}

		if( StringUtils.isNotBlank(filter.getDataInicialCarregamento()) ) {
			final Date data = DateUtil.obtemDateHorarioZerado(DateUtil.toDate(filter.getDataInicialCarregamento(), FormatDate.DAY_MONTH_YEAR));
			if( data != null ) {
				sql.append("and DFE.DTHRCARREGADO >= ? ");
				parans.add( data );
			}
		}

		if( StringUtils.isNotBlank(filter.getDataFinalCarregamento()) ) {
			final Date data = DateUtil.toDate(filter.getDataFinalCarregamento()+" 23:59:59", FormatDate.DAY_MONTH_YEAR_HOUR_MINUTE_SECOND);
			if( data != null ) {
				sql.append("and DFE.DTHRCARREGADO <= ? ");
				parans.add( data );
			}
		}

		if( StringUtils.isNotBlank(filter.getStatus()) ) {
			sql.append("and DFE.RETSTAT = ? ");
			parans.add( filter.getStatus() );
		}

		/*sql.append("and (PESFILTRAR.IDENTIFICACAO = ? or PESFILTRAR.IDENTIFICACAO = ?) ");
		parans.add( usuario.getPessoa().getIdentificacao() );
		parans.add( usuario.getEmpresaBase().getIdentificacao() );*/

		sql.append("and (PESFILTRAR.IDENTIFICACAO = ? ) ");
		parans.add( usuario.getEmpresaBase() != null ? usuario.getEmpresaBase().getIdentificacao() : usuario.getPessoa().getIdentificacao() );

		//sql.append("and DFE.TIPOAMB = 1 "); // ambiente produção-homologação

		sql.append("order by DFE.").append( filter.ordenacao.getField() );

		final Query query = entityManager.createNativeQuery(sql.toString());
		query.setMaxResults( filter.getQtdRegistros() > 100 ? 100 : filter.getQtdRegistros() > 5 ? filter.getQtdRegistros() : 5 );

		for (int i = 0; i < parans.size(); i++) {
			query.setParameter( i + 1, parans.get(i));
		}
		return query;
	}



	private List<ReturnJsonNotasTO> adicionaEventosInList(List<ReturnJsonNotasTO> list) {
		if( list == null || list.isEmpty() )
			return list;

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT IDDOCUMENTOS, DESCRICAO FROM EVENTOS WHERE IDNOTA = ? ");

		try {
			for (final ReturnJsonNotasTO returnJsonNotasTO : list) {
				final Query query = entityManager.createNativeQuery(sql.toString());
				query.setParameter(1, returnJsonNotasTO.getId());
				final List<Object[]> eventos = query.getResultList();

				if( returnJsonNotasTO.getEventos() == null )
					returnJsonNotasTO.setEventos( new ArrayList<EventoNotasTO>() );

				if( eventos != null && eventos.size() > 0 ) {
					EventoNotasTO eventoNotasTO = null;
					for (final Object[] evt : eventos) {
						eventoNotasTO = new EventoNotasTO();
						eventoNotasTO.setId( Long.valueOf(String.valueOf(evt[0])) );
						eventoNotasTO.setDescricao( String.valueOf(evt[1]) );

						returnJsonNotasTO.getEventos().add( eventoNotasTO );
					}
				}
			}
		} catch(final Exception e){
			e.printStackTrace();
		}

		return list;
	}


	@Override
	@Transactional
	public void updateDanf(Documentos danf, Dfe dfe) {
		dfe.setDocDanf(danf);
		entityManager.merge(dfe);
	}


	@Override
	public Dfe findOne(String chaveNFe, String chaveCTe) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe inner join dfe.destinatario where dfe.chave = :chaveNFe or dfe.chave = :chaveCFe ");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("chaveNFe", chaveNFe);
		query.setParameter("chaveCFe", chaveCTe);
		query.setMaxResults(1);

		try {
			final List<Dfe> resultList = query.getResultList();
			if( resultList != null && resultList.size() > 0 )
				return resultList.get(0);
			else
				return null;
		} catch(final Exception e){
			LOGGER.error("Tentou buscar um arquivo inexistente");
		}
		return null;
	}


	@Override
	public Dfe findOne(String chaveUnica) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe inner join dfe.destinatario where dfe.chave = :chave ");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("chave", chaveUnica);
		query.setMaxResults(1);

		try {
			final List<Dfe> resultList = query.getResultList();
			if( resultList != null && resultList.size() > 0 )
				return resultList.get(0);
			else
				return null;
		} catch(final Exception e){
			LOGGER.error("Tentou buscar um arquivo inexistente");
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
		sql.append("inner join dfe.destinatario ");
		sql.append("inner join dfe.emitente ");
		sql.append("where (dfe.destinatario.identificacao = :identificacao or dfe.emitente.identificacao = :emitente) and ( dfe.docDanf.id = :documento or dfe.docNfe.id = :documento ) ");

		final TypedQuery<Dfe> query = entityManager.createQuery(sql.toString(), Dfe.class);
		query.setParameter("identificacao", identificacao);
		query.setParameter("emitente", identificacao);
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


	@Override
	public List<Documentos> listDocDownload(FilterDownloadGrupoTO to) {
		if( to == null || to.getIds().length() <= 0 )
			return null;

		final List<Documentos> toReturn = new ArrayList<Documentos>();

		final StringBuilder sql = new StringBuilder();

		// agrupa os selects...
		// XML ( NF-e - CT-e )
		if( to.getDocXml() ) {
			sql.append("SELECT DFE.IDNFE, DOCUMENTOS.NOME, DOCUMENTOS.ARQUIVO FROM DFE ");
			sql.append("INNER JOIN DOCUMENTOS ON DOCUMENTOS.IDDOCUMENTOS = DFE.IDNFE ");
			sql.append("WHERE DFE.IDNOTA IN (").append( to.getIds() ).append(") ");
		}

		// DANFE
		if( to.getDocDanfe() ) {
			if( ! "".equals( sql.toString() ) )
				sql.append(" UNION ");

			sql.append("SELECT DFE.IDDANF, DOCUMENTOS.NOME, DOCUMENTOS.ARQUIVO FROM DFE ");
			sql.append("INNER JOIN DOCUMENTOS ON DOCUMENTOS.IDDOCUMENTOS = DFE.IDDANF ");
			sql.append("WHERE DFE.IDNOTA IN (").append( to.getIds() ).append(") ");
		}

		// EVENTOS (cancelamento e carta correção)
		if( to.getDocEventos() ) {
			if( ! "".equals( sql.toString() ) )
				sql.append(" UNION ");

			sql.append("SELECT DISTINCT EVENTOS.IDDOCUMENTOS, DOCUMENTOS.NOME, DOCUMENTOS.ARQUIVO FROM EVENTOS ");
			sql.append("INNER JOIN DOCUMENTOS ON DOCUMENTOS.IDDOCUMENTOS = EVENTOS.IDDOCUMENTOS ");
			sql.append("WHERE EVENTOS.IDNOTA IN (").append( to.getIds() ).append(") ");
		}

		if( "".equals( sql.toString() ) )
			return null;


		try {
			final Query query = entityManager.createNativeQuery(sql.toString());
			final List<Object[]> docs = query.getResultList();

			if( docs != null && docs.size() > 0 ) {
				Documentos documento = null;
				for (final Object[] doc : docs) {
					documento = new Documentos();
					documento.setId(Long.valueOf( String.valueOf( doc[0] ) ));
					documento.setNome( String.valueOf( doc[1] ) );
					documento.setArquivo( (byte[])doc[2] );

					toReturn.add( documento );
				}
			}
		} catch(final Exception e){
			e.printStackTrace();
		}

		return toReturn;
	}


	@Override
	public List<EmpresaTO> relConsumoMes(Integer mes, Integer ano, Long contratante) {
		if( mes == null || ano == null )
			return null;

		final StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(" empresa.nome,  ");
		sql.append(" empresa.identificacao, ");
		sql.append(" empresa.cidade,  ");
		sql.append(" empresa.uf,  ");
		sql.append(" (select count(*) from DFE dfe ");
		sql.append("  inner join PESSOA emitente on emitente.idpessoa = dfe.IDPESSOAEMITENTE ");
		sql.append("  inner join PESSOA destinatario on destinatario.idpessoa = dfe.IDPESSOAdestinatario ");
		sql.append("  where (emitente.identificacao = empresa.identificacao or destinatario.identificacao = empresa.identificacao) ");
		sql.append("    and MONTH(dfe.DTHRCARREGADO) = ? ");
		sql.append("    and YEAR(dfe.DTHRCARREGADO) = ? ) ");
		sql.append(" as quantidade ");
		sql.append("from EMPRESA empresa ");
		sql.append("where empresa.ativa = 'S' and empresa.idcontratante = ? ");


		final List<EmpresaTO> toReturn = new ArrayList<EmpresaTO>();
		try {
			final Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, mes);
			query.setParameter(2, ano);
			query.setParameter(3, contratante);
			final List<Object[]> docs = query.getResultList();

			if( docs != null && docs.size() > 0 ) {
				EmpresaTO empresa = null;
				for (final Object[] doc : docs) {
					empresa = new EmpresaTO();

					empresa.setNome( String.valueOf( doc[0] ) );
					empresa.setIdentificacao( String.valueOf( doc[1] ) );
					empresa.setCidade( String.valueOf( doc[2] ) );
					empresa.setUf( String.valueOf( doc[3] ) );
					empresa.setQuantidade( Integer.valueOf( String.valueOf( doc[4] ) ) );

					toReturn.add( empresa );
				}
			}
		} catch(final Exception e){
			e.printStackTrace();
		}

		return toReturn;
	}


	@Override
	public List<Dfe> findByRestIntegracao(String cnpjDestinatario) {
		return lsRestIntegracao(cnpjDestinatario, null);
	}

	@Override
	public List<Dfe> findByRestIntegracao(String cnpjDestinatario, String cnpjEmitente) {
		return lsRestIntegracao(cnpjDestinatario, cnpjEmitente);
	}


	private List<Dfe> lsRestIntegracao(String cnpjDestinatario, String cnpjEmitente) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe inner join dfe.destinatario left outer join fetch dfe.emitente ");
		sql.append("where dfe.destinatario.identificacao = :cnpjDestinatario and ( dfe.sincronizado = :simNao or dfe.sincronizado is null ) ");
		sql.append("and dfe.nfeOuCte = :nfeOuCte ");

		if( cnpjEmitente != null )
			sql.append("and dfe.emitente.identificacao = :cnpjEmitente ");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("cnpjDestinatario", cnpjDestinatario);
		query.setParameter("simNao", SimNao.N);
		query.setParameter("nfeOuCte", TipoDocumento.NFE);

		if( cnpjEmitente != null )
			query.setParameter("cnpjEmitente", cnpjEmitente);

		try {
			return query.getResultList();
		} catch(final Exception e){
			LOGGER.error("Tentou buscar um arquivo inexistente");
		}
		return null;
	}


	@Override
	public Dfe findOneByChaveJoinNfe(String chave) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select dfe from Dfe dfe left outer join fetch dfe.docNfe where dfe.chave = :chave and dfe.nfeOuCte = :nfeOuCte ");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("chave", chave);
		query.setParameter("nfeOuCte", TipoDocumento.NFE);

		query.setMaxResults(1);

		try {
			final List<Dfe> resultList = query.getResultList();
			if( resultList != null && resultList.size() > 0 )
				return resultList.get(0);
			else
				return null;
		} catch(final Exception e){
			LOGGER.error("Tentou buscar um arquivo inexistente");
		}
		return null;
	}

}
