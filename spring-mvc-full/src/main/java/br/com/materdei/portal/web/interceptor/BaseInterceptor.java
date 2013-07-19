package br.com.viasoft.portaldef.web.interceptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public abstract class BaseInterceptor implements HandlerInterceptor, Serializable {

	public static <A extends Annotation> A findAnnotation(Object handler, Class<A> annotationType){
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			//return AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotationType);
			return handlerMethod.getMethodAnnotation(annotationType);
		}
		return null;
	}
	
}