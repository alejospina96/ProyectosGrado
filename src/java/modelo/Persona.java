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
public class Persona {
    private long identificacion;
    private String pNombre,sNombre,pApellido,sApellido,email;
    private boolean jurado;

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        return pNombre + " " + pApellido + " " + sApellido ;
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
        final Persona other = (Persona) obj;
        if (this.identificacion != other.identificacion) {
            return false;
        }
        return true;
    }

    public Persona(long identificacion, String pNombre, String sNombre, String pApellido, String sApellido, String email) {
        this.identificacion = identificacion;
        this.pNombre = pNombre;
        this.sNombre = sNombre;
        this.pApellido = pApellido;
        this.sApellido = sApellido;
        this.email = email;
        this.jurado= false;
    }

    public boolean isJurado() {
        return jurado;
    }

    public void setJurado() {
        this.jurado = true;
    }
    
    public Persona(long identificacion, String pNombre, String pApellido, String sApellido, String email) {
        this.identificacion = identificacion;
        this.pNombre = pNombre;
        this.sApellido = null;
        this.pApellido = pApellido;
        this.sApellido = sApellido;
        this.email = email;
        this.jurado = false;
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public String getpNombre() {
        return pNombre;
    }

    public String getsNombre() {
        return sNombre;
    }

    public String getpApellido() {
        return pApellido;
    }

    public String getsApellido() {
        return sApellido;
    }

    public String getEmail() {
        return email;
    }
    
}
