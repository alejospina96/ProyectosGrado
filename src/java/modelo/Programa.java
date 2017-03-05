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
public class Programa {
    private String codigo, abreviatura, nombre;

    public Programa(String codigo) {
        this.codigo = codigo;
        this.abreviatura = null;
        this.nombre = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public String getNombre() {
        return nombre;
    }

    public Programa(String codigo, String abreviatura, String nombre) {
        this.codigo = codigo;
        this.abreviatura = abreviatura;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return codigo + " " + abreviatura ;
    }
    
}
