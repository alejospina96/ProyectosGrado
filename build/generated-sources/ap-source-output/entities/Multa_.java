package entities;

import entities.Plazo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T10:32:15")
@StaticMetamodel(Multa.class)
public class Multa_ { 

    public static volatile SingularAttribute<Multa, Integer> idMulta;
    public static volatile CollectionAttribute<Multa, Plazo> plazoCollection;
    public static volatile SingularAttribute<Multa, Double> valorMulta;

}