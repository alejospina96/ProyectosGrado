/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.PersonaJpaController;
import entities.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.eclipse.persistence.config.TargetDatabase;

/**
 *
 * @author cristian
 */
@ManagedBean
@RequestScoped
public class ListarJuradoBean {
private List<Persona> jurados = new PersonaJpaController(Data.EMF).findJuradosEntities();
    /**
     * Creates a new instance of ListarJuradoBean
     */
    public ListarJuradoBean() {
    }

    public List<Persona> getJurados() {
        return jurados;
    }

    public void setJurados(List<Persona> jurados) {
        this.jurados = jurados;
    }
    
    
}
