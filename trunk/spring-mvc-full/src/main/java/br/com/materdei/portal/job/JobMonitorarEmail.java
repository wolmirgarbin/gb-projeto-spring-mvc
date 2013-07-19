package br.com.viasoft.portaldef.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.service.MailLoadService;


@Service
public class JobMonitorarEmail {

	@Autowired
	private MailLoadService mailLoadService;
	
	
	public void monitorarEmail(){
		// mailLoadService.loadMail();
	}
	
}