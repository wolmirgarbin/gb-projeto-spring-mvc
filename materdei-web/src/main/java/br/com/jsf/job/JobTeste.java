package br.com.jsf.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.jsf.service.ClienteService;

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
        //System.out.println("Quantidade Total de Clientes = " + this.clienteService.contarTotalClientes(new Cliente()));
    }
    
}