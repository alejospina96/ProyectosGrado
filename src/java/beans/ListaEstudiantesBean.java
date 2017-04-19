/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.EstudianteJpaController;
import entities.Estudiante;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author daniel
 */
@ManagedBean
@SessionScoped
public class ListaEstudiantesBean {

    /**
     * Creates a new instance of ListaEstudiantesBean
     */
    public ListaEstudiantesBean() {
    }
    private List<Estudiante> estudiantes = new EstudianteJpaController(Data.EMF).findEstudianteEntities();;
   
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
}
