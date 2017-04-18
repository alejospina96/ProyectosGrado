/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.PersonaJpaController;
import entities.Persona;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cristian
 */
@ManagedBean
@RequestScoped
public class CrearJuradoBean {

    private Persona persona = new Persona();
    private String error = "";
    private String successMessage = "";

    /**
     * Creates a new instance of CrearJuradoBean
     */
    public CrearJuradoBean() {
    }

    public void guardar() {
        PersonaJpaController pController = new PersonaJpaController(Data.EMF);
        try {
            persona.setPersonaEsJurado(true);
            pController.create(persona);
            successMessage="Se ha agregado satisfactoriamente el Jurado";
        } catch (Exception e) {
            error = "Error al agregar jurado: "+e.getMessage();
        }
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }


}