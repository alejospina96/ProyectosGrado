/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "estado_trabajo_grado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoTrabajoGrado.findAll", query = "SELECT e FROM EstadoTrabajoGrado e"),
    @NamedQuery(name = "EstadoTrabajoGrado.findByEpgId", query = "SELECT e FROM EstadoTrabajoGrado e WHERE e.epgId = :epgId"),
    @NamedQuery(name = "EstadoTrabajoGrado.findByEpgDescripcion", query = "SELECT e FROM EstadoTrabajoGrado e WHERE e.epgDescripcion = :epgDescripcion")})
public class EstadoTrabajoGrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "epg_id", nullable = false)
    private Integer epgId;
    @Basic(optional = false)
    @Column(name = "epg_descripcion", nullable = false, length = 30)
    private String epgDescripcion;
    @OneToMany(mappedBy = "tgConceptoEstado")
    private Collection<TrabajoGrado> trabajoGradoCollection;

    public EstadoTrabajoGrado() {
    }

    public EstadoTrabajoGrado(Integer epgId) {
        this.epgId = epgId;
    }

    public EstadoTrabajoGrado(Integer epgId, String epgDescripcion) {
        this.epgId = epgId;
        this.epgDescripcion = epgDescripcion;
    }

    public Integer getEpgId() {
        return epgId;
    }

    public void setEpgId(Integer epgId) {
        this.epgId = epgId;
    }

    public String getEpgDescripcion() {
        return epgDescripcion;
    }

    public void setEpgDescripcion(String epgDescripcion) {
        this.epgDescripcion = epgDescripcion;
    }

    @XmlTransient
    public Collection<TrabajoGrado> getTrabajoGradoCollection() {
        return trabajoGradoCollection;
    }

    public void setTrabajoGradoCollection(Collection<TrabajoGrado> trabajoGradoCollection) {
        this.trabajoGradoCollection = trabajoGradoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (epgId != null ? epgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoTrabajoGrado)) {
            return false;
        }
        EstadoTrabajoGrado other = (EstadoTrabajoGrado) object;
        if ((this.epgId == null && other.epgId != null) || (this.epgId != null && !this.epgId.equals(other.epgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadoTrabajoGrado[ epgId=" + epgId + " ]";
    }
    
}
