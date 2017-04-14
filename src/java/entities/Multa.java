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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author daniel
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Multa.findAll", query = "SELECT m FROM Multa m"),
    @NamedQuery(name = "Multa.findByIdMulta", query = "SELECT m FROM Multa m WHERE m.idMulta = :idMulta"),
    @NamedQuery(name = "Multa.findByValorMulta", query = "SELECT m FROM Multa m WHERE m.valorMulta = :valorMulta")})
public class Multa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa", nullable = false)
    private Integer idMulta;
    @Basic(optional = false)
    @Column(name = "valor_multa", nullable = false)
    private double valorMulta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plazoMulta")
    private Collection<Plazo> plazoCollection;

    public Multa() {
    }

    public Multa(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public Multa(Integer idMulta, double valorMulta) {
        this.idMulta = idMulta;
        this.valorMulta = valorMulta;
    }

    public Integer getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
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
        hash += (idMulta != null ? idMulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Multa)) {
            return false;
        }
        Multa other = (Multa) object;
        if ((this.idMulta == null && other.idMulta != null) || (this.idMulta != null && !this.idMulta.equals(other.idMulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Multa[ idMulta=" + idMulta + " ]";
    }
    
}
