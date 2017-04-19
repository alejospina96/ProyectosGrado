/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.ModalidadTrabajoJpaController;
import entities.ModalidadTrabajo;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author daniel
 */
@ManagedBean
@SessionScoped
public class ListaModalidadesBean {
    
    private List<ModalidadTrabajo> modalidades = new ModalidadTrabajoJpaController(Data.EMF).findModalidadTrabajoEntities();
            ;
    /**
     * Creates a new instance of ListaModalidadesBean
     */
    public ListaModalidadesBean() {
    }

    public List<ModalidadTrabajo> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ModalidadTrabajo> modalidades) {
        this.modalidades = modalidades;
    }
    
}
