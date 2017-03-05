/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;

/**
 *
 * @author daniel
 */
public class Plazo {
    private int id;
    private Date fechaInicio, fechaFin;
    private ConceptoPlazo concepto;
    private Multa multa;
    private boolean vencido;

    public Plazo(int id, Date fechaFin, ConceptoPlazo concepto, Multa multa) {
        this.id = id;
        this.fechaInicio = new Date(new java.util.Date().getTime());
        this.fechaFin = fechaFin;
        this.concepto = concepto;
        this.multa = multa;
        vencido = false;
    }

    public int getId() {
        return id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public ConceptoPlazo getConcepto() {
        return concepto;
    }

    public Multa getMulta() {
        return multa;
    }

    public boolean isVencido() {
        return vencido;
    }

    public void setVencido(boolean vencido) {
        this.vencido = vencido;
    }
     
    
    
}
