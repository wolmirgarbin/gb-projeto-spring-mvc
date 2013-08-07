package br.edu.materdei.spring.service;

import br.edu.materdei.spring.model.Cliente;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Order;

/**
 *
 * @author duduso
 */
public interface ClienteService extends Serializable{

    public int contarTotalClientes(Cliente filtros);

    public List<Cliente> pesquisarClientesPorDemanda(int firstResult, int maxResults, Cliente filtros, Order order);

    public void excluirCliente(Cliente cliente);

    public Cliente obterPorId(Long idCliente);

    public Long salvar(Cliente cliente);

    public void atualizar(Cliente cliente);

}
