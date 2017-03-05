/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class TrabajoGrado {
    private int id;
    private Modalidad modalidad;
    private String tematica;
    private EstadoTrabajoGrado estado;
    private File archivo;
    private Date fechaDefensa;
    private Plazo plazoCorrecciones;
    private ArrayList<Persona> jurados;
    private ArrayList<Observacion> observaciones;

    public TrabajoGrado(int id, Modalidad modalidad, String tematica) {
        this.id = id;
        this.modalidad = modalidad;
        this.tematica = tematica;
        this.archivo = null;
        this.estado = null;
        fechaDefensa = null;
        this.jurados = new ArrayList<Persona>();
        this.observaciones = new ArrayList<Observacion>();
        this.plazoCorrecciones = null;
    }    

    public Plazo getPlazoCorrecciones() {
        return plazoCorrecciones;
    }

    public void setPlazoCorrecciones(Plazo plazoCorrecciones) {
        this.plazoCorrecciones = plazoCorrecciones;
    }

    public ArrayList<Observacion> getObservaciones() {
        return observaciones;
    }
    public boolean hasObservacion(Observacion o){
        for (Observacion observacion : observaciones) {
            if(o.equals(observacion)){
                return true;
            }
        }
        return false;
    }
    public boolean removeObservacion(Observacion o){
        if(hasObservacion(o)){
            observaciones.remove(o);
            return true;
        }
        return false;
    }
    public boolean addObservacion(Observacion o){
        if(!hasObservacion(o)){
            observaciones.add(o);
            return true;
        }
        return false;
    }
    public boolean removeJurado(Persona p){
        if(hasJurado(p)){
            jurados.remove(p);
            return true;
        }
        return false;
    }
    public boolean addJurado(Persona p){
        if(!hasJurado(p)){
            jurados.add(p);
            return true;
        }
        return false;
    }
    public boolean hasJurado(Persona p){
        for (Persona jurado : jurados) {
            if(p.equals(jurado))return true;
        }
        return false;
    }
    public void setJurados(ArrayList<Persona> jurados) {
        this.jurados = jurados;
    }

    public ArrayList<Persona> getJurados() {
        return jurados;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrabajoGrado other = (TrabajoGrado) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public String getTematica() {
        return tematica;
    }

    public EstadoTrabajoGrado getEstado() {
        return estado;
    }

    public File getArchivo() {
        return archivo;
    }

    public Date getFechaDefensa() {
        return fechaDefensa;
    }

    public void setEstado(EstadoTrabajoGrado estado) {
        this.estado = estado;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "TrabajoGrado{" + "id=" + id + '}';
    }

    public void setFechaDefensa(Date fechaDefensa) {
        this.fechaDefensa = fechaDefensa;
    }
    
}
