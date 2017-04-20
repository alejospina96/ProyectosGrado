package entities;

import entities.Estudiante;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T10:32:15")
@StaticMetamodel(Programa.class)
public class Programa_ { 

    public static volatile CollectionAttribute<Programa, Estudiante> estudianteCollection;
    public static volatile SingularAttribute<Programa, String> programaAbreviatura;
    public static volatile SingularAttribute<Programa, String> programaNombre;
    public static volatile SingularAttribute<Programa, String> programaCodigo;

}