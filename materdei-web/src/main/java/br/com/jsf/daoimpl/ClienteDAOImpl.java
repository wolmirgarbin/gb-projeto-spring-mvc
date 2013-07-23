package br.com.jsf.daoimpl;

import br.com.jsf.constant.SpringScope;
import br.com.jsf.dao.ClienteDAO;
import br.com.jsf.model.Cliente;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author duduso
 */
@Repository("clienteDAO")
@Scope(SpringScope.SINGLETON)
public class ClienteDAOImpl implements ClienteDAO {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ClienteDAOImpl.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public int contarTotalClientes(Cliente filtros) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Cliente.class);
        criteria.setProjection(Projections.rowCount());
        addFiltrosConsulta(criteria, filtros);
        int total = ((Long) criteria.uniqueResult()).intValue();
        return total;
    }

    @Override
    public List<Cliente> pesquisarClientesPorDemanda(int firstResult, int maxResults, Cliente filtros, Order order) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Cliente.class);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        if(order == null){
            criteria.addOrder(Order.asc("nome"));
        }else{
            criteria.addOrder(order);
        }
        addFiltrosConsulta(criteria, filtros);
        return criteria.list();
    }

    @Override
    public void excluirCliente(Cliente cliente) {
        this.sessionFactory.getCurrentSession().delete(cliente);
        this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Long salvar(Cliente cliente){
        return (Long) this.sessionFactory.getCurrentSession().save(cliente);
    }

    @Override
    public void atualizar(Cliente cliente){
        this.sessionFactory.getCurrentSession().update(cliente);
    }

    @Override
    public Cliente obterPorId(Long idCliente){
        return (Cliente) this.sessionFactory.getCurrentSession().get(Cliente.class, idCliente);
    }

    private void addFiltrosConsulta(Criteria criteria, Cliente filtros) {
        if (filtros.getDataNascimento() != null) {
            criteria.add(Restrictions.eq("dataNascimento", filtros.getDataNascimento()));
        }
        if (filtros.getIdade() != null) {
            criteria.add(Restrictions.eq("idade", filtros.getIdade()));
        }
        if (StringUtils.isNotBlank(filtros.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtros.getNome(), MatchMode.ANYWHERE));
        }
        if (filtros.getSexo() != null) {
            criteria.add(Restrictions.eq("sexo", filtros.getSexo()));
        }
    }
}
