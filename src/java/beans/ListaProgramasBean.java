/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.ProgramaJpaController;
import entities.Programa;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author daniel
 */
@ManagedBean
@SessionScoped
public class ListaProgramasBean {
    
    private List<Programa> programas;
    /**
     * Creates a new instance of ListaProgramasBean
     */
    public ListaProgramasBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(Data.PERSISTANCE_UNIT_NAME);
        ProgramaJpaController programaController = new ProgramaJpaController(emf);
        programas = programaController.findProgramaEntities();
    }

    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
    }
    
}
