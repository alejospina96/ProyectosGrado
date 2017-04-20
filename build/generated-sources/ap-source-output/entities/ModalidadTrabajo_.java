package entities;

import entities.TrabajoGrado;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T10:32:15")
@StaticMetamodel(ModalidadTrabajo.class)
public class ModalidadTrabajo_ { 

    public static volatile SingularAttribute<ModalidadTrabajo, Integer> modalidadId;
    public static volatile SingularAttribute<ModalidadTrabajo, String> modalidadDescripcion;
    public static volatile CollectionAttribute<ModalidadTrabajo, TrabajoGrado> trabajoGradoCollection;

}