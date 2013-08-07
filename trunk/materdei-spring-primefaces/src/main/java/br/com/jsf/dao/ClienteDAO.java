package br.com.jsf.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

import br.com.jsf.model.Cliente;

/**
 *
 * @author duduso
 */
public interface ClienteDAO extends Serializable {

    public int contarTotalClientes(Cliente filtros);

    public List<Cliente> pesquisarClientesPorDemanda(int firstResult, int maxResults, Cliente filtros, Order order);

    public void excluirCliente(Cliente cliente);

    public Cliente obterPorId(Long idCliente);

    public Long salvar(Cliente cliente);

    public void atualizar(Cliente cliente);

}
