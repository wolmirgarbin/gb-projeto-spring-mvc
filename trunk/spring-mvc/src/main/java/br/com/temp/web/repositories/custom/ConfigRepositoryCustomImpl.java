package br.com.viasoft.portaldef.repositories.custom;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.viasoft.portaldef.entities.Config;


@Repository
public class ConfigRepositoryCustomImpl extends BaseRepositoryCustom<Config> implements ConfigRepositoryCustom {
	
	private static final long serialVersionUID = -6079510077637832522L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigRepositoryCustomImpl.class);
	

	
	@Override
	@Transactional
	public Config getNextMailLeitura() {
		final StringBuilder sql = new StringBuilder();
		sql.append("select config from Config config ");
		sql.append("where config.loadEmail is not null and config.loadSenha is not null and config.loadUsuario is not null ");
		sql.append("order by config.loadDhLeitura desc ");
		
		final TypedQuery<Config> query = entityManager.createQuery(sql.toString(), Config.class);
		query.setMaxResults( 1 );
		
		try {
			final List<Config> ret = query.getResultList();
			if( ret != null )
				return ret.get(0);
			else
				return null;
		} catch(final Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}