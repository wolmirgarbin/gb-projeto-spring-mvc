package br.com.viasoft.portaldef.web.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigAcessoUsuario {

	// colocar a role da op��o do menu
	String role() default "";
	
}