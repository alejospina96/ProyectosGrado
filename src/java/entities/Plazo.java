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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author daniel
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plazo.findAll", query = "SELECT p FROM Plazo p"),
    @NamedQuery(name = "Plazo.findByPlazoId", query = "SELECT p FROM Plazo p WHERE p.plazoId = :plazoId"),
    @NamedQuery(name = "Plazo.findByPlazoFechaInicio", query = "SELECT p FROM Plazo p WHERE p.plazoFechaInicio = :plazoFechaInicio"),
    @NamedQuery(name = "Plazo.findByPlazoFechaFin", query = "SELECT p FROM Plazo p WHERE p.plazoFechaFin = :plazoFechaFin")})
public class Plazo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "plazo_id", nullable = false)
    private Integer plazoId;
    @Basic(optional = false)
    @Column(name = "plazo_fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date plazoFechaInicio;
    @Basic(optional = false)
    @Column(name = "plazo_fecha_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date plazoFechaFin;
    @OneToMany(mappedBy = "propuestaPlazoCorrecciones")
    private Collection<Propuesta> propuestaCollection;
    @JoinColumn(name = "plazo_concepto", referencedColumnName = "cplazo_id", nullable = false)
    @ManyToOne(optional = false)
    private ConceptoPlazo plazoConcepto;
    @JoinColumn(name = "plazo_multa", referencedColumnName = "id_multa", nullable = false)
    @ManyToOne(optional = false)
    private Multa plazoMulta;
    @OneToMany(mappedBy = "tgPlazoEntrega")
    private Collection<TrabajoGrado> trabajoGradoCollection;
    @OneToMany(mappedBy = "prorrogaPlazoEntrega")
    private Collection<Prorroga> prorrogaCollection;

    public Plazo() {
    }

    public Plazo(Integer plazoId) {
        this.plazoId = plazoId;
    }

    public Plazo(Integer plazoId, Date plazoFechaInicio, Date plazoFechaFin) {
        this.plazoId = plazoId;
        this.plazoFechaInicio = plazoFechaInicio;
        this.plazoFechaFin = plazoFechaFin;
    }

    public Integer getPlazoId() {
        return plazoId;
    }

    public void setPlazoId(Integer plazoId) {
        this.plazoId = plazoId;
    }

    public Date getPlazoFechaInicio() {
        return plazoFechaInicio;
    }

    public void setPlazoFechaInicio(Date plazoFechaInicio) {
        this.plazoFechaInicio = plazoFechaInicio;
    }

    public Date getPlazoFechaFin() {
        return plazoFechaFin;
    }

    public void setPlazoFechaFin(Date plazoFechaFin) {
        this.plazoFechaFin = plazoFechaFin;
    }

    @XmlTransient
    public Collection<Propuesta> getPropuestaCollection() {
        return propuestaCollection;
    }

    public void setPropuestaCollection(Collection<Propuesta> propuestaCollection) {
        this.propuestaCollection = propuestaCollection;
    }

    public ConceptoPlazo getPlazoConcepto() {
        return plazoConcepto;
    }

    public void setPlazoConcepto(ConceptoPlazo plazoConcepto) {
        this.plazoConcepto = plazoConcepto;
    }

    public Multa getPlazoMulta() {
        return plazoMulta;
    }

    public void setPlazoMulta(Multa plazoMulta) {
        this.plazoMulta = plazoMulta;
    }

    @XmlTransient
    public Collection<TrabajoGrado> getTrabajoGradoCollection() {
        return trabajoGradoCollection;
    }

    public void setTrabajoGradoCollection(Collection<TrabajoGrado> trabajoGradoCollection) {
        this.trabajoGradoCollection = trabajoGradoCollection;
    }

    @XmlTransient
    public Collection<Prorroga> getProrrogaCollection() {
        return prorrogaCollection;
    }

    public void setProrrogaCollection(Collection<Prorroga> prorrogaCollection) {
        this.prorrogaCollection = prorrogaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plazoId != null ? plazoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plazo)) {
            return false;
        }
        Plazo other = (Plazo) object;
        if ((this.plazoId == null && other.plazoId != null) || (this.plazoId != null && !this.plazoId.equals(other.plazoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Plazo[ plazoId=" + plazoId + " ]";
    }
    
}
