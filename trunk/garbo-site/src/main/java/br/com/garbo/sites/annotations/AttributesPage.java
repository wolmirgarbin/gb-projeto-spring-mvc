package br.com.garbo.sites.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributesPage {

	String menuPage() default "";
	String title() default "";
	boolean addEmpresa() default false;
	boolean addUsuario() default true;
	boolean isCapa() default false;
	boolean usaRoleAdm() default false;
	
	String groupResources() default "base";
	
}
