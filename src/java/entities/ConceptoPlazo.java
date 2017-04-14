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
@Table(name = "concepto_plazo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptoPlazo.findAll", query = "SELECT c FROM ConceptoPlazo c"),
    @NamedQuery(name = "ConceptoPlazo.findByCplazoId", query = "SELECT c FROM ConceptoPlazo c WHERE c.cplazoId = :cplazoId"),
    @NamedQuery(name = "ConceptoPlazo.findByCplazoDescripcion", query = "SELECT c FROM ConceptoPlazo c WHERE c.cplazoDescripcion = :cplazoDescripcion")})
public class ConceptoPlazo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cplazo_id", nullable = false)
    private Integer cplazoId;
    @Basic(optional = false)
    @Column(name = "cplazo_descripcion", nullable = false, length = 50)
    private String cplazoDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plazoConcepto")
    private Collection<Plazo> plazoCollection;

    public ConceptoPlazo() {
    }

    public ConceptoPlazo(Integer cplazoId) {
        this.cplazoId = cplazoId;
    }

    public ConceptoPlazo(Integer cplazoId, String cplazoDescripcion) {
        this.cplazoId = cplazoId;
        this.cplazoDescripcion = cplazoDescripcion;
    }

    public Integer getCplazoId() {
        return cplazoId;
    }

    public void setCplazoId(Integer cplazoId) {
        this.cplazoId = cplazoId;
    }

    public String getCplazoDescripcion() {
        return cplazoDescripcion;
    }

    public void setCplazoDescripcion(String cplazoDescripcion) {
        this.cplazoDescripcion = cplazoDescripcion;
    }

    @XmlTransient
    public Collection<Plazo> getPlazoCollection() {
        return plazoCollection;
    }

    public void setPlazoCollection(Collection<Plazo> plazoCollection) {
        this.plazoCollection = plazoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cplazoId != null ? cplazoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptoPlazo)) {
            return false;
        }
        ConceptoPlazo other = (ConceptoPlazo) object;
        if ((this.cplazoId == null && other.cplazoId != null) || (this.cplazoId != null && !this.cplazoId.equals(other.cplazoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ConceptoPlazo[ cplazoId=" + cplazoId + " ]";
    }
    
}
