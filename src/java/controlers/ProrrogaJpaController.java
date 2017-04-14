/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Plazo;
import entities.Estudiante;
import entities.Prorroga;
import entities.TrabajoGrado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class ProrrogaJpaController implements Serializable {

    public ProrrogaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prorroga prorroga) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazo prorrogaPlazoEntrega = prorroga.getProrrogaPlazoEntrega();
            if (prorrogaPlazoEntrega != null) {
                prorrogaPlazoEntrega = em.getReference(prorrogaPlazoEntrega.getClass(), prorrogaPlazoEntrega.getPlazoId());
                prorroga.setProrrogaPlazoEntrega(prorrogaPlazoEntrega);
            }
            Estudiante prorrogaSolicitante = prorroga.getProrrogaSolicitante();
            if (prorrogaSolicitante != null) {
                prorrogaSolicitante = em.getReference(prorrogaSolicitante.getClass(), prorrogaSolicitante.getEstudianteCodigo());
                prorroga.setProrrogaSolicitante(prorrogaSolicitante);
            }
            TrabajoGrado prorrogaTrabajo = prorroga.getProrrogaTrabajo();
            if (prorrogaTrabajo != null) {
                prorrogaTrabajo = em.getReference(prorrogaTrabajo.getClass(), prorrogaTrabajo.getTgId());
                prorroga.setProrrogaTrabajo(prorrogaTrabajo);
            }
            em.persist(prorroga);
            if (prorrogaPlazoEntrega != null) {
                prorrogaPlazoEntrega.getProrrogaCollection().add(prorroga);
                prorrogaPlazoEntrega = em.merge(prorrogaPlazoEntrega);
            }
            if (prorrogaSolicitante != null) {
                prorrogaSolicitante.getProrrogaCollection().add(prorroga);
                prorrogaSolicitante = em.merge(prorrogaSolicitante);
            }
            if (prorrogaTrabajo != null) {
                prorrogaTrabajo.getProrrogaCollection().add(prorroga);
                prorrogaTrabajo = em.merge(prorrogaTrabajo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prorroga prorroga) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prorroga persistentProrroga = em.find(Prorroga.class, prorroga.getProrrogaId());
            Plazo prorrogaPlazoEntregaOld = persistentProrroga.getProrrogaPlazoEntrega();
            Plazo prorrogaPlazoEntregaNew = prorroga.getProrrogaPlazoEntrega();
            Estudiante prorrogaSolicitanteOld = persistentProrroga.getProrrogaSolicitante();
            Estudiante prorrogaSolicitanteNew = prorroga.getProrrogaSolicitante();
            TrabajoGrado prorrogaTrabajoOld = persistentProrroga.getProrrogaTrabajo();
            TrabajoGrado prorrogaTrabajoNew = prorroga.getProrrogaTrabajo();
            if (prorrogaPlazoEntregaNew != null) {
                prorrogaPlazoEntregaNew = em.getReference(prorrogaPlazoEntregaNew.getClass(), prorrogaPlazoEntregaNew.getPlazoId());
                prorroga.setProrrogaPlazoEntrega(prorrogaPlazoEntregaNew);
            }
            if (prorrogaSolicitanteNew != null) {
                prorrogaSolicitanteNew = em.getReference(prorrogaSolicitanteNew.getClass(), prorrogaSolicitanteNew.getEstudianteCodigo());
                prorroga.setProrrogaSolicitante(prorrogaSolicitanteNew);
            }
            if (prorrogaTrabajoNew != null) {
                prorrogaTrabajoNew = em.getReference(prorrogaTrabajoNew.getClass(), prorrogaTrabajoNew.getTgId());
                prorroga.setProrrogaTrabajo(prorrogaTrabajoNew);
            }
            prorroga = em.merge(prorroga);
            if (prorrogaPlazoEntregaOld != null && !prorrogaPlazoEntregaOld.equals(prorrogaPlazoEntregaNew)) {
                prorrogaPlazoEntregaOld.getProrrogaCollection().remove(prorroga);
                prorrogaPlazoEntregaOld = em.merge(prorrogaPlazoEntregaOld);
            }
            if (prorrogaPlazoEntregaNew != null && !prorrogaPlazoEntregaNew.equals(prorrogaPlazoEntregaOld)) {
                prorrogaPlazoEntregaNew.getProrrogaCollection().add(prorroga);
                prorrogaPlazoEntregaNew = em.merge(prorrogaPlazoEntregaNew);
            }
            if (prorrogaSolicitanteOld != null && !prorrogaSolicitanteOld.equals(prorrogaSolicitanteNew)) {
                prorrogaSolicitanteOld.getProrrogaCollection().remove(prorroga);
                prorrogaSolicitanteOld = em.merge(prorrogaSolicitanteOld);
            }
            if (prorrogaSolicitanteNew != null && !prorrogaSolicitanteNew.equals(prorrogaSolicitanteOld)) {
                prorrogaSolicitanteNew.getProrrogaCollection().add(prorroga);
                prorrogaSolicitanteNew = em.merge(prorrogaSolicitanteNew);
            }
            if (prorrogaTrabajoOld != null && !prorrogaTrabajoOld.equals(prorrogaTrabajoNew)) {
                prorrogaTrabajoOld.getProrrogaCollection().remove(prorroga);
                prorrogaTrabajoOld = em.merge(prorrogaTrabajoOld);
            }
            if (prorrogaTrabajoNew != null && !prorrogaTrabajoNew.equals(prorrogaTrabajoOld)) {
                prorrogaTrabajoNew.getProrrogaCollection().add(prorroga);
                prorrogaTrabajoNew = em.merge(prorrogaTrabajoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prorroga.getProrrogaId();
                if (findProrroga(id) == null) {
                    throw new NonexistentEntityException("The prorroga with id " + id + " no longer exists.");
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
            Prorroga prorroga;
            try {
                prorroga = em.getReference(Prorroga.class, id);
                prorroga.getProrrogaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prorroga with id " + id + " no longer exists.", enfe);
            }
            Plazo prorrogaPlazoEntrega = prorroga.getProrrogaPlazoEntrega();
            if (prorrogaPlazoEntrega != null) {
                prorrogaPlazoEntrega.getProrrogaCollection().remove(prorroga);
                prorrogaPlazoEntrega = em.merge(prorrogaPlazoEntrega);
            }
            Estudiante prorrogaSolicitante = prorroga.getProrrogaSolicitante();
            if (prorrogaSolicitante != null) {
                prorrogaSolicitante.getProrrogaCollection().remove(prorroga);
                prorrogaSolicitante = em.merge(prorrogaSolicitante);
            }
            TrabajoGrado prorrogaTrabajo = prorroga.getProrrogaTrabajo();
            if (prorrogaTrabajo != null) {
                prorrogaTrabajo.getProrrogaCollection().remove(prorroga);
                prorrogaTrabajo = em.merge(prorrogaTrabajo);
            }
            em.remove(prorroga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prorroga> findProrrogaEntities() {
        return findProrrogaEntities(true, -1, -1);
    }

    public List<Prorroga> findProrrogaEntities(int maxResults, int firstResult) {
        return findProrrogaEntities(false, maxResults, firstResult);
    }

    private List<Prorroga> findProrrogaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prorroga.class));
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

    public Prorroga findProrroga(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prorroga.class, id);
        } finally {
            em.close();
        }
    }

    public int getProrrogaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prorroga> rt = cq.from(Prorroga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
