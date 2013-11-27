package br.com.garbo.sites.interceptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public abstract class BaseInterceptor implements HandlerInterceptor, Serializable {

	private static final long serialVersionUID = -4271900024614402062L;

	public static <A extends Annotation> A findAnnotation(Object handler, Class<A> annotationType){
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			//return AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotationType);
			return handlerMethod.getMethodAnnotation(annotationType);
		}
		return null;
	}
	
}