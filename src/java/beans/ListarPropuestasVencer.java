/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import Classes.Data;
import controlers.PropuestaJpaController;
import entities.Propuesta;
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
public class ListarPropuestasVencer {
    private List<Propuesta> propuestasVencer = new PropuestaJpaController(Data.EMF).findPropuestaEntities();
    private List<Propuesta> propuestas = new PropuestaJpaController(Data.EMF).findPropuestaEntities();
    private Date fecha;
    /**
     * Creates a new instance of ListarPropuestasVencer
     */
    public ListarPropuestasVencer() {
        propuestasVencer.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 60);
        fecha = calendar.getTime();
        Date fecha1;
        System.out.println("entro");
        for (int i = 0; i < propuestas.size(); i++) {
            try {
                if (propuestas.get(i).getPropuestaFechaEntrega() != null) {
                    fecha1 = propuestas.get(i).getPropuestaPlazoCorrecciones().getPlazoFechaFin();
                    if(fecha.after(fecha1)){
                        propuestasVencer.add(propuestas.get(i));
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " error");
            }
        }
    }

    public List<Propuesta> getPropuestasVencer() {
        return propuestasVencer;
    }

    public void setPropuestasVencer(List<Propuesta> propuestasVencer) {
        this.propuestasVencer = propuestasVencer;
    }

    public List<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
