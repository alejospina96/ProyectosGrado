/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import entities.Multa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Plazo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class MultaJpaController implements Serializable {

    public MultaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Multa multa) {
        if (multa.getPlazoCollection() == null) {
            multa.setPlazoCollection(new ArrayList<Plazo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Plazo> attachedPlazoCollection = new ArrayList<Plazo>();
            for (Plazo plazoCollectionPlazoToAttach : multa.getPlazoCollection()) {
                plazoCollectionPlazoToAttach = em.getReference(plazoCollectionPlazoToAttach.getClass(), plazoCollectionPlazoToAttach.getPlazoId());
                attachedPlazoCollection.add(plazoCollectionPlazoToAttach);
            }
            multa.setPlazoCollection(attachedPlazoCollection);
            em.persist(multa);
            for (Plazo plazoCollectionPlazo : multa.getPlazoCollection()) {
                Multa oldPlazoMultaOfPlazoCollectionPlazo = plazoCollectionPlazo.getPlazoMulta();
                plazoCollectionPlazo.setPlazoMulta(multa);
                plazoCollectionPlazo = em.merge(plazoCollectionPlazo);
                if (oldPlazoMultaOfPlazoCollectionPlazo != null) {
                    oldPlazoMultaOfPlazoCollectionPlazo.getPlazoCollection().remove(plazoCollectionPlazo);
                    oldPlazoMultaOfPlazoCollectionPlazo = em.merge(oldPlazoMultaOfPlazoCollectionPlazo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Multa multa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Multa persistentMulta = em.find(Multa.class, multa.getIdMulta());
            Collection<Plazo> plazoCollectionOld = persistentMulta.getPlazoCollection();
            Collection<Plazo> plazoCollectionNew = multa.getPlazoCollection();
            List<String> illegalOrphanMessages = null;
            for (Plazo plazoCollectionOldPlazo : plazoCollectionOld) {
                if (!plazoCollectionNew.contains(plazoCollectionOldPlazo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Plazo " + plazoCollectionOldPlazo + " since its plazoMulta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Plazo> attachedPlazoCollectionNew = new ArrayList<Plazo>();
            for (Plazo plazoCollectionNewPlazoToAttach : plazoCollectionNew) {
                plazoCollectionNewPlazoToAttach = em.getReference(plazoCollectionNewPlazoToAttach.getClass(), plazoCollectionNewPlazoToAttach.getPlazoId());
                attachedPlazoCollectionNew.add(plazoCollectionNewPlazoToAttach);
            }
            plazoCollectionNew = attachedPlazoCollectionNew;
            multa.setPlazoCollection(plazoCollectionNew);
            multa = em.merge(multa);
            for (Plazo plazoCollectionNewPlazo : plazoCollectionNew) {
                if (!plazoCollectionOld.contains(plazoCollectionNewPlazo)) {
                    Multa oldPlazoMultaOfPlazoCollectionNewPlazo = plazoCollectionNewPlazo.getPlazoMulta();
                    plazoCollectionNewPlazo.setPlazoMulta(multa);
                    plazoCollectionNewPlazo = em.merge(plazoCollectionNewPlazo);
                    if (oldPlazoMultaOfPlazoCollectionNewPlazo != null && !oldPlazoMultaOfPlazoCollectionNewPlazo.equals(multa)) {
                        oldPlazoMultaOfPlazoCollectionNewPlazo.getPlazoCollection().remove(plazoCollectionNewPlazo);
                        oldPlazoMultaOfPlazoCollectionNewPlazo = em.merge(oldPlazoMultaOfPlazoCollectionNewPlazo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = multa.getIdMulta();
                if (findMulta(id) == null) {
                    throw new NonexistentEntityException("The multa with id " + id + " no longer exists.");
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
            Multa multa;
            try {
                multa = em.getReference(Multa.class, id);
                multa.getIdMulta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The multa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Plazo> plazoCollectionOrphanCheck = multa.getPlazoCollection();
            for (Plazo plazoCollectionOrphanCheckPlazo : plazoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Multa (" + multa + ") cannot be destroyed since the Plazo " + plazoCollectionOrphanCheckPlazo + " in its plazoCollection field has a non-nullable plazoMulta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(multa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Multa> findMultaEntities() {
        return findMultaEntities(true, -1, -1);
    }

    public List<Multa> findMultaEntities(int maxResults, int firstResult) {
        return findMultaEntities(false, maxResults, firstResult);
    }

    private List<Multa> findMultaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Multa.class));
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

    public Multa findMulta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Multa.class, id);
        } finally {
            em.close();
        }
    }

    public int getMultaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Multa> rt = cq.from(Multa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
