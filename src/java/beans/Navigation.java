/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author daniel
 */
@ManagedBean
@SessionScoped
public class Navigation {

    private String indexEstudiante= "estudiante_index", 
            indexMiembroComite="miembro_comite_index", 
            homePage="index", 
            agregarEstudiante="agregar_estudiante", 
            registrarPropuesta="registrar_propuesta", 
            entregarTrabajo="entregar_trabajo",
            mostrarEstudiantes="mostrar_estudiantes";
    public Navigation() {
    }

    public String getMostrarEstudiantes() {
        return mostrarEstudiantes;
    }

    public void setMostrarEstudiantes(String mostrarEstudiantes) {
        this.mostrarEstudiantes = mostrarEstudiantes;
    }

    public String getEntregarTrabajo() {
        return entregarTrabajo;
    }

    public void setEntregarTrabajo(String entregarTrabajo) {
        this.entregarTrabajo = entregarTrabajo;
    }

    public String getRegistrarPropuesta() {
        return registrarPropuesta;
    }

    public void setRegistrarPropuesta(String registrarPropuesta) {
        this.registrarPropuesta = registrarPropuesta;
    }

    public String getAgregarEstudiante() {
        return agregarEstudiante;
    }

    public void setAgregarEstudiante(String agregarEstudiante) {
        this.agregarEstudiante = agregarEstudiante;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }


    public String getIndexMiembroComite() {
        return indexMiembroComite;
    }

    public void setIndexMiembroComite(String indexMiembroComite) {
        this.indexMiembroComite = indexMiembroComite;
    }

    public String getIndexEstudiante() {
        return indexEstudiante;
    }

    public void setIndexEstudiante(String indexEstudiante) {
        this.indexEstudiante = indexEstudiante;
    }

}
