package entities;

import entities.ConceptoPlazo;
import entities.Multa;
import entities.Propuesta;
import entities.Prorroga;
import entities.TrabajoGrado;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T10:32:15")
@StaticMetamodel(Plazo.class)
public class Plazo_ { 

    public static volatile SingularAttribute<Plazo, Integer> plazoId;
    public static volatile CollectionAttribute<Plazo, Prorroga> prorrogaCollection;
    public static volatile SingularAttribute<Plazo, ConceptoPlazo> plazoConcepto;
    public static volatile CollectionAttribute<Plazo, Propuesta> propuestaCollection;
    public static volatile SingularAttribute<Plazo, Date> plazoFechaInicio;
    public static volatile SingularAttribute<Plazo, Date> plazoFechaFin;
    public static volatile CollectionAttribute<Plazo, TrabajoGrado> trabajoGradoCollection;
    public static volatile SingularAttribute<Plazo, Multa> plazoMulta;

}