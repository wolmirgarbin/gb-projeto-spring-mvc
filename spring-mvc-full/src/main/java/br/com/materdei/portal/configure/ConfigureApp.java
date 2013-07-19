package br.com.viasoft.portaldef.configure;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

@Getter
public class ConfigureApp {
	
	private static ConfigureApp instance;
	
	
	private String applicationPath;
	private final String diretorioArquivos;
	private final String emailPadrao;
	private final String instanciaApp;
	private final Integer tempoMaximoMensagem;
	
	
	private ConfigureApp() { 
		this.applicationPath = System.getProperty("portal.dfe.application.path");
		this.diretorioArquivos =  System.getProperty("portal.dfe.diretorio.arquivos");
		this.emailPadrao = System.getProperty("portal.dfe.send.mail.emailpadrao");
		this.instanciaApp = System.getProperty("portal.dfe.instancia");
		this.tempoMaximoMensagem =  Integer.valueOf( System.getProperty("portal.dfe.tempo.max.mensagem") );
	}
	
	
	public static ConfigureApp getInstance(){
		if( instance == null ) {
			instance = new ConfigureApp();
		}
		return instance; 
	}
	
	public static void updatePath(HttpServletRequest request) {
		instance.applicationPath = "http://"+ request.getServerName() +":"+ request.getServerPort() +""+ request.getContextPath() +"/";
	}
}