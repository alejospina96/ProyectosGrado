/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.TrabajoGradoJpaController;
import controlers.exceptions.NonexistentEntityException;
import entities.EstadoTrabajoGrado;
import entities.TrabajoGrado;
import java.sql.Date;
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
public class EntregarTrabajoBean {

    private int id;
    private String archivo, error, successMessage;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    /**
     * Creates a new instance of EntregarTrabajoBean
     */
    public EntregarTrabajoBean() {
    }

    public void entregar() {
        TrabajoGradoJpaController controller = new TrabajoGradoJpaController(Data.EMF);
        TrabajoGrado trabajo = controller.findTrabajoGrado(this.id);
        if (trabajo != null) {
            if (null != trabajo.getTgConceptoEstado().getEpgId()) {
                switch (trabajo.getTgConceptoEstado().getEpgId()) {
                    case 1:
                        error = "La propuesta respectiva a este trabajo de grado no ha sido aprobada";
                        break;
                    case 3:
                    case 4:
                        error = "El trabajo de grado \"" + trabajo.getTgTematica() + "\" ya ha sido aprobado con estado " + trabajo.getTgConceptoEstado().getEpgDescripcion();
                        break;
                    case 5:
                        error = "El trabajo de grado \"" + trabajo.getTgTematica() + "\" ha sido devuelto para correcciones, ingrese por el modulo \"Presentar Correción\"";
                        break;
                    case 6:
                        error = "El trabajo de grado \"" + trabajo.getTgTematica() + "\" ya fue entregado y aún no ha sido evaluado";
                        break;
                    default:
                        entregar(trabajo, controller);
                        break;
                }
            }
        } else {
            error = "No se encuentra un trabajo con ese numero";
        }
    }

    private void entregar(TrabajoGrado trabajo, TrabajoGradoJpaController controller) {
        trabajo.setTgArchivo(this.archivo);
        trabajo.setTgConceptoEstado(new EstadoTrabajoGrado(6));
        trabajo.setTgFechaEntrega(new Date(new java.util.Date().getTime()));
        try {
            controller.edit(trabajo);
            successMessage = "Trabajo entregado con éxito";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EntregarTrabajoBean.class.getName()).log(Level.SEVERE, null, ex);
            error = "NEX: " + ex.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(EntregarTrabajoBean.class.getName()).log(Level.SEVERE, null, ex);
            error = "Error actualizando el estado del trabajo de grado: " + ex.getMessage();
        }
    }
}
