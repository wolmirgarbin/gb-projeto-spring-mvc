package br.com.viasoft.portaldef.repositories.custom;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.web.to.FiltroPessoaTO;


@Repository
public class PessoaRepositoryCustomImpl extends BaseRepositoryCustom<Pessoa> implements PessoaRepositoryCustom {
	
	private static final long serialVersionUID = -6079510077637832522L;

	
	@Override
	public List<Pessoa> buscar(FiltroPessoaTO to) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select pessoa from Pessoa pessoa ");
		sql.append("where pessoa.ativo = :ativo ");
		
		if( StringUtils.isNotEmpty( to.getNome() ) ) {
			sql.append("and (upper(pessoa.nome) like upper(:nome) or upper(pessoa.fantasia) like upper(:nome) ) ");
		}
		
		if( StringUtils.isNotEmpty( to.getIdentificacao() ) ) {
			sql.append("and pessoa.identificacao = :identificacao ");
		}
		
		sql.append("order by pessoa.nome ");
		
		final TypedQuery<Pessoa> query = entityManager.createQuery(sql.toString(), Pessoa.class);
		query.setParameter("ativo", SimNao.S);
		
		if( StringUtils.isNotEmpty( to.getNome() ) ) {
			query.setParameter("nome", "%"+ to.getNome() +"%");
		}
		
		if( StringUtils.isNotEmpty( to.getIdentificacao() ) ) {
			query.setParameter("identificacao", to.getIdentificacao());
		}
		
		
		query.setMaxResults( 20 );
		query.setFirstResult(to.getApartirDe());
		
		try {
			return query.getResultList();
		} catch(final Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Long count(FiltroPessoaTO to) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select count(pessoa) from Pessoa pessoa ");
		sql.append("where pessoa.ativo = :ativo ");
		
		if( StringUtils.isNotEmpty( to.getNome() ) ) {
			sql.append("and (upper(pessoa.nome) like upper(:nome) or upper(pessoa.fantasia) like upper(:nome) ) ");
		}
		
		if( StringUtils.isNotEmpty( to.getIdentificacao() ) ) {
			sql.append("and pessoa.identificacao = :identificacao ");
		}
		
		sql.append("order by pessoa.nome ");
		
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ativo", SimNao.S);
		
		if( StringUtils.isNotEmpty( to.getNome() ) ) {
			query.setParameter("nome", "%"+ to.getNome() +"%");
		}
		
		if( StringUtils.isNotEmpty( to.getIdentificacao() ) ) {
			query.setParameter("identificacao", to.getIdentificacao());
		}
		
		try {
			return (Long)query.getSingleResult();
		} catch(final Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}