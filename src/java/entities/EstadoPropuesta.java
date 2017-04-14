/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "estado_propuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoPropuesta.findAll", query = "SELECT e FROM EstadoPropuesta e"),
    @NamedQuery(name = "EstadoPropuesta.findByEpId", query = "SELECT e FROM EstadoPropuesta e WHERE e.epId = :epId"),
    @NamedQuery(name = "EstadoPropuesta.findByEpDescripcion", query = "SELECT e FROM EstadoPropuesta e WHERE e.epDescripcion = :epDescripcion")})
public class EstadoPropuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ep_id", nullable = false)
    private Integer epId;
    @Basic(optional = false)
    @Column(name = "ep_descripcion", nullable = false, length = 30)
    private String epDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propuestaConceptoEstado")
    private Collection<Propuesta> propuestaCollection;

    public EstadoPropuesta() {
    }

    public EstadoPropuesta(Integer epId) {
        this.epId = epId;
    }

    public EstadoPropuesta(Integer epId, String epDescripcion) {
        this.epId = epId;
        this.epDescripcion = epDescripcion;
    }

    public Integer getEpId() {
        return epId;
    }

    public void setEpId(Integer epId) {
        this.epId = epId;
    }

    public String getEpDescripcion() {
        return epDescripcion;
    }

    public void setEpDescripcion(String epDescripcion) {
        this.epDescripcion = epDescripcion;
    }

    @XmlTransient
    public Collection<Propuesta> getPropuestaCollection() {
        return propuestaCollection;
    }

    public void setPropuestaCollection(Collection<Propuesta> propuestaCollection) {
        this.propuestaCollection = propuestaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (epId != null ? epId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoPropuesta)) {
            return false;
        }
        EstadoPropuesta other = (EstadoPropuesta) object;
        if ((this.epId == null && other.epId != null) || (this.epId != null && !this.epId.equals(other.epId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadoPropuesta[ epId=" + epId + " ]";
    }
    
}
