/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.EstudianteJpaController;
import controlers.PersonaJpaController;
import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import entities.Estudiante;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author daniel
 */
@ManagedBean
@RequestScoped
public class EliminarEstudianteBean {

    /**
     * Creates a new instance of EliminarEstudianteBean
     */
    public EliminarEstudianteBean() {
    }
    private Estudiante estudiante = new Estudiante();
    private String error = "", success = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public String eliminar() {
        String value = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("estudiante_eliminar");
        System.out.println("El valo es =" + value);
        PersonaJpaController pController = new PersonaJpaController(Data.EMF);
        EstudianteJpaController controller = new EstudianteJpaController(Data.EMF);
        try {
            Long cod = Long.parseLong(value);
            Long id = controller.findEstudiante(cod).getEstudiantePersona().getPersonaIdentificacion();
            controller.destroy(cod);
            pController.destroy(id);
            success = "Estudiante eliminado con exito";
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(EliminarEstudianteBean.class.getName()).log(Level.SEVERE, null, ex);
            error = "Error al eliminar a la persona";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EliminarEstudianteBean.class.getName()).log(Level.SEVERE, null, ex);
            error = "El estudiante no existe";
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(EliminarEstudianteBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: "+ex.getMessage());
        }
        return new Navigation().getMostrarEstudiantes();
    }
}
