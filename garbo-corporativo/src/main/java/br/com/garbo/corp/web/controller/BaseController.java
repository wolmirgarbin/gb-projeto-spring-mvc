package br.com.garbo.corp.web.controller;

import org.springframework.ui.Model;

public class BaseController {

	public static final String URL_BASE = "/";
	public static final String URL_PLANOS = "/planos";
	public static final String URL_LOGOUT = "/logout";
	public static final String URL_ERRO = "/error/pagina-nao-encontrada";
	
	public static final String URL_MINHA_AREA = "/minha-area";
	
	public static final String URL_USUARIO = "/usuario";
	public static final String URL_USUARIO_NOME = "/usuario/{usuario}";
	
	
	
	public void messageSucess(String message, Model model) {
		model.addAttribute("messageSucess", true);
		messageAlert(message, model);
	}
	
	public void messageAlert(String message, Model model) {
		model.addAttribute("message", message);
	}
}