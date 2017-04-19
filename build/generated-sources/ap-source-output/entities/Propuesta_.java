package entities;

import entities.EstadoPropuesta;
import entities.Observacion;
import entities.Plazo;
import entities.TrabajoGrado;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-19T09:18:53")
@StaticMetamodel(Propuesta.class)
public class Propuesta_ { 

    public static volatile SingularAttribute<Propuesta, Date> propuestaFecha;
    public static volatile SingularAttribute<Propuesta, TrabajoGrado> trabajoGrado;
    public static volatile SingularAttribute<Propuesta, EstadoPropuesta> propuestaConceptoEstado;
    public static volatile SingularAttribute<Propuesta, Integer> propuestaTrabajo;
    public static volatile CollectionAttribute<Propuesta, Observacion> observacionCollection;
    public static volatile SingularAttribute<Propuesta, Plazo> propuestaPlazoCorrecciones;
    public static volatile SingularAttribute<Propuesta, Date> propuestaFechaEntrega;
    public static volatile SingularAttribute<Propuesta, String> propuestaArchivo;

}