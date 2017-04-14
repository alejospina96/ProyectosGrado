/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.EstudianteJpaController;
import controlers.PersonaJpaController;
import entities.Estudiante;
import entities.Persona;
import entities.Programa;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author daniel
 */
@ManagedBean
@RequestScoped
public class CrearEstudianteBean {
    private Estudiante estudiante = new Estudiante();
    private Persona persona = new Persona();
    private Programa programa = new Programa();
    private String error="";
    private String successMessage ="";
    /**
     * Creates a new instance of CrearEstudianteBean
     */
    public CrearEstudianteBean() {        
    }
    public void guardar(){
        estudiante.setEstudiantePersona(persona);
        estudiante.setEstudiantePrograma(programa);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(Data.PERSISTANCE_UNIT_NAME);
        EstudianteJpaController estudianteController = new EstudianteJpaController(emf);
        PersonaJpaController personaController = new PersonaJpaController(emf);
        try {
            personaController.create(persona);
            try{
                estudianteController.create(estudiante);
                successMessage="Estudiante Creado con exito";
            }catch(Exception ex){
                personaController.destroy(persona.getPersonaIdentificacion());
                error = ex.getMessage();
            }                      
        } catch (Exception ex) {
            Logger.getLogger(CrearEstudianteBean.class.getName()).log(Level.SEVERE, null, ex);
            error = ex.getMessage();
        }
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
}
