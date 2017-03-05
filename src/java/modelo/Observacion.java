/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author daniel
 */
public class Observacion {

    private int id;
    private String observacion;
   

    public Observacion(int id, String descripcion) {
        this.id = id;
        this.observacion = descripcion;
       
    }


    public int getId() {
        return id;
    }

    public String getObservacion() {
        return observacion;
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
        final Observacion other = (Observacion) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
