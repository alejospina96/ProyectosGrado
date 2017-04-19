/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.PropuestaJpaController;
import controlers.TrabajoGradoJpaController;
import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import controlers.exceptions.PreexistingEntityException;
import entities.EstadoPropuesta;
import entities.EstadoTrabajoGrado;
import entities.Estudiante;
import entities.ModalidadTrabajo;
import entities.Propuesta;
import entities.TrabajoGrado;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author daniel
 */
@ManagedBean
@RequestScoped
public class RegistrarPropuestaBean {

    private ModalidadTrabajo modalidad = new ModalidadTrabajo();
    private String estudiantes = "";
    private List<Estudiante> estudiantesList = new ArrayList<Estudiante>();
    private TrabajoGrado trabajo = new TrabajoGrado();
    private Propuesta propuesta = new Propuesta();
    private String errorEstudiantes = "";
    private String error = "";
    private String successMessage = "";

    public void guardar() {
        trabajo.setEstudianteCollection(estudiantesList);
        trabajo.setTgConceptoEstado(new EstadoTrabajoGrado(1));
        trabajo.setTgModalidad(modalidad);
        TrabajoGradoJpaController controllerTrabajo = new TrabajoGradoJpaController(Data.EMF);
        PropuestaJpaController controller = new PropuestaJpaController(Data.EMF);
        int idTrabajo = controllerTrabajo.create(trabajo);
        propuesta.setPropuestaTrabajo(idTrabajo);
        propuesta.setTrabajoGrado(trabajo);
        propuesta.setPropuestaFecha(new Date(new java.util.Date().getTime()));
        propuesta.setPropuestaConceptoEstado(new EstadoPropuesta(1));
        try {
            controller.create(propuesta);
            successMessage = "Propuesta creada satisfactoriamente<br/>El numero del trabajo es el "+idTrabajo;
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(RegistrarPropuestaBean.class.getName()).log(Level.SEVERE, null, ex);
            error = "Error CRITICO contacte con el administrador y muestrele una captura de pantalla:<br/> " + ex.getMessage();
                   
        } catch (Exception ex) {
            Logger.getLogger(RegistrarPropuestaBean.class.getName()).log(Level.SEVERE, null, ex);
            error = "Ya existe una propuesta con ese nombre de archivo";
        }
        if(!error.isEmpty()){
            try {
                controllerTrabajo.destroy(idTrabajo);
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(RegistrarPropuestaBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RegistrarPropuestaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    public String getErrorEstudiantes() {
        return errorEstudiantes;
    }

    public void setErrorEstudiantes(String errorEstudiantes) {
        this.errorEstudiantes = errorEstudiantes;
    }

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    public TrabajoGrado getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoGrado trabajo) {
        this.trabajo = trabajo;
    }

    public String getEstudiantes() {
        return estudiantes;
    }

    private void volverListEstudiantes() {
        String[] arrayEstudiantes = estudiantes.split(",");
        for (String arrayEstudiante : arrayEstudiantes) {
            estudiantesList.add(new Estudiante(Long.parseLong(arrayEstudiante)));
        }
    }

    public void setEstudiantes(String estudiantes) {
        this.estudiantes = estudiantes;
        volverListEstudiantes();
    }

    public ModalidadTrabajo getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadTrabajo modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * Creates a new instance of RegistrarPropuestaBean
     */
    public RegistrarPropuestaBean() {
    }

}
