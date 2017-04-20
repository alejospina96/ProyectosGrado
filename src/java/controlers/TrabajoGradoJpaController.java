/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import controlers.exceptions.IllegalOrphanException;
import controlers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Propuesta;
import entities.EstadoTrabajoGrado;
import entities.ModalidadTrabajo;
import entities.Plazo;
import entities.Estudiante;
import java.util.ArrayList;
import java.util.Collection;
import entities.Persona;
import entities.Prorroga;
import entities.Observacion;
import entities.TrabajoGrado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.jpa.config.NamedQuery;

/**
 *
 * @author daniel
 */
public class TrabajoGradoJpaController implements Serializable {

    public TrabajoGradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int create(TrabajoGrado trabajoGrado) {
        int lastId = -1;
        if (trabajoGrado.getEstudianteCollection() == null) {
            trabajoGrado.setEstudianteCollection(new ArrayList<Estudiante>());
        }
        if (trabajoGrado.getPersonaCollection() == null) {
            trabajoGrado.setPersonaCollection(new ArrayList<Persona>());
        }
        if (trabajoGrado.getProrrogaCollection() == null) {
            trabajoGrado.setProrrogaCollection(new ArrayList<Prorroga>());
        }
        if (trabajoGrado.getObservacionCollection() == null) {
            trabajoGrado.setObservacionCollection(new ArrayList<Observacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuesta propuesta = trabajoGrado.getPropuesta();
            if (propuesta != null) {
                propuesta = em.getReference(propuesta.getClass(), propuesta.getPropuestaTrabajo());
                trabajoGrado.setPropuesta(propuesta);
            }
            EstadoTrabajoGrado tgConceptoEstado = trabajoGrado.getTgConceptoEstado();
            if (tgConceptoEstado != null) {
                tgConceptoEstado = em.getReference(tgConceptoEstado.getClass(), tgConceptoEstado.getEpgId());
                trabajoGrado.setTgConceptoEstado(tgConceptoEstado);
            }
            ModalidadTrabajo tgModalidad = trabajoGrado.getTgModalidad();
            if (tgModalidad != null) {
                tgModalidad = em.getReference(tgModalidad.getClass(), tgModalidad.getModalidadId());
                trabajoGrado.setTgModalidad(tgModalidad);
            }
            Plazo tgPlazoEntrega = trabajoGrado.getTgPlazoEntrega();
            if (tgPlazoEntrega != null) {
                tgPlazoEntrega = em.getReference(tgPlazoEntrega.getClass(), tgPlazoEntrega.getPlazoId());
                trabajoGrado.setTgPlazoEntrega(tgPlazoEntrega);
            }
            Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionEstudianteToAttach : trabajoGrado.getEstudianteCollection()) {
                estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getEstudianteCodigo());
                attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
            }
            trabajoGrado.setEstudianteCollection(attachedEstudianteCollection);
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : trabajoGrado.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getPersonaIdentificacion());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            trabajoGrado.setPersonaCollection(attachedPersonaCollection);
            Collection<Prorroga> attachedProrrogaCollection = new ArrayList<Prorroga>();
            for (Prorroga prorrogaCollectionProrrogaToAttach : trabajoGrado.getProrrogaCollection()) {
                prorrogaCollectionProrrogaToAttach = em.getReference(prorrogaCollectionProrrogaToAttach.getClass(), prorrogaCollectionProrrogaToAttach.getProrrogaId());
                attachedProrrogaCollection.add(prorrogaCollectionProrrogaToAttach);
            }
            trabajoGrado.setProrrogaCollection(attachedProrrogaCollection);
            Collection<Observacion> attachedObservacionCollection = new ArrayList<Observacion>();
            for (Observacion observacionCollectionObservacionToAttach : trabajoGrado.getObservacionCollection()) {
                observacionCollectionObservacionToAttach = em.getReference(observacionCollectionObservacionToAttach.getClass(), observacionCollectionObservacionToAttach.getOpgId());
                attachedObservacionCollection.add(observacionCollectionObservacionToAttach);
            }
            trabajoGrado.setObservacionCollection(attachedObservacionCollection);
            em.persist(trabajoGrado);
            if (propuesta != null) {
                TrabajoGrado oldTrabajoGradoOfPropuesta = propuesta.getTrabajoGrado();
                if (oldTrabajoGradoOfPropuesta != null) {
                    oldTrabajoGradoOfPropuesta.setPropuesta(null);
                    oldTrabajoGradoOfPropuesta = em.merge(oldTrabajoGradoOfPropuesta);
                }
                propuesta.setTrabajoGrado(trabajoGrado);
                propuesta = em.merge(propuesta);
            }
            if (tgConceptoEstado != null) {
                tgConceptoEstado.getTrabajoGradoCollection().add(trabajoGrado);
                tgConceptoEstado = em.merge(tgConceptoEstado);
            }
            if (tgModalidad != null) {
                tgModalidad.getTrabajoGradoCollection().add(trabajoGrado);
                tgModalidad = em.merge(tgModalidad);
            }
            if (tgPlazoEntrega != null) {
                tgPlazoEntrega.getTrabajoGradoCollection().add(trabajoGrado);
                tgPlazoEntrega = em.merge(tgPlazoEntrega);
            }
            for (Estudiante estudianteCollectionEstudiante : trabajoGrado.getEstudianteCollection()) {
                estudianteCollectionEstudiante.getTrabajoGradoCollection().add(trabajoGrado);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
            }
            for (Persona personaCollectionPersona : trabajoGrado.getPersonaCollection()) {
                personaCollectionPersona.getTrabajoGradoCollection().add(trabajoGrado);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            for (Prorroga prorrogaCollectionProrroga : trabajoGrado.getProrrogaCollection()) {
                TrabajoGrado oldProrrogaTrabajoOfProrrogaCollectionProrroga = prorrogaCollectionProrroga.getProrrogaTrabajo();
                prorrogaCollectionProrroga.setProrrogaTrabajo(trabajoGrado);
                prorrogaCollectionProrroga = em.merge(prorrogaCollectionProrroga);
                if (oldProrrogaTrabajoOfProrrogaCollectionProrroga != null) {
                    oldProrrogaTrabajoOfProrrogaCollectionProrroga.getProrrogaCollection().remove(prorrogaCollectionProrroga);
                    oldProrrogaTrabajoOfProrrogaCollectionProrroga = em.merge(oldProrrogaTrabajoOfProrrogaCollectionProrroga);
                }
            }
            for (Observacion observacionCollectionObservacion : trabajoGrado.getObservacionCollection()) {
                TrabajoGrado oldOpgTrabajoOfObservacionCollectionObservacion = observacionCollectionObservacion.getOpgTrabajo();
                observacionCollectionObservacion.setOpgTrabajo(trabajoGrado);
                observacionCollectionObservacion = em.merge(observacionCollectionObservacion);
                if (oldOpgTrabajoOfObservacionCollectionObservacion != null) {
                    oldOpgTrabajoOfObservacionCollectionObservacion.getObservacionCollection().remove(observacionCollectionObservacion);
                    oldOpgTrabajoOfObservacionCollectionObservacion = em.merge(oldOpgTrabajoOfObservacionCollectionObservacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                lastId = em.createQuery("SELECT MAX(t.tgId) FROM TrabajoGrado t", Integer.class).getSingleResult();

                em.close();
            }
        }
        return lastId;
    }

    public void edit(TrabajoGrado trabajoGrado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TrabajoGrado persistentTrabajoGrado = em.find(TrabajoGrado.class, trabajoGrado.getTgId());
            Propuesta propuestaOld = persistentTrabajoGrado.getPropuesta();
            Propuesta propuestaNew = trabajoGrado.getPropuesta();
            EstadoTrabajoGrado tgConceptoEstadoOld = persistentTrabajoGrado.getTgConceptoEstado();
            EstadoTrabajoGrado tgConceptoEstadoNew = trabajoGrado.getTgConceptoEstado();
            ModalidadTrabajo tgModalidadOld = persistentTrabajoGrado.getTgModalidad();
            ModalidadTrabajo tgModalidadNew = trabajoGrado.getTgModalidad();
            Plazo tgPlazoEntregaOld = persistentTrabajoGrado.getTgPlazoEntrega();
            Plazo tgPlazoEntregaNew = trabajoGrado.getTgPlazoEntrega();
            Collection<Estudiante> estudianteCollectionOld = persistentTrabajoGrado.getEstudianteCollection();
            Collection<Estudiante> estudianteCollectionNew = trabajoGrado.getEstudianteCollection();
            Collection<Persona> personaCollectionOld = persistentTrabajoGrado.getPersonaCollection();
            Collection<Persona> personaCollectionNew = trabajoGrado.getPersonaCollection();
            Collection<Prorroga> prorrogaCollectionOld = persistentTrabajoGrado.getProrrogaCollection();
            Collection<Prorroga> prorrogaCollectionNew = trabajoGrado.getProrrogaCollection();
            Collection<Observacion> observacionCollectionOld = persistentTrabajoGrado.getObservacionCollection();
            Collection<Observacion> observacionCollectionNew = trabajoGrado.getObservacionCollection();
            List<String> illegalOrphanMessages = null;
            if (propuestaOld != null && !propuestaOld.equals(propuestaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Propuesta " + propuestaOld + " since its trabajoGrado field is not nullable.");
            }
            for (Prorroga prorrogaCollectionOldProrroga : prorrogaCollectionOld) {
                if (!prorrogaCollectionNew.contains(prorrogaCollectionOldProrroga)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prorroga " + prorrogaCollectionOldProrroga + " since its prorrogaTrabajo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (propuestaNew != null) {
                propuestaNew = em.getReference(propuestaNew.getClass(), propuestaNew.getPropuestaTrabajo());
                trabajoGrado.setPropuesta(propuestaNew);
            }
            if (tgConceptoEstadoNew != null) {
                tgConceptoEstadoNew = em.getReference(tgConceptoEstadoNew.getClass(), tgConceptoEstadoNew.getEpgId());
                trabajoGrado.setTgConceptoEstado(tgConceptoEstadoNew);
            }
            if (tgModalidadNew != null) {
                tgModalidadNew = em.getReference(tgModalidadNew.getClass(), tgModalidadNew.getModalidadId());
                trabajoGrado.setTgModalidad(tgModalidadNew);
            }
            if (tgPlazoEntregaNew != null) {
                tgPlazoEntregaNew = em.getReference(tgPlazoEntregaNew.getClass(), tgPlazoEntregaNew.getPlazoId());
                trabajoGrado.setTgPlazoEntrega(tgPlazoEntregaNew);
            }
            Collection<Estudiante> attachedEstudianteCollectionNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionNewEstudianteToAttach : estudianteCollectionNew) {
                estudianteCollectionNewEstudianteToAttach = em.getReference(estudianteCollectionNewEstudianteToAttach.getClass(), estudianteCollectionNewEstudianteToAttach.getEstudianteCodigo());
                attachedEstudianteCollectionNew.add(estudianteCollectionNewEstudianteToAttach);
            }
            estudianteCollectionNew = attachedEstudianteCollectionNew;
            trabajoGrado.setEstudianteCollection(estudianteCollectionNew);
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getPersonaIdentificacion());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            trabajoGrado.setPersonaCollection(personaCollectionNew);
            Collection<Prorroga> attachedProrrogaCollectionNew = new ArrayList<Prorroga>();
            for (Prorroga prorrogaCollectionNewProrrogaToAttach : prorrogaCollectionNew) {
                prorrogaCollectionNewProrrogaToAttach = em.getReference(prorrogaCollectionNewProrrogaToAttach.getClass(), prorrogaCollectionNewProrrogaToAttach.getProrrogaId());
                attachedProrrogaCollectionNew.add(prorrogaCollectionNewProrrogaToAttach);
            }
            prorrogaCollectionNew = attachedProrrogaCollectionNew;
            trabajoGrado.setProrrogaCollection(prorrogaCollectionNew);
            Collection<Observacion> attachedObservacionCollectionNew = new ArrayList<Observacion>();
            for (Observacion observacionCollectionNewObservacionToAttach : observacionCollectionNew) {
                observacionCollectionNewObservacionToAttach = em.getReference(observacionCollectionNewObservacionToAttach.getClass(), observacionCollectionNewObservacionToAttach.getOpgId());
                attachedObservacionCollectionNew.add(observacionCollectionNewObservacionToAttach);
            }
            observacionCollectionNew = attachedObservacionCollectionNew;
            trabajoGrado.setObservacionCollection(observacionCollectionNew);
            trabajoGrado = em.merge(trabajoGrado);
            if (propuestaNew != null && !propuestaNew.equals(propuestaOld)) {
                TrabajoGrado oldTrabajoGradoOfPropuesta = propuestaNew.getTrabajoGrado();
                if (oldTrabajoGradoOfPropuesta != null) {
                    oldTrabajoGradoOfPropuesta.setPropuesta(null);
                    oldTrabajoGradoOfPropuesta = em.merge(oldTrabajoGradoOfPropuesta);
                }
                propuestaNew.setTrabajoGrado(trabajoGrado);
                propuestaNew = em.merge(propuestaNew);
            }
            if (tgConceptoEstadoOld != null && !tgConceptoEstadoOld.equals(tgConceptoEstadoNew)) {
                tgConceptoEstadoOld.getTrabajoGradoCollection().remove(trabajoGrado);
                tgConceptoEstadoOld = em.merge(tgConceptoEstadoOld);
            }
            if (tgConceptoEstadoNew != null && !tgConceptoEstadoNew.equals(tgConceptoEstadoOld)) {
                tgConceptoEstadoNew.getTrabajoGradoCollection().add(trabajoGrado);
                tgConceptoEstadoNew = em.merge(tgConceptoEstadoNew);
            }
            if (tgModalidadOld != null && !tgModalidadOld.equals(tgModalidadNew)) {
                tgModalidadOld.getTrabajoGradoCollection().remove(trabajoGrado);
                tgModalidadOld = em.merge(tgModalidadOld);
            }
            if (tgModalidadNew != null && !tgModalidadNew.equals(tgModalidadOld)) {
                tgModalidadNew.getTrabajoGradoCollection().add(trabajoGrado);
                tgModalidadNew = em.merge(tgModalidadNew);
            }
            if (tgPlazoEntregaOld != null && !tgPlazoEntregaOld.equals(tgPlazoEntregaNew)) {
                tgPlazoEntregaOld.getTrabajoGradoCollection().remove(trabajoGrado);
                tgPlazoEntregaOld = em.merge(tgPlazoEntregaOld);
            }
            if (tgPlazoEntregaNew != null && !tgPlazoEntregaNew.equals(tgPlazoEntregaOld)) {
                tgPlazoEntregaNew.getTrabajoGradoCollection().add(trabajoGrado);
                tgPlazoEntregaNew = em.merge(tgPlazoEntregaNew);
            }
            for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
                if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
                    estudianteCollectionOldEstudiante.getTrabajoGradoCollection().remove(trabajoGrado);
                    estudianteCollectionOldEstudiante = em.merge(estudianteCollectionOldEstudiante);
                }
            }
            for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
                if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
                    estudianteCollectionNewEstudiante.getTrabajoGradoCollection().add(trabajoGrado);
                    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
                }
            }
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    personaCollectionOldPersona.getTrabajoGradoCollection().remove(trabajoGrado);
                    personaCollectionOldPersona = em.merge(personaCollectionOldPersona);
                }
            }
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    personaCollectionNewPersona.getTrabajoGradoCollection().add(trabajoGrado);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                }
            }
            for (Prorroga prorrogaCollectionNewProrroga : prorrogaCollectionNew) {
                if (!prorrogaCollectionOld.contains(prorrogaCollectionNewProrroga)) {
                    TrabajoGrado oldProrrogaTrabajoOfProrrogaCollectionNewProrroga = prorrogaCollectionNewProrroga.getProrrogaTrabajo();
                    prorrogaCollectionNewProrroga.setProrrogaTrabajo(trabajoGrado);
                    prorrogaCollectionNewProrroga = em.merge(prorrogaCollectionNewProrroga);
                    if (oldProrrogaTrabajoOfProrrogaCollectionNewProrroga != null && !oldProrrogaTrabajoOfProrrogaCollectionNewProrroga.equals(trabajoGrado)) {
                        oldProrrogaTrabajoOfProrrogaCollectionNewProrroga.getProrrogaCollection().remove(prorrogaCollectionNewProrroga);
                        oldProrrogaTrabajoOfProrrogaCollectionNewProrroga = em.merge(oldProrrogaTrabajoOfProrrogaCollectionNewProrroga);
                    }
                }
            }
            for (Observacion observacionCollectionOldObservacion : observacionCollectionOld) {
                if (!observacionCollectionNew.contains(observacionCollectionOldObservacion)) {
                    observacionCollectionOldObservacion.setOpgTrabajo(null);
                    observacionCollectionOldObservacion = em.merge(observacionCollectionOldObservacion);
                }
            }
            for (Observacion observacionCollectionNewObservacion : observacionCollectionNew) {
                if (!observacionCollectionOld.contains(observacionCollectionNewObservacion)) {
                    TrabajoGrado oldOpgTrabajoOfObservacionCollectionNewObservacion = observacionCollectionNewObservacion.getOpgTrabajo();
                    observacionCollectionNewObservacion.setOpgTrabajo(trabajoGrado);
                    observacionCollectionNewObservacion = em.merge(observacionCollectionNewObservacion);
                    if (oldOpgTrabajoOfObservacionCollectionNewObservacion != null && !oldOpgTrabajoOfObservacionCollectionNewObservacion.equals(trabajoGrado)) {
                        oldOpgTrabajoOfObservacionCollectionNewObservacion.getObservacionCollection().remove(observacionCollectionNewObservacion);
                        oldOpgTrabajoOfObservacionCollectionNewObservacion = em.merge(oldOpgTrabajoOfObservacionCollectionNewObservacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = trabajoGrado.getTgId();
                if (findTrabajoGrado(id) == null) {
                    throw new NonexistentEntityException("The trabajoGrado with id " + id + " no longer exists.");
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
            TrabajoGrado trabajoGrado;
            try {
                trabajoGrado = em.getReference(TrabajoGrado.class, id);
                trabajoGrado.getTgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajoGrado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Propuesta propuestaOrphanCheck = trabajoGrado.getPropuesta();
            if (propuestaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TrabajoGrado (" + trabajoGrado + ") cannot be destroyed since the Propuesta " + propuestaOrphanCheck + " in its propuesta field has a non-nullable trabajoGrado field.");
            }
            Collection<Prorroga> prorrogaCollectionOrphanCheck = trabajoGrado.getProrrogaCollection();
            for (Prorroga prorrogaCollectionOrphanCheckProrroga : prorrogaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TrabajoGrado (" + trabajoGrado + ") cannot be destroyed since the Prorroga " + prorrogaCollectionOrphanCheckProrroga + " in its prorrogaCollection field has a non-nullable prorrogaTrabajo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EstadoTrabajoGrado tgConceptoEstado = trabajoGrado.getTgConceptoEstado();
            if (tgConceptoEstado != null) {
                tgConceptoEstado.getTrabajoGradoCollection().remove(trabajoGrado);
                tgConceptoEstado = em.merge(tgConceptoEstado);
            }
            ModalidadTrabajo tgModalidad = trabajoGrado.getTgModalidad();
            if (tgModalidad != null) {
                tgModalidad.getTrabajoGradoCollection().remove(trabajoGrado);
                tgModalidad = em.merge(tgModalidad);
            }
            Plazo tgPlazoEntrega = trabajoGrado.getTgPlazoEntrega();
            if (tgPlazoEntrega != null) {
                tgPlazoEntrega.getTrabajoGradoCollection().remove(trabajoGrado);
                tgPlazoEntrega = em.merge(tgPlazoEntrega);
            }
            Collection<Estudiante> estudianteCollection = trabajoGrado.getEstudianteCollection();
            for (Estudiante estudianteCollectionEstudiante : estudianteCollection) {
                estudianteCollectionEstudiante.getTrabajoGradoCollection().remove(trabajoGrado);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
            }
            Collection<Persona> personaCollection = trabajoGrado.getPersonaCollection();
            for (Persona personaCollectionPersona : personaCollection) {
                personaCollectionPersona.getTrabajoGradoCollection().remove(trabajoGrado);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            Collection<Observacion> observacionCollection = trabajoGrado.getObservacionCollection();
            for (Observacion observacionCollectionObservacion : observacionCollection) {
                observacionCollectionObservacion.setOpgTrabajo(null);
                observacionCollectionObservacion = em.merge(observacionCollectionObservacion);
            }
            em.remove(trabajoGrado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TrabajoGrado> findTrabajoGradoEntities() {
        return findTrabajoGradoEntities(true, -1, -1);
    }

    public List<TrabajoGrado> findTrabajoGradoEntities(int maxResults, int firstResult) {
        return findTrabajoGradoEntities(false, maxResults, firstResult);
    }

    private List<TrabajoGrado> findTrabajoGradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TrabajoGrado.class));
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

    public TrabajoGrado findTrabajoGrado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TrabajoGrado.class, id);
        } finally {
            em.close();
        }
    }

    public List<TrabajoGrado> findTrabajosVigentes() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("TrabajoGrado.findByDiffConcepto");
            q.setParameter("epgId", 1);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getTrabajoGradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TrabajoGrado> rt = cq.from(TrabajoGrado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
