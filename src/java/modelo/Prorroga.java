/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;

/**
 *
 * @author daniel
 */
public class Prorroga {
    private int id;
    private TrabajoGrado trabajo;
    private Estudiante solicitante;
    private File archivo;
    private Plazo plazoEntrega;

    public Prorroga(int id, TrabajoGrado trabajo, Estudiante solicitante, File archivo) {
        this.id = id;
        this.trabajo = trabajo;
        this.solicitante = solicitante;
        this.archivo = archivo;
        this.plazoEntrega = null;
    }

    public int getId() {
        return id;
    }

    public TrabajoGrado getTrabajo() {
        return trabajo;
    }

    public Estudiante getSolicitante() {
        return solicitante;
    }

    public File getArchivo() {
        return archivo;
    }

    public Plazo getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setPlazoEntrega(Plazo plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }
    
    
    
}
