/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.NonexistentEntityException;
import entities.Observacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Propuesta;
import entities.TrabajoGrado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class ObservacionJpaController implements Serializable {

    public ObservacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Observacion observacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuesta opgPropuesta = observacion.getOpgPropuesta();
            if (opgPropuesta != null) {
                opgPropuesta = em.getReference(opgPropuesta.getClass(), opgPropuesta.getPropuestaTrabajo());
                observacion.setOpgPropuesta(opgPropuesta);
            }
            TrabajoGrado opgTrabajo = observacion.getOpgTrabajo();
            if (opgTrabajo != null) {
                opgTrabajo = em.getReference(opgTrabajo.getClass(), opgTrabajo.getTgId());
                observacion.setOpgTrabajo(opgTrabajo);
            }
            em.persist(observacion);
            if (opgPropuesta != null) {
                opgPropuesta.getObservacionCollection().add(observacion);
                opgPropuesta = em.merge(opgPropuesta);
            }
            if (opgTrabajo != null) {
                opgTrabajo.getObservacionCollection().add(observacion);
                opgTrabajo = em.merge(opgTrabajo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Observacion observacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Observacion persistentObservacion = em.find(Observacion.class, observacion.getOpgId());
            Propuesta opgPropuestaOld = persistentObservacion.getOpgPropuesta();
            Propuesta opgPropuestaNew = observacion.getOpgPropuesta();
            TrabajoGrado opgTrabajoOld = persistentObservacion.getOpgTrabajo();
            TrabajoGrado opgTrabajoNew = observacion.getOpgTrabajo();
            if (opgPropuestaNew != null) {
                opgPropuestaNew = em.getReference(opgPropuestaNew.getClass(), opgPropuestaNew.getPropuestaTrabajo());
                observacion.setOpgPropuesta(opgPropuestaNew);
            }
            if (opgTrabajoNew != null) {
                opgTrabajoNew = em.getReference(opgTrabajoNew.getClass(), opgTrabajoNew.getTgId());
                observacion.setOpgTrabajo(opgTrabajoNew);
            }
            observacion = em.merge(observacion);
            if (opgPropuestaOld != null && !opgPropuestaOld.equals(opgPropuestaNew)) {
                opgPropuestaOld.getObservacionCollection().remove(observacion);
                opgPropuestaOld = em.merge(opgPropuestaOld);
            }
            if (opgPropuestaNew != null && !opgPropuestaNew.equals(opgPropuestaOld)) {
                opgPropuestaNew.getObservacionCollection().add(observacion);
                opgPropuestaNew = em.merge(opgPropuestaNew);
            }
            if (opgTrabajoOld != null && !opgTrabajoOld.equals(opgTrabajoNew)) {
                opgTrabajoOld.getObservacionCollection().remove(observacion);
                opgTrabajoOld = em.merge(opgTrabajoOld);
            }
            if (opgTrabajoNew != null && !opgTrabajoNew.equals(opgTrabajoOld)) {
                opgTrabajoNew.getObservacionCollection().add(observacion);
                opgTrabajoNew = em.merge(opgTrabajoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = observacion.getOpgId();
                if (findObservacion(id) == null) {
                    throw new NonexistentEntityException("The observacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Observacion observacion;
            try {
                observacion = em.getReference(Observacion.class, id);
                observacion.getOpgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The observacion with id " + id + " no longer exists.", enfe);
            }
            Propuesta opgPropuesta = observacion.getOpgPropuesta();
            if (opgPropuesta != null) {
                opgPropuesta.getObservacionCollection().remove(observacion);
                opgPropuesta = em.merge(opgPropuesta);
            }
            TrabajoGrado opgTrabajo = observacion.getOpgTrabajo();
            if (opgTrabajo != null) {
                opgTrabajo.getObservacionCollection().remove(observacion);
                opgTrabajo = em.merge(opgTrabajo);
            }
            em.remove(observacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Observacion> findObservacionEntities() {
        return findObservacionEntities(true, -1, -1);
    }

    public List<Observacion> findObservacionEntities(int maxResults, int firstResult) {
        return findObservacionEntities(false, maxResults, firstResult);
    }

    private List<Observacion> findObservacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Observacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Observacion findObservacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Observacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getObservacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Observacion> rt = cq.from(Observacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
