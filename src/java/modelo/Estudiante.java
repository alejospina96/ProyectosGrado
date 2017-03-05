/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Estudiante {

    private long codigo;

    public Estudiante(long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "codigo=" + codigo + " "+ persona ;
    }
    private Persona persona;
    private Programa programa;
    private ArrayList<Propuesta> propuestas;

    public Estudiante(long codigo, Persona persona, Programa programa) {
        this.codigo = codigo;
        this.persona = persona;
        this.programa = programa;
        propuestas = new ArrayList<Propuesta>();
    }

    public ArrayList<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(ArrayList<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }
    public boolean removePropuesta(Propuesta propuesta){
        if(hasPropuesta(propuesta)){
            propuestas.remove(propuesta);
            return true;
        }
        return false;
    }
    public boolean addPropuesta(Propuesta propuesta) {
        if(hasPropuesta(propuesta)){
            return false;
        }
        propuestas.add(propuesta);
        return true;
    }

    public boolean hasPropuesta(Propuesta propuesta) {
        for (int i = 0; i < propuestas.size(); i++) {
            Propuesta p = propuestas.get(i);
            if (p.equals(propuesta)) {
                return true;
            }
        }
        return false;
    }

    public long getCodigo() {
        return codigo;
    }

    public Persona getPersona() {
        return persona;
    }

    public Programa getPrograma() {
        return programa;
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
        final Estudiante other = (Estudiante) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }
    
}
