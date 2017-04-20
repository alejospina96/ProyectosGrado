package entities;

import entities.Propuesta;
import entities.TrabajoGrado;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T10:32:15")
@StaticMetamodel(Observacion.class)
public class Observacion_ { 

    public static volatile SingularAttribute<Observacion, Integer> opgId;
    public static volatile SingularAttribute<Observacion, String> opgObservacion;
    public static volatile SingularAttribute<Observacion, Propuesta> opgPropuesta;
    public static volatile SingularAttribute<Observacion, TrabajoGrado> opgTrabajo;

}