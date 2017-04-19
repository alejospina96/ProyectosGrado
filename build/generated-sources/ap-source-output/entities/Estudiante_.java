package entities;

import entities.Persona;
import entities.Programa;
import entities.Prorroga;
import entities.TrabajoGrado;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-19T09:18:53")
@StaticMetamodel(Estudiante.class)
public class Estudiante_ { 

    public static volatile SingularAttribute<Estudiante, Long> estudianteCodigo;
    public static volatile CollectionAttribute<Estudiante, Prorroga> prorrogaCollection;
    public static volatile SingularAttribute<Estudiante, Programa> estudiantePrograma;
    public static volatile SingularAttribute<Estudiante, Persona> estudiantePersona;
    public static volatile CollectionAttribute<Estudiante, TrabajoGrado> trabajoGradoCollection;

}