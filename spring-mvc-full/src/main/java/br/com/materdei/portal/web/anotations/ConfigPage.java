package br.com.viasoft.portaldef.web.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPage {

	String menuPage() default "";
	String title() default "Portal DFE";
	boolean addEmpresa() default false;
	boolean addUsuario() default true;
	boolean isCapa() default false;
	boolean usaRoleAdm() default false;
	
}
