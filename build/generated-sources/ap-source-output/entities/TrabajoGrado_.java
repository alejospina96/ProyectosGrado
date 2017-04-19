package entities;

import entities.EstadoTrabajoGrado;
import entities.Estudiante;
import entities.ModalidadTrabajo;
import entities.Observacion;
import entities.Persona;
import entities.Plazo;
import entities.Propuesta;
import entities.Prorroga;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-19T09:18:53")
@StaticMetamodel(TrabajoGrado.class)
public class TrabajoGrado_ { 

    public static volatile CollectionAttribute<TrabajoGrado, Estudiante> estudianteCollection;
    public static volatile CollectionAttribute<TrabajoGrado, Persona> personaCollection;
    public static volatile CollectionAttribute<TrabajoGrado, Observacion> observacionCollection;
    public static volatile SingularAttribute<TrabajoGrado, Propuesta> propuesta;
    public static volatile SingularAttribute<TrabajoGrado, String> tgArchivo;
    public static volatile SingularAttribute<TrabajoGrado, ModalidadTrabajo> tgModalidad;
    public static volatile SingularAttribute<TrabajoGrado, Date> fechaDefensa;
    public static volatile SingularAttribute<TrabajoGrado, Date> tgFechaEntrega;
    public static volatile SingularAttribute<TrabajoGrado, EstadoTrabajoGrado> tgConceptoEstado;
    public static volatile CollectionAttribute<TrabajoGrado, Prorroga> prorrogaCollection;
    public static volatile SingularAttribute<TrabajoGrado, Plazo> tgPlazoEntrega;
    public static volatile SingularAttribute<TrabajoGrado, Integer> tgId;
    public static volatile SingularAttribute<TrabajoGrado, String> tgTematica;

}