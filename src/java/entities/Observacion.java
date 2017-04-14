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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Observacion.findAll", query = "SELECT o FROM Observacion o"),
    @NamedQuery(name = "Observacion.findByOpgId", query = "SELECT o FROM Observacion o WHERE o.opgId = :opgId")})
public class Observacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "opg_id", nullable = false)
    private Integer opgId;
    @Basic(optional = false)
    @Lob
    @Column(name = "opg_observacion", nullable = false, length = 65535)
    private String opgObservacion;
    @JoinColumn(name = "opg_propuesta", referencedColumnName = "propuesta_trabajo")
    @ManyToOne
    private Propuesta opgPropuesta;
    @JoinColumn(name = "opg_trabajo", referencedColumnName = "tg_id")
    @ManyToOne
    private TrabajoGrado opgTrabajo;

    public Observacion() {
    }

    public Observacion(Integer opgId) {
        this.opgId = opgId;
    }

    public Observacion(Integer opgId, String opgObservacion) {
        this.opgId = opgId;
        this.opgObservacion = opgObservacion;
    }

    public Integer getOpgId() {
        return opgId;
    }

    public void setOpgId(Integer opgId) {
        this.opgId = opgId;
    }

    public String getOpgObservacion() {
        return opgObservacion;
    }

    public void setOpgObservacion(String opgObservacion) {
        this.opgObservacion = opgObservacion;
    }

    public Propuesta getOpgPropuesta() {
        return opgPropuesta;
    }

    public void setOpgPropuesta(Propuesta opgPropuesta) {
        this.opgPropuesta = opgPropuesta;
    }

    public TrabajoGrado getOpgTrabajo() {
        return opgTrabajo;
    }

    public void setOpgTrabajo(TrabajoGrado opgTrabajo) {
        this.opgTrabajo = opgTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (opgId != null ? opgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observacion)) {
            return false;
        }
        Observacion other = (Observacion) object;
        if ((this.opgId == null && other.opgId != null) || (this.opgId != null && !this.opgId.equals(other.opgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Observacion[ opgId=" + opgId + " ]";
    }
    
}
