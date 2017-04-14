/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author daniel
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prorroga.findAll", query = "SELECT p FROM Prorroga p"),
    @NamedQuery(name = "Prorroga.findByProrrogaId", query = "SELECT p FROM Prorroga p WHERE p.prorrogaId = :prorrogaId"),
    @NamedQuery(name = "Prorroga.findByProrrogaArchivo", query = "SELECT p FROM Prorroga p WHERE p.prorrogaArchivo = :prorrogaArchivo")})
public class Prorroga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prorroga_id", nullable = false)
    private Integer prorrogaId;
    @Basic(optional = false)
    @Column(name = "prorroga_archivo", nullable = false, length = 30)
    private String prorrogaArchivo;
    @JoinColumn(name = "prorroga_plazo_entrega", referencedColumnName = "plazo_id")
    @ManyToOne
    private Plazo prorrogaPlazoEntrega;
    @JoinColumn(name = "prorroga_solicitante", referencedColumnName = "estudiante_codigo", nullable = false)
    @ManyToOne(optional = false)
    private Estudiante prorrogaSolicitante;
    @JoinColumn(name = "prorroga_trabajo", referencedColumnName = "tg_id", nullable = false)
    @ManyToOne(optional = false)
    private TrabajoGrado prorrogaTrabajo;

    public Prorroga() {
    }

    public Prorroga(Integer prorrogaId) {
        this.prorrogaId = prorrogaId;
    }

    public Prorroga(Integer prorrogaId, String prorrogaArchivo) {
        this.prorrogaId = prorrogaId;
        this.prorrogaArchivo = prorrogaArchivo;
    }

    public Integer getProrrogaId() {
        return prorrogaId;
    }

    public void setProrrogaId(Integer prorrogaId) {
        this.prorrogaId = prorrogaId;
    }

    public String getProrrogaArchivo() {
        return prorrogaArchivo;
    }

    public void setProrrogaArchivo(String prorrogaArchivo) {
        this.prorrogaArchivo = prorrogaArchivo;
    }

    public Plazo getProrrogaPlazoEntrega() {
        return prorrogaPlazoEntrega;
    }

    public void setProrrogaPlazoEntrega(Plazo prorrogaPlazoEntrega) {
        this.prorrogaPlazoEntrega = prorrogaPlazoEntrega;
    }

    public Estudiante getProrrogaSolicitante() {
        return prorrogaSolicitante;
    }

    public void setProrrogaSolicitante(Estudiante prorrogaSolicitante) {
        this.prorrogaSolicitante = prorrogaSolicitante;
    }

    public TrabajoGrado getProrrogaTrabajo() {
        return prorrogaTrabajo;
    }

    public void setProrrogaTrabajo(TrabajoGrado prorrogaTrabajo) {
        this.prorrogaTrabajo = prorrogaTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prorrogaId != null ? prorrogaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prorroga)) {
            return false;
        }
        Prorroga other = (Prorroga) object;
        if ((this.prorrogaId == null && other.prorrogaId != null) || (this.prorrogaId != null && !this.prorrogaId.equals(other.prorrogaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Prorroga[ prorrogaId=" + prorrogaId + " ]";
    }
    
}
