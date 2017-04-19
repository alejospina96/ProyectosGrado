package entities;

import entities.Estudiante;
import entities.Plazo;
import entities.TrabajoGrado;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-19T09:18:53")
@StaticMetamodel(Prorroga.class)
public class Prorroga_ { 

    public static volatile SingularAttribute<Prorroga, Plazo> prorrogaPlazoEntrega;
    public static volatile SingularAttribute<Prorroga, Estudiante> prorrogaSolicitante;
    public static volatile SingularAttribute<Prorroga, String> prorrogaArchivo;
    public static volatile SingularAttribute<Prorroga, TrabajoGrado> prorrogaTrabajo;
    public static volatile SingularAttribute<Prorroga, Integer> prorrogaId;

}