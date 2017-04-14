/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import controlers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Plazo;
import entities.EstadoPropuesta;
import entities.TrabajoGrado;
import entities.Observacion;
import entities.Propuesta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class PropuestaJpaController implements Serializable {

    public PropuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propuesta propuesta) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (propuesta.getObservacionCollection() == null) {
            propuesta.setObservacionCollection(new ArrayList<Observacion>());
        }
        List<String> illegalOrphanMessages = null;
        TrabajoGrado trabajoGradoOrphanCheck = propuesta.getTrabajoGrado();
        if (trabajoGradoOrphanCheck != null) {
            Propuesta oldPropuestaOfTrabajoGrado = trabajoGradoOrphanCheck.getPropuesta();
            if (oldPropuestaOfTrabajoGrado != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TrabajoGrado " + trabajoGradoOrphanCheck + " already has an item of type Propuesta whose trabajoGrado column cannot be null. Please make another selection for the trabajoGrado field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazo propuestaPlazoCorrecciones = propuesta.getPropuestaPlazoCorrecciones();
            if (propuestaPlazoCorrecciones != null) {
                propuestaPlazoCorrecciones = em.getReference(propuestaPlazoCorrecciones.getClass(), propuestaPlazoCorrecciones.getPlazoId());
                propuesta.setPropuestaPlazoCorrecciones(propuestaPlazoCorrecciones);
            }
            EstadoPropuesta propuestaConceptoEstado = propuesta.getPropuestaConceptoEstado();
            if (propuestaConceptoEstado != null) {
                propuestaConceptoEstado = em.getReference(propuestaConceptoEstado.getClass(), propuestaConceptoEstado.getEpId());
                propuesta.setPropuestaConceptoEstado(propuestaConceptoEstado);
            }
            TrabajoGrado trabajoGrado = propuesta.getTrabajoGrado();
            if (trabajoGrado != null) {
                trabajoGrado = em.getReference(trabajoGrado.getClass(), trabajoGrado.getTgId());
                propuesta.setTrabajoGrado(trabajoGrado);
            }
            Collection<Observacion> attachedObservacionCollection = new ArrayList<Observacion>();
            for (Observacion observacionCollectionObservacionToAttach : propuesta.getObservacionCollection()) {
                observacionCollectionObservacionToAttach = em.getReference(observacionCollectionObservacionToAttach.getClass(), observacionCollectionObservacionToAttach.getOpgId());
                attachedObservacionCollection.add(observacionCollectionObservacionToAttach);
            }
            propuesta.setObservacionCollection(attachedObservacionCollection);
            em.persist(propuesta);
            if (propuestaPlazoCorrecciones != null) {
                propuestaPlazoCorrecciones.getPropuestaCollection().add(propuesta);
                propuestaPlazoCorrecciones = em.merge(propuestaPlazoCorrecciones);
            }
            if (propuestaConceptoEstado != null) {
                propuestaConceptoEstado.getPropuestaCollection().add(propuesta);
                propuestaConceptoEstado = em.merge(propuestaConceptoEstado);
            }
            if (trabajoGrado != null) {
                trabajoGrado.setPropuesta(propuesta);
                trabajoGrado = em.merge(trabajoGrado);
            }
            for (Observacion observacionCollectionObservacion : propuesta.getObservacionCollection()) {
                Propuesta oldOpgPropuestaOfObservacionCollectionObservacion = observacionCollectionObservacion.getOpgPropuesta();
                observacionCollectionObservacion.setOpgPropuesta(propuesta);
                observacionCollectionObservacion = em.merge(observacionCollectionObservacion);
                if (oldOpgPropuestaOfObservacionCollectionObservacion != null) {
                    oldOpgPropuestaOfObservacionCollectionObservacion.getObservacionCollection().remove(observacionCollectionObservacion);
                    oldOpgPropuestaOfObservacionCollectionObservacion = em.merge(oldOpgPropuestaOfObservacionCollectionObservacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropuesta(propuesta.getPropuestaTrabajo()) != null) {
                throw new PreexistingEntityException("Propuesta " + propuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propuesta propuesta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuesta persistentPropuesta = em.find(Propuesta.class, propuesta.getPropuestaTrabajo());
            Plazo propuestaPlazoCorreccionesOld = persistentPropuesta.getPropuestaPlazoCorrecciones();
            Plazo propuestaPlazoCorreccionesNew = propuesta.getPropuestaPlazoCorrecciones();
            EstadoPropuesta propuestaConceptoEstadoOld = persistentPropuesta.getPropuestaConceptoEstado();
            EstadoPropuesta propuestaConceptoEstadoNew = propuesta.getPropuestaConceptoEstado();
            TrabajoGrado trabajoGradoOld = persistentPropuesta.getTrabajoGrado();
            TrabajoGrado trabajoGradoNew = propuesta.getTrabajoGrado();
            Collection<Observacion> observacionCollectionOld = persistentPropuesta.getObservacionCollection();
            Collection<Observacion> observacionCollectionNew = propuesta.getObservacionCollection();
            List<String> illegalOrphanMessages = null;
            if (trabajoGradoNew != null && !trabajoGradoNew.equals(trabajoGradoOld)) {
                Propuesta oldPropuestaOfTrabajoGrado = trabajoGradoNew.getPropuesta();
                if (oldPropuestaOfTrabajoGrado != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TrabajoGrado " + trabajoGradoNew + " already has an item of type Propuesta whose trabajoGrado column cannot be null. Please make another selection for the trabajoGrado field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (propuestaPlazoCorreccionesNew != null) {
                propuestaPlazoCorreccionesNew = em.getReference(propuestaPlazoCorreccionesNew.getClass(), propuestaPlazoCorreccionesNew.getPlazoId());
                propuesta.setPropuestaPlazoCorrecciones(propuestaPlazoCorreccionesNew);
            }
            if (propuestaConceptoEstadoNew != null) {
                propuestaConceptoEstadoNew = em.getReference(propuestaConceptoEstadoNew.getClass(), propuestaConceptoEstadoNew.getEpId());
                propuesta.setPropuestaConceptoEstado(propuestaConceptoEstadoNew);
            }
            if (trabajoGradoNew != null) {
                trabajoGradoNew = em.getReference(trabajoGradoNew.getClass(), trabajoGradoNew.getTgId());
                propuesta.setTrabajoGrado(trabajoGradoNew);
            }
            Collection<Observacion> attachedObservacionCollectionNew = new ArrayList<Observacion>();
            for (Observacion observacionCollectionNewObservacionToAttach : observacionCollectionNew) {
                observacionCollectionNewObservacionToAttach = em.getReference(observacionCollectionNewObservacionToAttach.getClass(), observacionCollectionNewObservacionToAttach.getOpgId());
                attachedObservacionCollectionNew.add(observacionCollectionNewObservacionToAttach);
            }
            observacionCollectionNew = attachedObservacionCollectionNew;
            propuesta.setObservacionCollection(observacionCollectionNew);
            propuesta = em.merge(propuesta);
            if (propuestaPlazoCorreccionesOld != null && !propuestaPlazoCorreccionesOld.equals(propuestaPlazoCorreccionesNew)) {
                propuestaPlazoCorreccionesOld.getPropuestaCollection().remove(propuesta);
                propuestaPlazoCorreccionesOld = em.merge(propuestaPlazoCorreccionesOld);
            }
            if (propuestaPlazoCorreccionesNew != null && !propuestaPlazoCorreccionesNew.equals(propuestaPlazoCorreccionesOld)) {
                propuestaPlazoCorreccionesNew.getPropuestaCollection().add(propuesta);
                propuestaPlazoCorreccionesNew = em.merge(propuestaPlazoCorreccionesNew);
            }
            if (propuestaConceptoEstadoOld != null && !propuestaConceptoEstadoOld.equals(propuestaConceptoEstadoNew)) {
                propuestaConceptoEstadoOld.getPropuestaCollection().remove(propuesta);
                propuestaConceptoEstadoOld = em.merge(propuestaConceptoEstadoOld);
            }
            if (propuestaConceptoEstadoNew != null && !propuestaConceptoEstadoNew.equals(propuestaConceptoEstadoOld)) {
                propuestaConceptoEstadoNew.getPropuestaCollection().add(propuesta);
                propuestaConceptoEstadoNew = em.merge(propuestaConceptoEstadoNew);
            }
            if (trabajoGradoOld != null && !trabajoGradoOld.equals(trabajoGradoNew)) {
                trabajoGradoOld.setPropuesta(null);
                trabajoGradoOld = em.merge(trabajoGradoOld);
            }
            if (trabajoGradoNew != null && !trabajoGradoNew.equals(trabajoGradoOld)) {
                trabajoGradoNew.setPropuesta(propuesta);
                trabajoGradoNew = em.merge(trabajoGradoNew);
            }
            for (Observacion observacionCollectionOldObservacion : observacionCollectionOld) {
                if (!observacionCollectionNew.contains(observacionCollectionOldObservacion)) {
                    observacionCollectionOldObservacion.setOpgPropuesta(null);
                    observacionCollectionOldObservacion = em.merge(observacionCollectionOldObservacion);
                }
            }
            for (Observacion observacionCollectionNewObservacion : observacionCollectionNew) {
                if (!observacionCollectionOld.contains(observacionCollectionNewObservacion)) {
                    Propuesta oldOpgPropuestaOfObservacionCollectionNewObservacion = observacionCollectionNewObservacion.getOpgPropuesta();
                    observacionCollectionNewObservacion.setOpgPropuesta(propuesta);
                    observacionCollectionNewObservacion = em.merge(observacionCollectionNewObservacion);
                    if (oldOpgPropuestaOfObservacionCollectionNewObservacion != null && !oldOpgPropuestaOfObservacionCollectionNewObservacion.equals(propuesta)) {
                        oldOpgPropuestaOfObservacionCollectionNewObservacion.getObservacionCollection().remove(observacionCollectionNewObservacion);
                        oldOpgPropuestaOfObservacionCollectionNewObservacion = em.merge(oldOpgPropuestaOfObservacionCollectionNewObservacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = propuesta.getPropuestaTrabajo();
                if (findPropuesta(id) == null) {
                    throw new NonexistentEntityException("The propuesta with id " + id + " no longer exists.");
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
            Propuesta propuesta;
            try {
                propuesta = em.getReference(Propuesta.class, id);
                propuesta.getPropuestaTrabajo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propuesta with id " + id + " no longer exists.", enfe);
            }
            Plazo propuestaPlazoCorrecciones = propuesta.getPropuestaPlazoCorrecciones();
            if (propuestaPlazoCorrecciones != null) {
                propuestaPlazoCorrecciones.getPropuestaCollection().remove(propuesta);
                propuestaPlazoCorrecciones = em.merge(propuestaPlazoCorrecciones);
            }
            EstadoPropuesta propuestaConceptoEstado = propuesta.getPropuestaConceptoEstado();
            if (propuestaConceptoEstado != null) {
                propuestaConceptoEstado.getPropuestaCollection().remove(propuesta);
                propuestaConceptoEstado = em.merge(propuestaConceptoEstado);
            }
            TrabajoGrado trabajoGrado = propuesta.getTrabajoGrado();
            if (trabajoGrado != null) {
                trabajoGrado.setPropuesta(null);
                trabajoGrado = em.merge(trabajoGrado);
            }
            Collection<Observacion> observacionCollection = propuesta.getObservacionCollection();
            for (Observacion observacionCollectionObservacion : observacionCollection) {
                observacionCollectionObservacion.setOpgPropuesta(null);
                observacionCollectionObservacion = em.merge(observacionCollectionObservacion);
            }
            em.remove(propuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propuesta> findPropuestaEntities() {
        return findPropuestaEntities(true, -1, -1);
    }

    public List<Propuesta> findPropuestaEntities(int maxResults, int firstResult) {
        return findPropuestaEntities(false, maxResults, firstResult);
    }

    private List<Propuesta> findPropuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propuesta.class));
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

    public Propuesta findPropuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propuesta> rt = cq.from(Propuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
