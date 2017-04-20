/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "trabajo_grado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrabajoGrado.findAll", query = "SELECT t FROM TrabajoGrado t"),
    @NamedQuery(name = "TrabajoGrado.findByTgId", query = "SELECT t FROM TrabajoGrado t WHERE t.tgId = :tgId"),
    @NamedQuery(name = "TrabajoGrado.findByTgTematica", query = "SELECT t FROM TrabajoGrado t WHERE t.tgTematica = :tgTematica"),
    @NamedQuery(name = "TrabajoGrado.findByTgArchivo", query = "SELECT t FROM TrabajoGrado t WHERE t.tgArchivo = :tgArchivo"),
    @NamedQuery(name = "TrabajoGrado.findByTgFechaEntrega", query = "SELECT t FROM TrabajoGrado t WHERE t.tgFechaEntrega = :tgFechaEntrega"),
    @NamedQuery(name = "TrabajoGrado.findByFechaDefensa", query = "SELECT t FROM TrabajoGrado t WHERE t.fechaDefensa = :fechaDefensa")})
public class TrabajoGrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tg_id", nullable = false)
    private Integer tgId;
    @Basic(optional = false)
    @Column(name = "tg_tematica", nullable = false, length = 50)
    private String tgTematica;
    @Column(name = "tg_archivo", length = 30)
    private String tgArchivo;
    @Column(name = "tg_fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date tgFechaEntrega;
    @Column(name = "fecha_defensa")
    @Temporal(TemporalType.DATE)
    private Date fechaDefensa;
    @ManyToMany(mappedBy = "trabajoGradoCollection")
    private Collection<Estudiante> estudianteCollection;
    @JoinTable(name = "jurado_trabajo_grado", joinColumns = {
        @JoinColumn(name = "jtg_trabajo_grado", referencedColumnName = "tg_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "jtg_persona", referencedColumnName = "persona_identificacion", nullable = false)})
    @ManyToMany
    private Collection<Persona> personaCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trabajoGrado")
    private Propuesta propuesta;
    @JoinColumn(name = "tg_concepto_estado", referencedColumnName = "epg_id")
    @ManyToOne
    private EstadoTrabajoGrado tgConceptoEstado;
    @JoinColumn(name = "tg_modalidad", referencedColumnName = "modalidad_id", nullable = false)
    @ManyToOne(optional = false)
    private ModalidadTrabajo tgModalidad;
    @JoinColumn(name = "tg_plazo_entrega", referencedColumnName = "plazo_id")
    @ManyToOne
    private Plazo tgPlazoEntrega;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prorrogaTrabajo")
    private Collection<Prorroga> prorrogaCollection;
    @OneToMany(mappedBy = "opgTrabajo")
    private Collection<Observacion> observacionCollection;

    public TrabajoGrado() {
    }

    public TrabajoGrado(Integer tgId) {
        this.tgId = tgId;
    }

    public TrabajoGrado(Integer tgId, String tgTematica) {
        this.tgId = tgId;
        this.tgTematica = tgTematica;
    }

    public Integer getTgId() {
        return tgId;
    }

    public void setTgId(Integer tgId) {
        this.tgId = tgId;
    }

    public String getTgTematica() {
        return tgTematica;
    }

    public void setTgTematica(String tgTematica) {
        this.tgTematica = tgTematica;
    }

    public String getTgArchivo() {
        return tgArchivo;
    }

    public void setTgArchivo(String tgArchivo) {
        this.tgArchivo = tgArchivo;
    }

    public Date getTgFechaEntrega() {
        return tgFechaEntrega;
    }

    public void setTgFechaEntrega(Date tgFechaEntrega) {
        this.tgFechaEntrega = tgFechaEntrega;
    }

    public Date getFechaDefensa() {
        return fechaDefensa;
    }

    public void setFechaDefensa(Date fechaDefensa) {
        this.fechaDefensa = fechaDefensa;
    }

    @XmlTransient
    public Collection<Estudiante> getEstudianteCollection() {
        return estudianteCollection;
    }

    public void setEstudianteCollection(Collection<Estudiante> estudianteCollection) {
        this.estudianteCollection = estudianteCollection;
    }

    @XmlTransient
    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    public EstadoTrabajoGrado getTgConceptoEstado() {
        return tgConceptoEstado;
    }

    public void setTgConceptoEstado(EstadoTrabajoGrado tgConceptoEstado) {
        this.tgConceptoEstado = tgConceptoEstado;
    }

    public ModalidadTrabajo getTgModalidad() {
        return tgModalidad;
    }

    public void setTgModalidad(ModalidadTrabajo tgModalidad) {
        this.tgModalidad = tgModalidad;
    }

    public Plazo getTgPlazoEntrega() {
        return tgPlazoEntrega;
    }

    public void setTgPlazoEntrega(Plazo tgPlazoEntrega) {
        this.tgPlazoEntrega = tgPlazoEntrega;
    }

    @XmlTransient
    public Collection<Prorroga> getProrrogaCollection() {
        return prorrogaCollection;
    }

    public void setProrrogaCollection(Collection<Prorroga> prorrogaCollection) {
        this.prorrogaCollection = prorrogaCollection;
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
        hash += (tgId != null ? tgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrabajoGrado)) {
            return false;
        }
        TrabajoGrado other = (TrabajoGrado) object;
        if ((this.tgId == null && other.tgId != null) || (this.tgId != null && !this.tgId.equals(other.tgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TrabajoGrado[ tgId=" + tgId + " ]";
    }
    
    public String obtenerCodigo(){
        List <Estudiante> est = new ArrayList<Estudiante>(estudianteCollection);
        String res = "";
        if (!est.isEmpty()) {
            for (int i = 0; i < est.size(); i++) {
                res+= est.get(i).getEstudiantePersona().getPersonaPNombre()+ "\n " + est.get(i).getEstudiantePersona().getPersonaPApellido()+ "\n";                
            }
            return res;
        }else{
            res = "NULL";
        }
        return res;
    }
}
