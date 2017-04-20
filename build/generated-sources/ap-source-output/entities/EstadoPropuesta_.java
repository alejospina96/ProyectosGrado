package entities;

import entities.Propuesta;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T10:32:15")
@StaticMetamodel(EstadoPropuesta.class)
public class EstadoPropuesta_ { 

    public static volatile SingularAttribute<EstadoPropuesta, String> epDescripcion;
    public static volatile CollectionAttribute<EstadoPropuesta, Propuesta> propuestaCollection;
    public static volatile SingularAttribute<EstadoPropuesta, Integer> epId;

}