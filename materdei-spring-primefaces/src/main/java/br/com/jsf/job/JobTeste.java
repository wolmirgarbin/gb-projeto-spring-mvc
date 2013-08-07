package br.com.jsf.job;

import br.com.jsf.model.Cliente;
import br.com.jsf.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author duduso
 */
@Component("jobTeste")
@Scope
public class JobTeste {
    
    @Autowired
    private ClienteService clienteService;
    
    @Scheduled(fixedDelay = 5000)
    public void execute(){
        System.out.println("Quantidade Total de Clientes = " + this.clienteService.contarTotalClientes(new Cliente()));
    }
    
}