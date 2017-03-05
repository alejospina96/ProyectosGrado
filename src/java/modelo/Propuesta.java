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
public class Propuesta {
    private TrabajoGrado trabajo;
    private Date fecha;
    private File archivo;
    private EstadoPropuesta estado;
    private ArrayList<Estudiante> estudiantes;
    private Plazo plazoCorrecciones;

    public Propuesta(TrabajoGrado trabajo, File archivo) {
        this.trabajo = trabajo;
        this.archivo = archivo;
        this.fecha = new Date(new java.util.Date().getTime());
        this.estado = null;
        this.estudiantes=new ArrayList<Estudiante>();
        this.plazoCorrecciones = null;
    }

    public void setPlazoCorrecciones(Plazo plazoCorrecciones) {
        this.plazoCorrecciones = plazoCorrecciones;
    }

    public Plazo getPlazoCorrecciones() {
        return plazoCorrecciones;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    public boolean hasEstudiante(Estudiante e){
        for (Estudiante estudiante : estudiantes) {
            if(estudiante.equals(e))return true;
        }
        return false;
    }
    
    public boolean addEstudiante(Estudiante e){
        if(!hasEstudiante(e)){
            estudiantes.add(e);
        }
        return false;
    }
    
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }

    public TrabajoGrado getTrabajo() {
        return trabajo;
    }

    public Date getFecha() {
        return fecha;
    }

    public File getArchivo() {
        return archivo;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Propuesta{" + "trabajo=" + trabajo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Propuesta other = (Propuesta) obj;
        if (this.trabajo != other.trabajo && (this.trabajo == null || !this.trabajo.equals(other.trabajo))) {
            return false;
        }
        return true;
    }
    
    
}
