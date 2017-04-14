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
import entities.Estudiante;
import entities.Programa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author daniel
 */
public class ProgramaJpaController implements Serializable {

    public ProgramaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programa programa) throws PreexistingEntityException, Exception {
        if (programa.getEstudianteCollection() == null) {
            programa.setEstudianteCollection(new ArrayList<Estudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionEstudianteToAttach : programa.getEstudianteCollection()) {
                estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getEstudianteCodigo());
                attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
            }
            programa.setEstudianteCollection(attachedEstudianteCollection);
            em.persist(programa);
            for (Estudiante estudianteCollectionEstudiante : programa.getEstudianteCollection()) {
                Programa oldEstudianteProgramaOfEstudianteCollectionEstudiante = estudianteCollectionEstudiante.getEstudiantePrograma();
                estudianteCollectionEstudiante.setEstudiantePrograma(programa);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
                if (oldEstudianteProgramaOfEstudianteCollectionEstudiante != null) {
                    oldEstudianteProgramaOfEstudianteCollectionEstudiante.getEstudianteCollection().remove(estudianteCollectionEstudiante);
                    oldEstudianteProgramaOfEstudianteCollectionEstudiante = em.merge(oldEstudianteProgramaOfEstudianteCollectionEstudiante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrograma(programa.getProgramaCodigo()) != null) {
                throw new PreexistingEntityException("Programa " + programa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programa programa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programa persistentPrograma = em.find(Programa.class, programa.getProgramaCodigo());
            Collection<Estudiante> estudianteCollectionOld = persistentPrograma.getEstudianteCollection();
            Collection<Estudiante> estudianteCollectionNew = programa.getEstudianteCollection();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
                if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteCollectionOldEstudiante + " since its estudiantePrograma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Estudiante> attachedEstudianteCollectionNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionNewEstudianteToAttach : estudianteCollectionNew) {
                estudianteCollectionNewEstudianteToAttach = em.getReference(estudianteCollectionNewEstudianteToAttach.getClass(), estudianteCollectionNewEstudianteToAttach.getEstudianteCodigo());
                attachedEstudianteCollectionNew.add(estudianteCollectionNewEstudianteToAttach);
            }
            estudianteCollectionNew = attachedEstudianteCollectionNew;
            programa.setEstudianteCollection(estudianteCollectionNew);
            programa = em.merge(programa);
            for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
                if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
                    Programa oldEstudianteProgramaOfEstudianteCollectionNewEstudiante = estudianteCollectionNewEstudiante.getEstudiantePrograma();
                    estudianteCollectionNewEstudiante.setEstudiantePrograma(programa);
                    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
                    if (oldEstudianteProgramaOfEstudianteCollectionNewEstudiante != null && !oldEstudianteProgramaOfEstudianteCollectionNewEstudiante.equals(programa)) {
                        oldEstudianteProgramaOfEstudianteCollectionNewEstudiante.getEstudianteCollection().remove(estudianteCollectionNewEstudiante);
                        oldEstudianteProgramaOfEstudianteCollectionNewEstudiante = em.merge(oldEstudianteProgramaOfEstudianteCollectionNewEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = programa.getProgramaCodigo();
                if (findPrograma(id) == null) {
                    throw new NonexistentEntityException("The programa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programa programa;
            try {
                programa = em.getReference(Programa.class, id);
                programa.getProgramaCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Estudiante> estudianteCollectionOrphanCheck = programa.getEstudianteCollection();
            for (Estudiante estudianteCollectionOrphanCheckEstudiante : estudianteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Programa (" + programa + ") cannot be destroyed since the Estudiante " + estudianteCollectionOrphanCheckEstudiante + " in its estudianteCollection field has a non-nullable estudiantePrograma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(programa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programa> findProgramaEntities() {
        return findProgramaEntities(true, -1, -1);
    }

    public List<Programa> findProgramaEntities(int maxResults, int firstResult) {
        return findProgramaEntities(false, maxResults, firstResult);
    }

    private List<Programa> findProgramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programa.class));
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

    public Programa findPrograma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programa.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programa> rt = cq.from(Programa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
