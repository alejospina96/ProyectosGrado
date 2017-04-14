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
import entities.ConceptoPlazo;
import entities.Multa;
import entities.Plazo;
import entities.Propuesta;
import java.util.ArrayList;
import java.util.Collection;
import entities.TrabajoGrado;
import entities.Prorroga;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class PlazoJpaController implements Serializable {

    public PlazoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plazo plazo) {
        if (plazo.getPropuestaCollection() == null) {
            plazo.setPropuestaCollection(new ArrayList<Propuesta>());
        }
        if (plazo.getTrabajoGradoCollection() == null) {
            plazo.setTrabajoGradoCollection(new ArrayList<TrabajoGrado>());
        }
        if (plazo.getProrrogaCollection() == null) {
            plazo.setProrrogaCollection(new ArrayList<Prorroga>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConceptoPlazo plazoConcepto = plazo.getPlazoConcepto();
            if (plazoConcepto != null) {
                plazoConcepto = em.getReference(plazoConcepto.getClass(), plazoConcepto.getCplazoId());
                plazo.setPlazoConcepto(plazoConcepto);
            }
            Multa plazoMulta = plazo.getPlazoMulta();
            if (plazoMulta != null) {
                plazoMulta = em.getReference(plazoMulta.getClass(), plazoMulta.getIdMulta());
                plazo.setPlazoMulta(plazoMulta);
            }
            Collection<Propuesta> attachedPropuestaCollection = new ArrayList<Propuesta>();
            for (Propuesta propuestaCollectionPropuestaToAttach : plazo.getPropuestaCollection()) {
                propuestaCollectionPropuestaToAttach = em.getReference(propuestaCollectionPropuestaToAttach.getClass(), propuestaCollectionPropuestaToAttach.getPropuestaTrabajo());
                attachedPropuestaCollection.add(propuestaCollectionPropuestaToAttach);
            }
            plazo.setPropuestaCollection(attachedPropuestaCollection);
            Collection<TrabajoGrado> attachedTrabajoGradoCollection = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGradoToAttach : plazo.getTrabajoGradoCollection()) {
                trabajoGradoCollectionTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionTrabajoGradoToAttach.getClass(), trabajoGradoCollectionTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollection.add(trabajoGradoCollectionTrabajoGradoToAttach);
            }
            plazo.setTrabajoGradoCollection(attachedTrabajoGradoCollection);
            Collection<Prorroga> attachedProrrogaCollection = new ArrayList<Prorroga>();
            for (Prorroga prorrogaCollectionProrrogaToAttach : plazo.getProrrogaCollection()) {
                prorrogaCollectionProrrogaToAttach = em.getReference(prorrogaCollectionProrrogaToAttach.getClass(), prorrogaCollectionProrrogaToAttach.getProrrogaId());
                attachedProrrogaCollection.add(prorrogaCollectionProrrogaToAttach);
            }
            plazo.setProrrogaCollection(attachedProrrogaCollection);
            em.persist(plazo);
            if (plazoConcepto != null) {
                plazoConcepto.getPlazoCollection().add(plazo);
                plazoConcepto = em.merge(plazoConcepto);
            }
            if (plazoMulta != null) {
                plazoMulta.getPlazoCollection().add(plazo);
                plazoMulta = em.merge(plazoMulta);
            }
            for (Propuesta propuestaCollectionPropuesta : plazo.getPropuestaCollection()) {
                Plazo oldPropuestaPlazoCorreccionesOfPropuestaCollectionPropuesta = propuestaCollectionPropuesta.getPropuestaPlazoCorrecciones();
                propuestaCollectionPropuesta.setPropuestaPlazoCorrecciones(plazo);
                propuestaCollectionPropuesta = em.merge(propuestaCollectionPropuesta);
                if (oldPropuestaPlazoCorreccionesOfPropuestaCollectionPropuesta != null) {
                    oldPropuestaPlazoCorreccionesOfPropuestaCollectionPropuesta.getPropuestaCollection().remove(propuestaCollectionPropuesta);
                    oldPropuestaPlazoCorreccionesOfPropuestaCollectionPropuesta = em.merge(oldPropuestaPlazoCorreccionesOfPropuestaCollectionPropuesta);
                }
            }
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : plazo.getTrabajoGradoCollection()) {
                Plazo oldTgPlazoEntregaOfTrabajoGradoCollectionTrabajoGrado = trabajoGradoCollectionTrabajoGrado.getTgPlazoEntrega();
                trabajoGradoCollectionTrabajoGrado.setTgPlazoEntrega(plazo);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
                if (oldTgPlazoEntregaOfTrabajoGradoCollectionTrabajoGrado != null) {
                    oldTgPlazoEntregaOfTrabajoGradoCollectionTrabajoGrado.getTrabajoGradoCollection().remove(trabajoGradoCollectionTrabajoGrado);
                    oldTgPlazoEntregaOfTrabajoGradoCollectionTrabajoGrado = em.merge(oldTgPlazoEntregaOfTrabajoGradoCollectionTrabajoGrado);
                }
            }
            for (Prorroga prorrogaCollectionProrroga : plazo.getProrrogaCollection()) {
                Plazo oldProrrogaPlazoEntregaOfProrrogaCollectionProrroga = prorrogaCollectionProrroga.getProrrogaPlazoEntrega();
                prorrogaCollectionProrroga.setProrrogaPlazoEntrega(plazo);
                prorrogaCollectionProrroga = em.merge(prorrogaCollectionProrroga);
                if (oldProrrogaPlazoEntregaOfProrrogaCollectionProrroga != null) {
                    oldProrrogaPlazoEntregaOfProrrogaCollectionProrroga.getProrrogaCollection().remove(prorrogaCollectionProrroga);
                    oldProrrogaPlazoEntregaOfProrrogaCollectionProrroga = em.merge(oldProrrogaPlazoEntregaOfProrrogaCollectionProrroga);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plazo plazo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plazo persistentPlazo = em.find(Plazo.class, plazo.getPlazoId());
            ConceptoPlazo plazoConceptoOld = persistentPlazo.getPlazoConcepto();
            ConceptoPlazo plazoConceptoNew = plazo.getPlazoConcepto();
            Multa plazoMultaOld = persistentPlazo.getPlazoMulta();
            Multa plazoMultaNew = plazo.getPlazoMulta();
            Collection<Propuesta> propuestaCollectionOld = persistentPlazo.getPropuestaCollection();
            Collection<Propuesta> propuestaCollectionNew = plazo.getPropuestaCollection();
            Collection<TrabajoGrado> trabajoGradoCollectionOld = persistentPlazo.getTrabajoGradoCollection();
            Collection<TrabajoGrado> trabajoGradoCollectionNew = plazo.getTrabajoGradoCollection();
            Collection<Prorroga> prorrogaCollectionOld = persistentPlazo.getProrrogaCollection();
            Collection<Prorroga> prorrogaCollectionNew = plazo.getProrrogaCollection();
            if (plazoConceptoNew != null) {
                plazoConceptoNew = em.getReference(plazoConceptoNew.getClass(), plazoConceptoNew.getCplazoId());
                plazo.setPlazoConcepto(plazoConceptoNew);
            }
            if (plazoMultaNew != null) {
                plazoMultaNew = em.getReference(plazoMultaNew.getClass(), plazoMultaNew.getIdMulta());
                plazo.setPlazoMulta(plazoMultaNew);
            }
            Collection<Propuesta> attachedPropuestaCollectionNew = new ArrayList<Propuesta>();
            for (Propuesta propuestaCollectionNewPropuestaToAttach : propuestaCollectionNew) {
                propuestaCollectionNewPropuestaToAttach = em.getReference(propuestaCollectionNewPropuestaToAttach.getClass(), propuestaCollectionNewPropuestaToAttach.getPropuestaTrabajo());
                attachedPropuestaCollectionNew.add(propuestaCollectionNewPropuestaToAttach);
            }
            propuestaCollectionNew = attachedPropuestaCollectionNew;
            plazo.setPropuestaCollection(propuestaCollectionNew);
            Collection<TrabajoGrado> attachedTrabajoGradoCollectionNew = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGradoToAttach : trabajoGradoCollectionNew) {
                trabajoGradoCollectionNewTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionNewTrabajoGradoToAttach.getClass(), trabajoGradoCollectionNewTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollectionNew.add(trabajoGradoCollectionNewTrabajoGradoToAttach);
            }
            trabajoGradoCollectionNew = attachedTrabajoGradoCollectionNew;
            plazo.setTrabajoGradoCollection(trabajoGradoCollectionNew);
            Collection<Prorroga> attachedProrrogaCollectionNew = new ArrayList<Prorroga>();
            for (Prorroga prorrogaCollectionNewProrrogaToAttach : prorrogaCollectionNew) {
                prorrogaCollectionNewProrrogaToAttach = em.getReference(prorrogaCollectionNewProrrogaToAttach.getClass(), prorrogaCollectionNewProrrogaToAttach.getProrrogaId());
                attachedProrrogaCollectionNew.add(prorrogaCollectionNewProrrogaToAttach);
            }
            prorrogaCollectionNew = attachedProrrogaCollectionNew;
            plazo.setProrrogaCollection(prorrogaCollectionNew);
            plazo = em.merge(plazo);
            if (plazoConceptoOld != null && !plazoConceptoOld.equals(plazoConceptoNew)) {
                plazoConceptoOld.getPlazoCollection().remove(plazo);
                plazoConceptoOld = em.merge(plazoConceptoOld);
            }
            if (plazoConceptoNew != null && !plazoConceptoNew.equals(plazoConceptoOld)) {
                plazoConceptoNew.getPlazoCollection().add(plazo);
                plazoConceptoNew = em.merge(plazoConceptoNew);
            }
            if (plazoMultaOld != null && !plazoMultaOld.equals(plazoMultaNew)) {
                plazoMultaOld.getPlazoCollection().remove(plazo);
                plazoMultaOld = em.merge(plazoMultaOld);
            }
            if (plazoMultaNew != null && !plazoMultaNew.equals(plazoMultaOld)) {
                plazoMultaNew.getPlazoCollection().add(plazo);
                plazoMultaNew = em.merge(plazoMultaNew);
            }
            for (Propuesta propuestaCollectionOldPropuesta : propuestaCollectionOld) {
                if (!propuestaCollectionNew.contains(propuestaCollectionOldPropuesta)) {
                    propuestaCollectionOldPropuesta.setPropuestaPlazoCorrecciones(null);
                    propuestaCollectionOldPropuesta = em.merge(propuestaCollectionOldPropuesta);
                }
            }
            for (Propuesta propuestaCollectionNewPropuesta : propuestaCollectionNew) {
                if (!propuestaCollectionOld.contains(propuestaCollectionNewPropuesta)) {
                    Plazo oldPropuestaPlazoCorreccionesOfPropuestaCollectionNewPropuesta = propuestaCollectionNewPropuesta.getPropuestaPlazoCorrecciones();
                    propuestaCollectionNewPropuesta.setPropuestaPlazoCorrecciones(plazo);
                    propuestaCollectionNewPropuesta = em.merge(propuestaCollectionNewPropuesta);
                    if (oldPropuestaPlazoCorreccionesOfPropuestaCollectionNewPropuesta != null && !oldPropuestaPlazoCorreccionesOfPropuestaCollectionNewPropuesta.equals(plazo)) {
                        oldPropuestaPlazoCorreccionesOfPropuestaCollectionNewPropuesta.getPropuestaCollection().remove(propuestaCollectionNewPropuesta);
                        oldPropuestaPlazoCorreccionesOfPropuestaCollectionNewPropuesta = em.merge(oldPropuestaPlazoCorreccionesOfPropuestaCollectionNewPropuesta);
                    }
                }
            }
            for (TrabajoGrado trabajoGradoCollectionOldTrabajoGrado : trabajoGradoCollectionOld) {
                if (!trabajoGradoCollectionNew.contains(trabajoGradoCollectionOldTrabajoGrado)) {
                    trabajoGradoCollectionOldTrabajoGrado.setTgPlazoEntrega(null);
                    trabajoGradoCollectionOldTrabajoGrado = em.merge(trabajoGradoCollectionOldTrabajoGrado);
                }
            }
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGrado : trabajoGradoCollectionNew) {
                if (!trabajoGradoCollectionOld.contains(trabajoGradoCollectionNewTrabajoGrado)) {
                    Plazo oldTgPlazoEntregaOfTrabajoGradoCollectionNewTrabajoGrado = trabajoGradoCollectionNewTrabajoGrado.getTgPlazoEntrega();
                    trabajoGradoCollectionNewTrabajoGrado.setTgPlazoEntrega(plazo);
                    trabajoGradoCollectionNewTrabajoGrado = em.merge(trabajoGradoCollectionNewTrabajoGrado);
                    if (oldTgPlazoEntregaOfTrabajoGradoCollectionNewTrabajoGrado != null && !oldTgPlazoEntregaOfTrabajoGradoCollectionNewTrabajoGrado.equals(plazo)) {
                        oldTgPlazoEntregaOfTrabajoGradoCollectionNewTrabajoGrado.getTrabajoGradoCollection().remove(trabajoGradoCollectionNewTrabajoGrado);
                        oldTgPlazoEntregaOfTrabajoGradoCollectionNewTrabajoGrado = em.merge(oldTgPlazoEntregaOfTrabajoGradoCollectionNewTrabajoGrado);
                    }
                }
            }
            for (Prorroga prorrogaCollectionOldProrroga : prorrogaCollectionOld) {
                if (!prorrogaCollectionNew.contains(prorrogaCollectionOldProrroga)) {
                    prorrogaCollectionOldProrroga.setProrrogaPlazoEntrega(null);
                    prorrogaCollectionOldProrroga = em.merge(prorrogaCollectionOldProrroga);
                }
            }
            for (Prorroga prorrogaCollectionNewProrroga : prorrogaCollectionNew) {
                if (!prorrogaCollectionOld.contains(prorrogaCollectionNewProrroga)) {
                    Plazo oldProrrogaPlazoEntregaOfProrrogaCollectionNewProrroga = prorrogaCollectionNewProrroga.getProrrogaPlazoEntrega();
                    prorrogaCollectionNewProrroga.setProrrogaPlazoEntrega(plazo);
                    prorrogaCollectionNewProrroga = em.merge(prorrogaCollectionNewProrroga);
                    if (oldProrrogaPlazoEntregaOfProrrogaCollectionNewProrroga != null && !oldProrrogaPlazoEntregaOfProrrogaCollectionNewProrroga.equals(plazo)) {
                        oldProrrogaPlazoEntregaOfProrrogaCollectionNewProrroga.getProrrogaCollection().remove(prorrogaCollectionNewProrroga);
                        oldProrrogaPlazoEntregaOfProrrogaCollectionNewProrroga = em.merge(oldProrrogaPlazoEntregaOfProrrogaCollectionNewProrroga);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plazo.getPlazoId();
                if (findPlazo(id) == null) {
                    throw new NonexistentEntityException("The plazo with id " + id + " no longer exists.");
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
            Plazo plazo;
            try {
                plazo = em.getReference(Plazo.class, id);
                plazo.getPlazoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plazo with id " + id + " no longer exists.", enfe);
            }
            ConceptoPlazo plazoConcepto = plazo.getPlazoConcepto();
            if (plazoConcepto != null) {
                plazoConcepto.getPlazoCollection().remove(plazo);
                plazoConcepto = em.merge(plazoConcepto);
            }
            Multa plazoMulta = plazo.getPlazoMulta();
            if (plazoMulta != null) {
                plazoMulta.getPlazoCollection().remove(plazo);
                plazoMulta = em.merge(plazoMulta);
            }
            Collection<Propuesta> propuestaCollection = plazo.getPropuestaCollection();
            for (Propuesta propuestaCollectionPropuesta : propuestaCollection) {
                propuestaCollectionPropuesta.setPropuestaPlazoCorrecciones(null);
                propuestaCollectionPropuesta = em.merge(propuestaCollectionPropuesta);
            }
            Collection<TrabajoGrado> trabajoGradoCollection = plazo.getTrabajoGradoCollection();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : trabajoGradoCollection) {
                trabajoGradoCollectionTrabajoGrado.setTgPlazoEntrega(null);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
            }
            Collection<Prorroga> prorrogaCollection = plazo.getProrrogaCollection();
            for (Prorroga prorrogaCollectionProrroga : prorrogaCollection) {
                prorrogaCollectionProrroga.setProrrogaPlazoEntrega(null);
                prorrogaCollectionProrroga = em.merge(prorrogaCollectionProrroga);
            }
            em.remove(plazo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plazo> findPlazoEntities() {
        return findPlazoEntities(true, -1, -1);
    }

    public List<Plazo> findPlazoEntities(int maxResults, int firstResult) {
        return findPlazoEntities(false, maxResults, firstResult);
    }

    private List<Plazo> findPlazoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plazo.class));
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

    public Plazo findPlazo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plazo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlazoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plazo> rt = cq.from(Plazo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
