package br.com.jsf.enumerator;

/**
 * @author duduso
 */
public enum PaginaEnum {
    
    CONSULTAR_CLIENTE("/restrito/cliente/consultar_cliente"),
    MANTER_CLIENTE("/restrito/cliente/manter_cliente");

    private String value;

    private PaginaEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
