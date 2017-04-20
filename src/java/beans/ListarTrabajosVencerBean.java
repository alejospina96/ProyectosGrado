/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import Classes.Data;
import controlers.TrabajoGradoJpaController;
import entities.TrabajoGrado;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Lab Redes
 */
@ManagedBean
@RequestScoped
public class ListarTrabajosVencerBean {

    private Date fecha;
    private List<TrabajoGrado> trabajos = new TrabajoGradoJpaController(Data.EMF).findTrabajoGradoEntities();
    private List<TrabajoGrado> trabajosVencer;
    /**
     * Creates a new instance of ListarTrabajosVencerBean
     */
    public ListarTrabajosVencerBean() {
        trabajosVencer = new TrabajoGradoJpaController(Data.EMF).findTrabajoGradoEntities() ;
        trabajosVencer.clear();
        Date fecha1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 60);
        fecha = calendar.getTime();
        for (int i = 0; i < trabajos.size(); i++) {
            try {
                if(trabajos.get(i).getTgPlazoEntrega().getPlazoFechaFin()!= null){
                fecha1 = trabajos.get(i).getTgPlazoEntrega().getPlazoFechaFin();
                if(fecha.after(fecha1)){
                    trabajosVencer.add(trabajos.get(i));
                }
              }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<TrabajoGrado> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<TrabajoGrado> trabajos) {
        this.trabajos = trabajos;
    }

    public List<TrabajoGrado> getTrabajosVencer() {
        return trabajosVencer;
    }

    public void setTrabajosVencer(List<TrabajoGrado> trabajosVencer) {
        this.trabajosVencer = trabajosVencer;
    }
    
    
}
