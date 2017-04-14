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
import entities.TrabajoGrado;
import java.util.ArrayList;
import java.util.Collection;
import entities.Estudiante;
import entities.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        if (persona.getTrabajoGradoCollection() == null) {
            persona.setTrabajoGradoCollection(new ArrayList<TrabajoGrado>());
        }
        if (persona.getEstudianteCollection() == null) {
            persona.setEstudianteCollection(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TrabajoGrado> attachedTrabajoGradoCollection = new ArrayList<TrabajoGrado>();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGradoToAttach : persona.getTrabajoGradoCollection()) {
                trabajoGradoCollectionTrabajoGradoToAttach = em.getReference(trabajoGradoCollectionTrabajoGradoToAttach.getClass(), trabajoGradoCollectionTrabajoGradoToAttach.getTgId());
                attachedTrabajoGradoCollection.add(trabajoGradoCollectionTrabajoGradoToAttach);
            }
            persona.setTrabajoGradoCollection(attachedTrabajoGradoCollection);
            Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionEstudianteToAttach : persona.getEstudianteCollection()) {
                estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getEstudianteCodigo());
                attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
            }
            persona.setEstudianteCollection(attachedEstudianteCollection);
            em.persist(persona);
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : persona.getTrabajoGradoCollection()) {
                trabajoGradoCollectionTrabajoGrado.getPersonaCollection().add(persona);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
            }
            for (Estudiante estudianteCollectionEstudiante : persona.getEstudianteCollection()) {
                Persona oldEstudiantePersonaOfEstudianteCollectionEstudiante = estudianteCollectionEstudiante.getEstudiantePersona();
                estudianteCollectionEstudiante.setEstudiantePersona(persona);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
                if (oldEstudiantePersonaOfEstudianteCollectionEstudiante != null) {
                    oldEstudiantePersonaOfEstudianteCollectionEstudiante.getEstudianteCollection().remove(estudianteCollectionEstudiante);
                    oldEstudiantePersonaOfEstudianteCollectionEstudiante = em.merge(oldEstudiantePersonaOfEstudianteCollectionEstudiante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getPersonaIdentificacion()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getPersonaIdentificacion());
            Collection<TrabajoGrado> trabajoGradoCollectionOld = persistentPersona.getTrabajoGradoCollection();
            Collection<TrabajoGrado> trabajoGradoCollectionNew = persona.getTrabajoGradoCollection();
            Collection<Estudiante> estudianteCollectionOld = persistentPersona.getEstudianteCollection();
            Collection<Estudiante> estudianteCollectionNew = persona.getEstudianteCollection();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
                if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteCollectionOldEstudiante + " since its estudiantePersona field is not nullable.");
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
            persona.setTrabajoGradoCollection(trabajoGradoCollectionNew);
            Collection<Estudiante> attachedEstudianteCollectionNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionNewEstudianteToAttach : estudianteCollectionNew) {
                estudianteCollectionNewEstudianteToAttach = em.getReference(estudianteCollectionNewEstudianteToAttach.getClass(), estudianteCollectionNewEstudianteToAttach.getEstudianteCodigo());
                attachedEstudianteCollectionNew.add(estudianteCollectionNewEstudianteToAttach);
            }
            estudianteCollectionNew = attachedEstudianteCollectionNew;
            persona.setEstudianteCollection(estudianteCollectionNew);
            persona = em.merge(persona);
            for (TrabajoGrado trabajoGradoCollectionOldTrabajoGrado : trabajoGradoCollectionOld) {
                if (!trabajoGradoCollectionNew.contains(trabajoGradoCollectionOldTrabajoGrado)) {
                    trabajoGradoCollectionOldTrabajoGrado.getPersonaCollection().remove(persona);
                    trabajoGradoCollectionOldTrabajoGrado = em.merge(trabajoGradoCollectionOldTrabajoGrado);
                }
            }
            for (TrabajoGrado trabajoGradoCollectionNewTrabajoGrado : trabajoGradoCollectionNew) {
                if (!trabajoGradoCollectionOld.contains(trabajoGradoCollectionNewTrabajoGrado)) {
                    trabajoGradoCollectionNewTrabajoGrado.getPersonaCollection().add(persona);
                    trabajoGradoCollectionNewTrabajoGrado = em.merge(trabajoGradoCollectionNewTrabajoGrado);
                }
            }
            for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
                if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
                    Persona oldEstudiantePersonaOfEstudianteCollectionNewEstudiante = estudianteCollectionNewEstudiante.getEstudiantePersona();
                    estudianteCollectionNewEstudiante.setEstudiantePersona(persona);
                    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
                    if (oldEstudiantePersonaOfEstudianteCollectionNewEstudiante != null && !oldEstudiantePersonaOfEstudianteCollectionNewEstudiante.equals(persona)) {
                        oldEstudiantePersonaOfEstudianteCollectionNewEstudiante.getEstudianteCollection().remove(estudianteCollectionNewEstudiante);
                        oldEstudiantePersonaOfEstudianteCollectionNewEstudiante = em.merge(oldEstudiantePersonaOfEstudianteCollectionNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getPersonaIdentificacion();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getPersonaIdentificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Estudiante> estudianteCollectionOrphanCheck = persona.getEstudianteCollection();
            for (Estudiante estudianteCollectionOrphanCheckEstudiante : estudianteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Estudiante " + estudianteCollectionOrphanCheckEstudiante + " in its estudianteCollection field has a non-nullable estudiantePersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TrabajoGrado> trabajoGradoCollection = persona.getTrabajoGradoCollection();
            for (TrabajoGrado trabajoGradoCollectionTrabajoGrado : trabajoGradoCollection) {
                trabajoGradoCollectionTrabajoGrado.getPersonaCollection().remove(persona);
                trabajoGradoCollectionTrabajoGrado = em.merge(trabajoGradoCollectionTrabajoGrado);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
