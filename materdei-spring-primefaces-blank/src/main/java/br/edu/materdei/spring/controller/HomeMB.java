package br.edu.materdei.spring.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.lindbergframework.spring.scope.AccessScoped;
import org.springframework.stereotype.Controller;


@Controller("homeMB")
@AccessScoped
public class HomeMB implements Serializable{
    
    private static final long serialVersionUID = 1L;

    
    public String nome;
    
    
    @PostConstruct
    public void onCreate(){
    	nome = "Minha primeira app jsf";
    }


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
