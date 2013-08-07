package br.edu.materdei.spring.serviceimpl;

import br.edu.materdei.spring.constant.SpringScope;
import br.edu.materdei.spring.dao.ClienteDAO;
import br.edu.materdei.spring.model.Cliente;
import br.edu.materdei.spring.service.ClienteService;

import java.util.List;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author duduso
 */
@Service("clienteService")
@Transactional(readOnly = true)
@Scope(SpringScope.SINGLETON)
public class ClienteServiceImpl implements ClienteService{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ClienteDAO clienteDAO;

    @Override
    public int contarTotalClientes(Cliente filtros){
        return this.clienteDAO.contarTotalClientes(filtros);
    }

    @Override
    public List<Cliente> pesquisarClientesPorDemanda(int firstResult, int maxResults, Cliente filtros, Order order){
        return clienteDAO.pesquisarClientesPorDemanda(firstResult, maxResults, filtros, order);
    }

    @Override
    @Transactional(readOnly = false)
    public void excluirCliente(Cliente cliente){
        this.clienteDAO.excluirCliente(cliente);
    }

    @Override
    public Cliente obterPorId(Long idCliente){
        return this.clienteDAO.obterPorId(idCliente);
    }

    @Override
    @Transactional(readOnly = false)
    public Long salvar(Cliente cliente){
        return this.clienteDAO.salvar(cliente);
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Cliente cliente){
        this.clienteDAO.atualizar(cliente);
    }

}
