/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import controlers.exceptions.PreexistingEntityException;
import entities.Estudiante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Persona;
import entities.Programa;
import entities.TrabajoGrado;
import java.util.ArrayList;
import java.util.Collection;
import entities.Prorroga;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) throws PreexistingEntityException, Exception {
        if (estudiante.getTrabajoGradoCollection() == null) {
            estudiante.setTrabajoGradoCollection(new ArrayList<TrabajoGrado>());
        }
        if (estudiante.getProrrogaCollection() == null) {
            estudiante.setProrrogaCollection(new ArrayList<Prorroga>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona estudiantePersona = estudiante.getEstudiantePersona();
            if (estudiantePersona != null) {
                estudiantePersona = em.getReference(estudiantePersona.getClass(), estudiantePersona.getPersonaIdentificacion());
                estudiante.setEstudiantePersona(estudiantePersona);
            }
            Programa estudiantePrograma = estudiante.getEstudiantePrograma();
            if (estudiantePrograma != null) {
                estudiantePrograma = em.getReference(estudiantePrograma.getClass(), estudiantePrograma.getProgramaCodigo());
                estudiante.setEstudiantePrograma(estudiantePrograma);
            }
            Collection<TrabajoGrado> attachedTrabajoGradoCollection = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGradoToAttach : estudiante.getTrabajoGradoCollection()) {
                trabajoGradoCollectionTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionTrabajoGradoToAttach.getClass(), trabajoGradoCollectionTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollection.add(trabajoGradoCollectionTrabajoGradoToAttach);
            }
            estudiante.setTrabajoGradoCollection(attachedTrabajoGradoCollection);
            Collection<Prorroga> attachedProrrogaCollection = new ArrayList<Prorroga>();
            for (Prorroga prorrogaCollectionProrrogaToAttach : estudiante.getProrrogaCollection()) {
                prorrogaCollectionProrrogaToAttach = em.getReference(prorrogaCollectionProrrogaToAttach.getClass(), prorrogaCollectionProrrogaToAttach.getProrrogaId());
                attachedProrrogaCollection.add(prorrogaCollectionProrrogaToAttach);
            }
            estudiante.setProrrogaCollection(attachedProrrogaCollection);
            em.persist(estudiante);
            if (estudiantePersona != null) {
                estudiantePersona.getEstudianteCollection().add(estudiante);
                estudiantePersona = em.merge(estudiantePersona);
            }
            if (estudiantePrograma != null) {
                estudiantePrograma.getEstudianteCollection().add(estudiante);
                estudiantePrograma = em.merge(estudiantePrograma);
            }
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : estudiante.getTrabajoGradoCollection()) {
                trabajoGradoCollectionTrabajoGrado.getEstudianteCollection().add(estudiante);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
            }
            for (Prorroga prorrogaCollectionProrroga : estudiante.getProrrogaCollection()) {
                Estudiante oldProrrogaSolicitanteOfProrrogaCollectionProrroga = prorrogaCollectionProrroga.getProrrogaSolicitante();
                prorrogaCollectionProrroga.setProrrogaSolicitante(estudiante);
                prorrogaCollectionProrroga = em.merge(prorrogaCollectionProrroga);
                if (oldProrrogaSolicitanteOfProrrogaCollectionProrroga != null) {
                    oldProrrogaSolicitanteOfProrrogaCollectionProrroga.getProrrogaCollection().remove(prorrogaCollectionProrroga);
                    oldProrrogaSolicitanteOfProrrogaCollectionProrroga = em.merge(oldProrrogaSolicitanteOfProrrogaCollectionProrroga);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante(estudiante.getEstudianteCodigo()) != null) {
                throw new PreexistingEntityException("Estudiante " + estudiante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getEstudianteCodigo());
            Persona estudiantePersonaOld = persistentEstudiante.getEstudiantePersona();
            Persona estudiantePersonaNew = estudiante.getEstudiantePersona();
            Programa estudianteProgramaOld = persistentEstudiante.getEstudiantePrograma();
            Programa estudianteProgramaNew = estudiante.getEstudiantePrograma();
            Collection<TrabajoGrado> trabajoGradoCollectionOld = persistentEstudiante.getTrabajoGradoCollection();
            Collection<TrabajoGrado> trabajoGradoCollectionNew = estudiante.getTrabajoGradoCollection();
            Collection<Prorroga> prorrogaCollectionOld = persistentEstudiante.getProrrogaCollection();
            Collection<Prorroga> prorrogaCollectionNew = estudiante.getProrrogaCollection();
            List<String> illegalOrphanMessages = null;
            for (Prorroga prorrogaCollectionOldProrroga : prorrogaCollectionOld) {
                if (!prorrogaCollectionNew.contains(prorrogaCollectionOldProrroga)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prorroga " + prorrogaCollectionOldProrroga + " since its prorrogaSolicitante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudiantePersonaNew != null) {
                estudiantePersonaNew = em.getReference(estudiantePersonaNew.getClass(), estudiantePersonaNew.getPersonaIdentificacion());
                estudiante.setEstudiantePersona(estudiantePersonaNew);
            }
            if (estudianteProgramaNew != null) {
                estudianteProgramaNew = em.getReference(estudianteProgramaNew.getClass(), estudianteProgramaNew.getProgramaCodigo());
                estudiante.setEstudiantePrograma(estudianteProgramaNew);
            }
            Collection<TrabajoGrado> attachedTrabajoGradoCollectionNew = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGradoToAttach : trabajoGradoCollectionNew) {
                trabajoGradoCollectionNewTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionNewTrabajoGradoToAttach.getClass(), trabajoGradoCollectionNewTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollectionNew.add(trabajoGradoCollectionNewTrabajoGradoToAttach);
            }
            trabajoGradoCollectionNew = attachedTrabajoGradoCollectionNew;
            estudiante.setTrabajoGradoCollection(trabajoGradoCollectionNew);
            Collection<Prorroga> attachedProrrogaCollectionNew = new ArrayList<Prorroga>();
            for (Prorroga prorrogaCollectionNewProrrogaToAttach : prorrogaCollectionNew) {
                prorrogaCollectionNewProrrogaToAttach = em.getReference(prorrogaCollectionNewProrrogaToAttach.getClass(), prorrogaCollectionNewProrrogaToAttach.getProrrogaId());
                attachedProrrogaCollectionNew.add(prorrogaCollectionNewProrrogaToAttach);
            }
            prorrogaCollectionNew = attachedProrrogaCollectionNew;
            estudiante.setProrrogaCollection(prorrogaCollectionNew);
            estudiante = em.merge(estudiante);
            if (estudiantePersonaOld != null && !estudiantePersonaOld.equals(estudiantePersonaNew)) {
                estudiantePersonaOld.getEstudianteCollection().remove(estudiante);
                estudiantePersonaOld = em.merge(estudiantePersonaOld);
            }
            if (estudiantePersonaNew != null && !estudiantePersonaNew.equals(estudiantePersonaOld)) {
                estudiantePersonaNew.getEstudianteCollection().add(estudiante);
                estudiantePersonaNew = em.merge(estudiantePersonaNew);
            }
            if (estudianteProgramaOld != null && !estudianteProgramaOld.equals(estudianteProgramaNew)) {
                estudianteProgramaOld.getEstudianteCollection().remove(estudiante);
                estudianteProgramaOld = em.merge(estudianteProgramaOld);
            }
            if (estudianteProgramaNew != null && !estudianteProgramaNew.equals(estudianteProgramaOld)) {
                estudianteProgramaNew.getEstudianteCollection().add(estudiante);
                estudianteProgramaNew = em.merge(estudianteProgramaNew);
            }
            for (TrabajoGrado trabajoGradoCollectionOldTrabajoGrado : trabajoGradoCollectionOld) {
                if (!trabajoGradoCollectionNew.contains(trabajoGradoCollectionOldTrabajoGrado)) {
                    trabajoGradoCollectionOldTrabajoGrado.getEstudianteCollection().remove(estudiante);
                    trabajoGradoCollectionOldTrabajoGrado = em.merge(trabajoGradoCollectionOldTrabajoGrado);
                }
            }
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGrado : trabajoGradoCollectionNew) {
                if (!trabajoGradoCollectionOld.contains(trabajoGradoCollectionNewTrabajoGrado)) {
                    trabajoGradoCollectionNewTrabajoGrado.getEstudianteCollection().add(estudiante);
                    trabajoGradoCollectionNewTrabajoGrado = em.merge(trabajoGradoCollectionNewTrabajoGrado);
                }
            }
            for (Prorroga prorrogaCollectionNewProrroga : prorrogaCollectionNew) {
                if (!prorrogaCollectionOld.contains(prorrogaCollectionNewProrroga)) {
                    Estudiante oldProrrogaSolicitanteOfProrrogaCollectionNewProrroga = prorrogaCollectionNewProrroga.getProrrogaSolicitante();
                    prorrogaCollectionNewProrroga.setProrrogaSolicitante(estudiante);
                    prorrogaCollectionNewProrroga = em.merge(prorrogaCollectionNewProrroga);
                    if (oldProrrogaSolicitanteOfProrrogaCollectionNewProrroga != null && !oldProrrogaSolicitanteOfProrrogaCollectionNewProrroga.equals(estudiante)) {
                        oldProrrogaSolicitanteOfProrrogaCollectionNewProrroga.getProrrogaCollection().remove(prorrogaCollectionNewProrroga);
                        oldProrrogaSolicitanteOfProrrogaCollectionNewProrroga = em.merge(oldProrrogaSolicitanteOfProrrogaCollectionNewProrroga);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudiante.getEstudianteCodigo();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getEstudianteCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Prorroga> prorrogaCollectionOrphanCheck = estudiante.getProrrogaCollection();
            for (Prorroga prorrogaCollectionOrphanCheckProrroga : prorrogaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Prorroga " + prorrogaCollectionOrphanCheckProrroga + " in its prorrogaCollection field has a non-nullable prorrogaSolicitante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona estudiantePersona = estudiante.getEstudiantePersona();
            if (estudiantePersona != null) {
                estudiantePersona.getEstudianteCollection().remove(estudiante);
                estudiantePersona = em.merge(estudiantePersona);
            }
            Programa estudiantePrograma = estudiante.getEstudiantePrograma();
            if (estudiantePrograma != null) {
                estudiantePrograma.getEstudianteCollection().remove(estudiante);
                estudiantePrograma = em.merge(estudiantePrograma);
            }
            Collection<TrabajoGrado> trabajoGradoCollection = estudiante.getTrabajoGradoCollection();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : trabajoGradoCollection) {
                trabajoGradoCollectionTrabajoGrado.getEstudianteCollection().remove(estudiante);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
