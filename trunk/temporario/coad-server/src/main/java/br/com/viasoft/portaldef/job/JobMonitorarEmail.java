package br.com.viasoft.portaldef.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.service.MailLoadService;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;


@Service
public class JobMonitorarEmail {

	@Autowired
	private MailLoadService mailLoadService;

	@Autowired
	private ProcessarArquivosService processarArquivosService;



	public void monitorarEmail(){
		mailLoadService.loadMail();
	}

}