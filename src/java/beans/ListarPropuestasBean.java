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
 * @author cristian
 */
@ManagedBean
@RequestScoped
public class ListarPropuestasBean {
    private Date fecha;
    private int estado;
    private List<Propuesta> propuestas = new PropuestaJpaController(Data.EMF).findPropuestaEntities();
    private List<Propuesta> propuestasEstado;
    private List<Propuesta> propuestasVencer;
    
    /**
     * Creates a new instance of ListarPropuestasBean
     */
    public ListarPropuestasBean() {
    }

    public List<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Propuesta> getPropuestasEstado() {
        return propuestasEstado;
    }

    public void setPropuestasEstado(List<Propuesta> propuestasEstado) {
        this.propuestasEstado = propuestasEstado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Propuesta> getPropuestasVencer() {
        return propuestasVencer;
    }

    public void setPropuestasVencer(List<Propuesta> propuestasVencer) {
        this.propuestasVencer = propuestasVencer;
    }
    
    
    public List propuestasEstado(){
        propuestasEstado= new PropuestaJpaController(Data.EMF).findPropuestaEntities();
        propuestasEstado.clear();
        for (int i=0; i<propuestas.size(); i++) {
            if(propuestas.get(i).getPropuestaConceptoEstado().getEpId()== estado){
                propuestasEstado.add(propuestas.get(i));
            }
        }
        return propuestasEstado;
    }
    
    public List propuestasAVencer(){
        propuestasVencer = new PropuestaJpaController(Data.EMF).findPropuestaEntities();
        propuestasVencer.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 60);
        fecha = calendar.getTime();
        Date fecha1;
        for (int i = 0; i < propuestas.size(); i++) {
            try {
                if (propuestas.get(i).getPropuestaFechaEntrega() != null) {
                    fecha1 = propuestas.get(i).getPropuestaFechaEntrega();
                    if(fecha.after(fecha1)){
                        propuestasVencer.add(propuestas.get(i));
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return propuestasVencer;
    }
    
    
}
