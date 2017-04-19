/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import entities.ModalidadTrabajo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.TrabajoGrado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class ModalidadTrabajoJpaController implements Serializable {

    public ModalidadTrabajoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ModalidadTrabajo modalidadTrabajo) {
        if (modalidadTrabajo.getTrabajoGradoCollection() == null) {
            modalidadTrabajo.setTrabajoGradoCollection(new ArrayList<TrabajoGrado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TrabajoGrado> attachedTrabajoGradoCollection = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGradoToAttach : modalidadTrabajo.getTrabajoGradoCollection()) {
                trabajoGradoCollectionTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionTrabajoGradoToAttach.getClass(), trabajoGradoCollectionTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollection.add(trabajoGradoCollectionTrabajoGradoToAttach);
            }
            modalidadTrabajo.setTrabajoGradoCollection(attachedTrabajoGradoCollection);
            em.persist(modalidadTrabajo);
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : modalidadTrabajo.getTrabajoGradoCollection()) {
                ModalidadTrabajo oldTgModalidadOfTrabajoGradoCollectionTrabajoGrado = trabajoGradoCollectionTrabajoGrado.getTgModalidad();
                trabajoGradoCollectionTrabajoGrado.setTgModalidad(modalidadTrabajo);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
                if (oldTgModalidadOfTrabajoGradoCollectionTrabajoGrado != null) {
                    oldTgModalidadOfTrabajoGradoCollectionTrabajoGrado.getTrabajoGradoCollection().remove(trabajoGradoCollectionTrabajoGrado);
                    oldTgModalidadOfTrabajoGradoCollectionTrabajoGrado = em.merge(oldTgModalidadOfTrabajoGradoCollectionTrabajoGrado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ModalidadTrabajo modalidadTrabajo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ModalidadTrabajo persistentModalidadTrabajo = em.find(ModalidadTrabajo.class, modalidadTrabajo.getModalidadId());
            Collection<TrabajoGrado> trabajoGradoCollectionOld = persistentModalidadTrabajo.getTrabajoGradoCollection();
            Collection<TrabajoGrado> trabajoGradoCollectionNew = modalidadTrabajo.getTrabajoGradoCollection();
            List<String> illegalOrphanMessages = null;
            for (TrabajoGrado trabajoGradoCollectionOldTrabajoGrado : trabajoGradoCollectionOld) {
                if (!trabajoGradoCollectionNew.contains(trabajoGradoCollectionOldTrabajoGrado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TrabajoGrado " + trabajoGradoCollectionOldTrabajoGrado + " since its tgModalidad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TrabajoGrado> attachedTrabajoGradoCollectionNew = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGradoToAttach : trabajoGradoCollectionNew) {
                trabajoGradoCollectionNewTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionNewTrabajoGradoToAttach.getClass(), trabajoGradoCollectionNewTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollectionNew.add(trabajoGradoCollectionNewTrabajoGradoToAttach);
            }
            trabajoGradoCollectionNew = attachedTrabajoGradoCollectionNew;
            modalidadTrabajo.setTrabajoGradoCollection(trabajoGradoCollectionNew);
            modalidadTrabajo = em.merge(modalidadTrabajo);
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGrado : trabajoGradoCollectionNew) {
                if (!trabajoGradoCollectionOld.contains(trabajoGradoCollectionNewTrabajoGrado)) {
                    ModalidadTrabajo oldTgModalidadOfTrabajoGradoCollectionNewTrabajoGrado = trabajoGradoCollectionNewTrabajoGrado.getTgModalidad();
                    trabajoGradoCollectionNewTrabajoGrado.setTgModalidad(modalidadTrabajo);
                    trabajoGradoCollectionNewTrabajoGrado = em.merge(trabajoGradoCollectionNewTrabajoGrado);
                    if (oldTgModalidadOfTrabajoGradoCollectionNewTrabajoGrado != null && !oldTgModalidadOfTrabajoGradoCollectionNewTrabajoGrado.equals(modalidadTrabajo)) {
                        oldTgModalidadOfTrabajoGradoCollectionNewTrabajoGrado.getTrabajoGradoCollection().remove(trabajoGradoCollectionNewTrabajoGrado);
                        oldTgModalidadOfTrabajoGradoCollectionNewTrabajoGrado = em.merge(oldTgModalidadOfTrabajoGradoCollectionNewTrabajoGrado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modalidadTrabajo.getModalidadId();
                if (findModalidadTrabajo(id) == null) {
                    throw new NonexistentEntityException("The modalidadTrabajo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ModalidadTrabajo modalidadTrabajo;
            try {
                modalidadTrabajo = em.getReference(ModalidadTrabajo.class, id);
                modalidadTrabajo.getModalidadId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modalidadTrabajo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TrabajoGrado> trabajoGradoCollectionOrphanCheck = modalidadTrabajo.getTrabajoGradoCollection();
            for (TrabajoGrado trabajoGradoCollectionOrphanCheckTrabajoGrado : trabajoGradoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ModalidadTrabajo (" + modalidadTrabajo + ") cannot be destroyed since the TrabajoGrado " + trabajoGradoCollectionOrphanCheckTrabajoGrado + " in its trabajoGradoCollection field has a non-nullable tgModalidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(modalidadTrabajo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ModalidadTrabajo> findModalidadTrabajoEntities() {
        return findModalidadTrabajoEntities(true, -1, -1);
    }

    public List<ModalidadTrabajo> findModalidadTrabajoEntities(int maxResults, int firstResult) {
        return findModalidadTrabajoEntities(false, maxResults, firstResult);
    }

    private List<ModalidadTrabajo> findModalidadTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ModalidadTrabajo.class));
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

    public ModalidadTrabajo findModalidadTrabajo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ModalidadTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModalidadTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ModalidadTrabajo> rt = cq.from(ModalidadTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
