package br.com.viasoft.portaldef.service;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public interface CaptchaService extends Serializable {

	BufferedImage createCaptcha();
	
	boolean isCorrect(String answer);
	
}