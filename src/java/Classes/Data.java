/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author daniel
 */
public class Data implements Serializable{
    public final static String PERSISTANCE_UNIT_NAME ="ProyectosGradoJPAPU";
    public final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
}
