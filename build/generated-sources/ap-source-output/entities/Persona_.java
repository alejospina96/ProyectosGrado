package entities;

import entities.Estudiante;
import entities.TrabajoGrado;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-19T09:18:53")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, String> personaSApellido;
    public static volatile CollectionAttribute<Persona, Estudiante> estudianteCollection;
    public static volatile SingularAttribute<Persona, String> personaSNombre;
    public static volatile SingularAttribute<Persona, String> personaPNombre;
    public static volatile SingularAttribute<Persona, Long> personaIdentificacion;
    public static volatile SingularAttribute<Persona, String> personaPApellido;
    public static volatile SingularAttribute<Persona, String> personaEmail;
    public static volatile SingularAttribute<Persona, Boolean> personaEsJurado;
    public static volatile CollectionAttribute<Persona, TrabajoGrado> trabajoGradoCollection;

}