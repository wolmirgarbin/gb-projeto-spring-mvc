package br.edu.materdei.spring.constant;

/**
 * Escopos Aceitos Pelo Spring
 * @author duduso
 */
public interface SpringScope {

    public static final String GLOBAL_SESSION = "globalSession";
    public static final String PROTOTYPE = "prototype";
    public static final String REQUEST = "request";
    public static final String SESSION = "session";
    public static final String SINGLETON = "singleton";
    public static final String VIEW = "view";
    
}
