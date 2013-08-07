package br.edu.materdei.spring.controller;

import br.edu.materdei.spring.datamodel.ClienteDataModel;
import br.edu.materdei.spring.enumerator.PaginaEnum;
import br.edu.materdei.spring.model.Cliente;
import br.edu.materdei.spring.service.ClienteService;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author duduso
 */
@Controller("consultarCliente")
@ApplicationScoped
public class ConsultarCliente implements Serializable{

    private ClienteDataModel dataModel;
    private Cliente clienteFiltro;

    @Autowired
    private ClienteService clienteService;

    public String setup(){
        limparFiltros();
        return PaginaEnum.CONSULTAR_CLIENTE.getValue();
    }

    public String removerCliente(Cliente cliente){
        clienteService.excluirCliente(cliente);
        pesquisar();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Excluído Com Sucesso!", ""));
        return null;
    }

    public String limparFiltros(){
        this.dataModel = null;
        this.clienteFiltro = new Cliente();
        return null;
    }

    public String pesquisar(){
        this.dataModel = new ClienteDataModel(this.clienteService, this.clienteFiltro);
        return null;
    }

    public ClienteDataModel getDataModel() {
        return this.dataModel;
    }

    public void setDataModel(ClienteDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Cliente getClienteFiltro() {
        return clienteFiltro;
    }

    public void setClienteFiltro(Cliente clienteFiltro) {
        this.clienteFiltro = clienteFiltro;
    }

}
