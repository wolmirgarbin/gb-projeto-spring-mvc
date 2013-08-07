package br.com.jsf.controller;

import br.com.jsf.enumerator.PaginaEnum;
import br.com.jsf.model.Cliente;
import br.com.jsf.model.Telefone;
import br.com.jsf.service.ClienteService;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author duduso
 */
@Controller("manterCliente")
@AccessScoped
public class ManterCliente implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private String ddd;
    private String numero;
    private boolean isDetalhe;
    
    @Autowired
    private ClienteService clienteService;
    
    public String createSetup() {
        this.cliente = new Cliente();
        this.isDetalhe = false;
        limparCamposTelefone();        
        return PaginaEnum.MANTER_CLIENTE.getValue();
    }

    public String editSetup(Long idCliente) {
        String pagina = createSetup();
        this.cliente = this.clienteService.obterPorId(idCliente);
        return pagina;
    }

    public String voltar() {
        return PaginaEnum.CONSULTAR_CLIENTE.getValue();
    }

    public void salvar() {
        if (this.cliente.getId() == null) {
            this.cliente.setId(this.clienteService.salvar(this.cliente));
        } else {
            this.clienteService.atualizar(this.cliente);
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Operação Realizada Com Sucesso!", ""));
    }

    public String detalhar(Long idCliente){
        String pagina = editSetup(idCliente);
        this.isDetalhe = true;
        return pagina;
    }

    public void adicionarTelefone() {
        Telefone telefone = new Telefone();
        telefone.setDdd(this.ddd);
        telefone.setNumero(this.numero);
        telefone.setCliente(this.cliente);
        this.cliente.getTelefones().add(telefone);
        limparCamposTelefone();
    }

    public void removerTelefone(Telefone telefone) {
        this.cliente.getTelefones().remove(telefone);
    }

    private void limparCamposTelefone() {
        this.ddd = null;
        this.numero = null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isIsDetalhe() {
        return isDetalhe;
    }

    public void setIsDetalhe(boolean isDetalhe) {
        this.isDetalhe = isDetalhe;
    }

}
