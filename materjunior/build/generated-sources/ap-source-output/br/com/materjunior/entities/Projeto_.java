package br.com.materjunior.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-06-16T14:28:09")
@StaticMetamodel(Projeto.class)
public class Projeto_ { 

    public static volatile SingularAttribute<Projeto, Integer> idcliente;
    public static volatile SingularAttribute<Projeto, Integer> horasprojeto;
    public static volatile SingularAttribute<Projeto, String> titulo;
    public static volatile SingularAttribute<Projeto, String> dtfinal;
    public static volatile SingularAttribute<Projeto, Integer> idprojeto;
    public static volatile SingularAttribute<Projeto, Date> dtinicio;
    public static volatile SingularAttribute<Projeto, String> descricao;

}