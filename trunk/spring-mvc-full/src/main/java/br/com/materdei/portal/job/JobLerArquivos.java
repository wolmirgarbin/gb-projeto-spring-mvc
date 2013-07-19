package br.com.viasoft.portaldef.job;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;


@Service
public class JobLerArquivos {

	@Autowired
	private ProcessarArquivosService processarArquivosService;
	
	public void vascularDiretorio(){
		try {
			processarArquivosService.processArquivos( processarArquivosService.carregaListaArquivos(ConfigureApp.getInstance().getDiretorioArquivos()) );
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}