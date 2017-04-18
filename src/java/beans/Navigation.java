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
            mostrarEstudiantes="mostrar_estudiantes",
            agregarJurado = "agregar_jurado",
            listarJurados = "listar_jurados",
            listarPropuestas="listar_propuestas",
            listarPropuestasEstado="listar_propuestas_estados",
            listarPropuestasVencer="listar_propuestas_a_vencer",
            listarTrabajosGrado="listar_trabajos_grado",
            listarTrabajosGradoJurado="listar_trabajos_grado_jurado",
            listarTrabajosGradoVencer="listar_trabajos_grado_a_vencer";
    
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

    public String getAgregarJurado() {
        return agregarJurado;
    }

    public void setAgregarJurado(String agregarJurado) {
        this.agregarJurado = agregarJurado;
    }

    public String getListarJurados() {
        return listarJurados;
    }

    public void setListarJurados(String listarJurados) {
        this.listarJurados = listarJurados;
    }

    public String getListarPropuestas() {
        return listarPropuestas;
    }

    public void setListarPropuestas(String listarPropuestas) {
        this.listarPropuestas = listarPropuestas;
    }

    public String getListarPropuestasEstado() {
        return listarPropuestasEstado;
    }

    public void setListarPropuestasEstado(String listarPropuestasEstado) {
        this.listarPropuestasEstado = listarPropuestasEstado;
    }

    public String getListarPropuestasVencer() {
        return listarPropuestasVencer;
    }

    public void setListarPropuestasVencer(String listarPropuestasVencer) {
        this.listarPropuestasVencer = listarPropuestasVencer;
    }

    public String getListarTrabajosGrado() {
        return listarTrabajosGrado;
    }

    public void setListarTrabajosGrado(String listarTrabajosGrado) {
        this.listarTrabajosGrado = listarTrabajosGrado;
    }

    public String getListarTrabajosGradoJurado() {
        return listarTrabajosGradoJurado;
    }

    public void setListarTrabajosGradoJurado(String listarTrabajosGradoJurado) {
        this.listarTrabajosGradoJurado = listarTrabajosGradoJurado;
    }

    public String getListarTrabajosGradoVencer() {
        return listarTrabajosGradoVencer;
    }

    public void setListarTrabajosGradoVencer(String listarTrabajosGradoVencer) {
        this.listarTrabajosGradoVencer = listarTrabajosGradoVencer;
    }

}
