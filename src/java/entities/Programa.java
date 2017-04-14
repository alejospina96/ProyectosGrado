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
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p"),
    @NamedQuery(name = "Programa.findByProgramaCodigo", query = "SELECT p FROM Programa p WHERE p.programaCodigo = :programaCodigo"),
    @NamedQuery(name = "Programa.findByProgramaAbreviatura", query = "SELECT p FROM Programa p WHERE p.programaAbreviatura = :programaAbreviatura"),
    @NamedQuery(name = "Programa.findByProgramaNombre", query = "SELECT p FROM Programa p WHERE p.programaNombre = :programaNombre")})
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "programa_codigo", nullable = false, length = 2)
    private String programaCodigo;
    @Basic(optional = false)
    @Column(name = "programa_abreviatura", nullable = false, length = 5)
    private String programaAbreviatura;
    @Basic(optional = false)
    @Column(name = "programa_nombre", nullable = false, length = 50)
    private String programaNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiantePrograma")
    private Collection<Estudiante> estudianteCollection;

    public Programa() {
    }

    public Programa(String programaCodigo) {
        this.programaCodigo = programaCodigo;
    }

    public Programa(String programaCodigo, String programaAbreviatura, String programaNombre) {
        this.programaCodigo = programaCodigo;
        this.programaAbreviatura = programaAbreviatura;
        this.programaNombre = programaNombre;
    }

    public String getProgramaCodigo() {
        return programaCodigo;
    }

    public void setProgramaCodigo(String programaCodigo) {
        this.programaCodigo = programaCodigo;
    }

    public String getProgramaAbreviatura() {
        return programaAbreviatura;
    }

    public void setProgramaAbreviatura(String programaAbreviatura) {
        this.programaAbreviatura = programaAbreviatura;
    }

    public String getProgramaNombre() {
        return programaNombre;
    }

    public void setProgramaNombre(String programaNombre) {
        this.programaNombre = programaNombre;
    }

    @XmlTransient
    public Collection<Estudiante> getEstudianteCollection() {
        return estudianteCollection;
    }

    public void setEstudianteCollection(Collection<Estudiante> estudianteCollection) {
        this.estudianteCollection = estudianteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programaCodigo != null ? programaCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programa)) {
            return false;
        }
        Programa other = (Programa) object;
        if ((this.programaCodigo == null && other.programaCodigo != null) || (this.programaCodigo != null && !this.programaCodigo.equals(other.programaCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Programa[ programaCodigo=" + programaCodigo + " ]";
    }
    
}
