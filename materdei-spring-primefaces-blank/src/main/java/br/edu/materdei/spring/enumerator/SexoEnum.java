package br.edu.materdei.spring.enumerator;

/**
 *
 * @author duduso
 */
public enum SexoEnum {

    Masculino("m"),
    Feminino("f");

    private String value;

    private SexoEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
