package br.com.viasoft.portaldef.configure;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;

@Getter @Setter
public class ApplicationProperties implements Serializable {

	private static final long serialVersionUID = 3164723817631764659L;
	
	
	@Value("#{portalDfeProperties['url.agro']}")
	private String urlAgro;
	
}