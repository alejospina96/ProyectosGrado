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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author daniel
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByEstudianteCodigo", query = "SELECT e FROM Estudiante e WHERE e.estudianteCodigo = :estudianteCodigo")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "estudiante_codigo", nullable = false)
    private Long estudianteCodigo;
    @JoinTable(name = "estudiante_propuesta", joinColumns = {
        @JoinColumn(name = "ep_estudiante", referencedColumnName = "estudiante_codigo", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "ep_trabajo_propuesto", referencedColumnName = "tg_id", nullable = false)})
    @ManyToMany
    private Collection<TrabajoGrado> trabajoGradoCollection;
    @JoinColumn(name = "estudiante_persona", referencedColumnName = "persona_identificacion", nullable = false)
    @ManyToOne(optional = false)
    private Persona estudiantePersona;
    @JoinColumn(name = "estudiante_programa", referencedColumnName = "programa_codigo", nullable = false)
    @ManyToOne(optional = false)
    private Programa estudiantePrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prorrogaSolicitante")
    private Collection<Prorroga> prorrogaCollection;

    public Estudiante() {
    }

    public Estudiante(Long estudianteCodigo) {
        this.estudianteCodigo = estudianteCodigo;
    }

    public Long getEstudianteCodigo() {
        return estudianteCodigo;
    }

    public void setEstudianteCodigo(Long estudianteCodigo) {
        this.estudianteCodigo = estudianteCodigo;
    }

    @XmlTransient
    public Collection<TrabajoGrado> getTrabajoGradoCollection() {
        return trabajoGradoCollection;
    }

    public void setTrabajoGradoCollection(Collection<TrabajoGrado> trabajoGradoCollection) {
        this.trabajoGradoCollection = trabajoGradoCollection;
    }

    public Persona getEstudiantePersona() {
        return estudiantePersona;
    }

    public void setEstudiantePersona(Persona estudiantePersona) {
        this.estudiantePersona = estudiantePersona;
    }

    public Programa getEstudiantePrograma() {
        return estudiantePrograma;
    }

    public void setEstudiantePrograma(Programa estudiantePrograma) {
        this.estudiantePrograma = estudiantePrograma;
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
        hash += (estudianteCodigo != null ? estudianteCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.estudianteCodigo == null && other.estudianteCodigo != null) || (this.estudianteCodigo != null && !this.estudianteCodigo.equals(other.estudianteCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return estudianteCodigo+"";
    }
    
}
