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
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByPersonaIdentificacion", query = "SELECT p FROM Persona p WHERE p.personaIdentificacion = :personaIdentificacion"),
    @NamedQuery(name = "Persona.findByPersonaPNombre", query = "SELECT p FROM Persona p WHERE p.personaPNombre = :personaPNombre"),
    @NamedQuery(name = "Persona.findByPersonaSNombre", query = "SELECT p FROM Persona p WHERE p.personaSNombre = :personaSNombre"),
    @NamedQuery(name = "Persona.findByPersonaPApellido", query = "SELECT p FROM Persona p WHERE p.personaPApellido = :personaPApellido"),
    @NamedQuery(name = "Persona.findByPersonaSApellido", query = "SELECT p FROM Persona p WHERE p.personaSApellido = :personaSApellido"),
    @NamedQuery(name = "Persona.findByPersonaEmail", query = "SELECT p FROM Persona p WHERE p.personaEmail = :personaEmail"),
    @NamedQuery(name = "Persona.findJurados", query = "SELECT p FROM Persona p WHERE p.personaEsJurado = 1"),
    @NamedQuery(name = "Persona.findByPersonaEsJurado", query = "SELECT p FROM Persona p WHERE p.personaEsJurado = :personaEsJurado")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "persona_identificacion", nullable = false)
    private Long personaIdentificacion;
    @Basic(optional = false)
    @Column(name = "persona_p_nombre", nullable = false, length = 15)
    private String personaPNombre;
    @Column(name = "persona_s_nombre", length = 15)
    private String personaSNombre;
    @Basic(optional = false)
    @Column(name = "persona_p_apellido", nullable = false, length = 15)
    private String personaPApellido;
    @Basic(optional = false)
    @Column(name = "persona_s_apellido", nullable = false, length = 15)
    private String personaSApellido;
    @Column(name = "persona_email", length = 40)
    private String personaEmail;
    @Basic(optional = false)
    @Column(name = "persona_es_jurado", nullable = false)
    private boolean personaEsJurado;
    @ManyToMany(mappedBy = "personaCollection")
    private Collection<TrabajoGrado> trabajoGradoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiantePersona")
    private Collection<Estudiante> estudianteCollection;

    public Persona() {
    }

    public Persona(Long personaIdentificacion) {
        this.personaIdentificacion = personaIdentificacion;
    }

    public Persona(Long personaIdentificacion, String personaPNombre, String personaPApellido, String personaSApellido, boolean personaEsJurado) {
        this.personaIdentificacion = personaIdentificacion;
        this.personaPNombre = personaPNombre;
        this.personaPApellido = personaPApellido;
        this.personaSApellido = personaSApellido;
        this.personaEsJurado = personaEsJurado;
    }

    public Long getPersonaIdentificacion() {
        return personaIdentificacion;
    }

    public void setPersonaIdentificacion(Long personaIdentificacion) {
        this.personaIdentificacion = personaIdentificacion;
    }

    public String getPersonaPNombre() {
        return personaPNombre;
    }

    public void setPersonaPNombre(String personaPNombre) {
        this.personaPNombre = personaPNombre;
    }

    public String getPersonaSNombre() {
        return personaSNombre;
    }

    public void setPersonaSNombre(String personaSNombre) {
        this.personaSNombre = personaSNombre;
    }

    public String getPersonaPApellido() {
        return personaPApellido;
    }

    public void setPersonaPApellido(String personaPApellido) {
        this.personaPApellido = personaPApellido;
    }

    public String getPersonaSApellido() {
        return personaSApellido;
    }

    public void setPersonaSApellido(String personaSApellido) {
        this.personaSApellido = personaSApellido;
    }

    public String getPersonaEmail() {
        return personaEmail;
    }

    public void setPersonaEmail(String personaEmail) {
        this.personaEmail = personaEmail;
    }

    public boolean getPersonaEsJurado() {
        return personaEsJurado;
    }

    public void setPersonaEsJurado(boolean personaEsJurado) {
        this.personaEsJurado = personaEsJurado;
    }

    @XmlTransient
    public Collection<TrabajoGrado> getTrabajoGradoCollection() {
        return trabajoGradoCollection;
    }

    public void setTrabajoGradoCollection(Collection<TrabajoGrado> trabajoGradoCollection) {
        this.trabajoGradoCollection = trabajoGradoCollection;
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
        hash += (personaIdentificacion != null ? personaIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.personaIdentificacion == null && other.personaIdentificacion != null) || (this.personaIdentificacion != null && !this.personaIdentificacion.equals(other.personaIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Persona[ personaIdentificacion=" + personaIdentificacion + " ]";
    }
    
}
