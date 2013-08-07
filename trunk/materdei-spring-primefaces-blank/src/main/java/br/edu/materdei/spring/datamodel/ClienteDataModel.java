package br.edu.materdei.spring.datamodel;

import br.edu.materdei.spring.model.Cliente;
import br.edu.materdei.spring.service.ClienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author duduso
 */
public class ClienteDataModel extends LazyDataModel<Cliente> {

    private List<Cliente> clientes;
    private ClienteService clienteService;
    private Cliente clienteFiltro;
    private Order order;

    public ClienteDataModel(ClienteService clienteService, Cliente clienteFiltro) {
        this.clientes = new ArrayList<Cliente>();
        this.clienteService = clienteService;
        this.clienteFiltro = clienteFiltro;
        this.order = null;
    }

    @Override
    public List<Cliente> load(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, String> filters) {

        if (StringUtils.isNotBlank(sortField) && sortOrder != null) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                this.order = Order.asc(sortField);
            } else if (sortOrder.equals(SortOrder.DESCENDING)) {
                this.order = Order.desc(sortField);
            }
        }

        setRowCount(this.clienteService.contarTotalClientes(this.clienteFiltro));
        this.clientes = this.clienteService.pesquisarClientesPorDemanda(firstResult, maxResults, this.clienteFiltro, this.order);

        return clientes;

    }

    @Override
    public Object getRowKey(Cliente player) {
        return player.getId();
    }

    @Override
    public Cliente getRowData(String playerId) {
        Long id = Long.valueOf(playerId);
        for (Cliente player : this.clientes) {
            if (id.equals(player.getId())) {
                return player;
            }
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

}
