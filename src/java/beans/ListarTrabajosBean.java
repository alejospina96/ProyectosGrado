/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Data;
import controlers.PropuestaJpaController;
import controlers.TrabajoGradoJpaController;
import entities.Persona;
import entities.TrabajoGrado;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cristian
 */
@ManagedBean
@RequestScoped
public class ListarTrabajosBean {

    private Long id;
    private Date fecha;
    private List<TrabajoGrado> trabajos = new TrabajoGradoJpaController(Data.EMF).findTrabajoGradoEntities();
    private List<TrabajoGrado> trabajosJutado;
    private List<TrabajoGrado> trabajosVencer;
    /**
     * Creates a new instance of ListarTrabajosBean
     */
    public ListarTrabajosBean() {
    }

    public List<TrabajoGrado> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<TrabajoGrado> trabajos) {
        this.trabajos = trabajos;
    }

    public List<TrabajoGrado> getTrabajosJutado() {
        return trabajosJutado;
    }

    public void setTrabajosJutado(List<TrabajoGrado> trabajosJutado) {
        this.trabajosJutado = trabajosJutado;
    }

    public List<TrabajoGrado> getTrabajosVencer() {
        return trabajosVencer;
    }

    public void setTrabajosVencer(List<TrabajoGrado> trabajosVencer) {
        this.trabajosVencer = trabajosVencer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public List trabajosJurado(){
        trabajosJutado = new TrabajoGradoJpaController(Data.EMF).findTrabajoGradoEntities() ;
        trabajosJutado.clear();
        for (int i=0; i<trabajos.size(); i++) {
            Collection<Persona> personaC = trabajos.get(i).getPersonaCollection();
            List <Persona> personas = new ArrayList<Persona>(personaC);
            for (int j = 0; j < personas.size(); j++) {
                if(personas.get(j).getPersonaIdentificacion().longValue() == id.longValue()){
                    trabajosJutado.add(trabajos.get(i));
                    System.out.println(id +" comparado con "+ personas.get(j).getPersonaIdentificacion());
                }
            }
        }
        return trabajosJutado;
    }
}
