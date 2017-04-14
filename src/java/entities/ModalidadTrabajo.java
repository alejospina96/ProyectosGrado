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
@Table(name = "modalidad_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModalidadTrabajo.findAll", query = "SELECT m FROM ModalidadTrabajo m"),
    @NamedQuery(name = "ModalidadTrabajo.findByModalidadId", query = "SELECT m FROM ModalidadTrabajo m WHERE m.modalidadId = :modalidadId"),
    @NamedQuery(name = "ModalidadTrabajo.findByModalidadDescripcion", query = "SELECT m FROM ModalidadTrabajo m WHERE m.modalidadDescripcion = :modalidadDescripcion")})
public class ModalidadTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "modalidad_id", nullable = false)
    private Integer modalidadId;
    @Basic(optional = false)
    @Column(name = "modalidad_descripcion", nullable = false, length = 30)
    private String modalidadDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tgModalidad")
    private Collection<TrabajoGrado> trabajoGradoCollection;

    public ModalidadTrabajo() {
    }

    public ModalidadTrabajo(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public ModalidadTrabajo(Integer modalidadId, String modalidadDescripcion) {
        this.modalidadId = modalidadId;
        this.modalidadDescripcion = modalidadDescripcion;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public String getModalidadDescripcion() {
        return modalidadDescripcion;
    }

    public void setModalidadDescripcion(String modalidadDescripcion) {
        this.modalidadDescripcion = modalidadDescripcion;
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
        hash += (modalidadId != null ? modalidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModalidadTrabajo)) {
            return false;
        }
        ModalidadTrabajo other = (ModalidadTrabajo) object;
        if ((this.modalidadId == null && other.modalidadId != null) || (this.modalidadId != null && !this.modalidadId.equals(other.modalidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ModalidadTrabajo[ modalidadId=" + modalidadId + " ]";
    }
    
}
