/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author daniel
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"propuesta_archivo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propuesta.findAll", query = "SELECT p FROM Propuesta p"),
    @NamedQuery(name = "Propuesta.findByPropuestaTrabajo", query = "SELECT p FROM Propuesta p WHERE p.propuestaTrabajo = :propuestaTrabajo"),
    @NamedQuery(name = "Propuesta.findByPropuestaFecha", query = "SELECT p FROM Propuesta p WHERE p.propuestaFecha = :propuestaFecha"),
    @NamedQuery(name = "Propuesta.findByPropuestaFechaEntrega", query = "SELECT p FROM Propuesta p WHERE p.propuestaFechaEntrega = :propuestaFechaEntrega"),
    @NamedQuery(name = "Propuesta.findByPropuestaArchivo", query = "SELECT p FROM Propuesta p WHERE p.propuestaArchivo = :propuestaArchivo")})
public class Propuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "propuesta_trabajo", nullable = false)
    private Integer propuestaTrabajo;
    @Basic(optional = false)
    @Column(name = "propuesta_fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date propuestaFecha;
    @Column(name = "propuesta_fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date propuestaFechaEntrega;
    @Column(name = "propuesta_archivo", length = 50)
    private String propuestaArchivo;
    @JoinColumn(name = "propuesta_plazo_correcciones", referencedColumnName = "plazo_id")
    @ManyToOne
    private Plazo propuestaPlazoCorrecciones;
    @JoinColumn(name = "propuesta_concepto_estado", referencedColumnName = "ep_id", nullable = false)
    @ManyToOne(optional = false)
    private EstadoPropuesta propuestaConceptoEstado;
    @JoinColumn(name = "propuesta_trabajo", referencedColumnName = "tg_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TrabajoGrado trabajoGrado;
    @OneToMany(mappedBy = "opgPropuesta")
    private Collection<Observacion> observacionCollection;

    public Propuesta() {
    }

    public Propuesta(Integer propuestaTrabajo) {
        this.propuestaTrabajo = propuestaTrabajo;
    }

    public Propuesta(Integer propuestaTrabajo, Date propuestaFecha) {
        this.propuestaTrabajo = propuestaTrabajo;
        this.propuestaFecha = propuestaFecha;
    }

    public Integer getPropuestaTrabajo() {
        return propuestaTrabajo;
    }

    public void setPropuestaTrabajo(Integer propuestaTrabajo) {
        this.propuestaTrabajo = propuestaTrabajo;
    }

    public Date getPropuestaFecha() {
        return propuestaFecha;
    }

    public void setPropuestaFecha(Date propuestaFecha) {
        this.propuestaFecha = propuestaFecha;
    }

    public Date getPropuestaFechaEntrega() {
        return propuestaFechaEntrega;
    }

    public void setPropuestaFechaEntrega(Date propuestaFechaEntrega) {
        this.propuestaFechaEntrega = propuestaFechaEntrega;
    }

    public String getPropuestaArchivo() {
        return propuestaArchivo;
    }

    public void setPropuestaArchivo(String propuestaArchivo) {
        this.propuestaArchivo = propuestaArchivo;
    }

    public Plazo getPropuestaPlazoCorrecciones() {
        return propuestaPlazoCorrecciones;
    }

    public void setPropuestaPlazoCorrecciones(Plazo propuestaPlazoCorrecciones) {
        this.propuestaPlazoCorrecciones = propuestaPlazoCorrecciones;
    }

    public EstadoPropuesta getPropuestaConceptoEstado() {
        return propuestaConceptoEstado;
    }

    public void setPropuestaConceptoEstado(EstadoPropuesta propuestaConceptoEstado) {
        this.propuestaConceptoEstado = propuestaConceptoEstado;
    }

    public TrabajoGrado getTrabajoGrado() {
        return trabajoGrado;
    }

    public void setTrabajoGrado(TrabajoGrado trabajoGrado) {
        this.trabajoGrado = trabajoGrado;
    }

    @XmlTransient
    public Collection<Observacion> getObservacionCollection() {
        return observacionCollection;
    }

    public void setObservacionCollection(Collection<Observacion> observacionCollection) {
        this.observacionCollection = observacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propuestaTrabajo != null ? propuestaTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propuesta)) {
            return false;
        }
        Propuesta other = (Propuesta) object;
        if ((this.propuestaTrabajo == null && other.propuestaTrabajo != null) || (this.propuestaTrabajo != null && !this.propuestaTrabajo.equals(other.propuestaTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Propuesta[ propuestaTrabajo=" + propuestaTrabajo + " ]";
    }
    
}
