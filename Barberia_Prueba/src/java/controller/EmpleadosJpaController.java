/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Usuarios;
import entity.Cronograma;
import java.util.ArrayList;
import java.util.List;
import entity.Respuesta;
import entity.CrearPro;
import entity.Empleados;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class EmpleadosJpaController implements Serializable {

    public EmpleadosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empleados.getCronogramaList() == null) {
            empleados.setCronogramaList(new ArrayList<Cronograma>());
        }
        if (empleados.getRespuestaList() == null) {
            empleados.setRespuestaList(new ArrayList<Respuesta>());
        }
        if (empleados.getCrearProList() == null) {
            empleados.setCrearProList(new ArrayList<CrearPro>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios numIdentificacion = empleados.getNumIdentificacion();
            if (numIdentificacion != null) {
                numIdentificacion = em.getReference(numIdentificacion.getClass(), numIdentificacion.getNumIdentificacion());
                empleados.setNumIdentificacion(numIdentificacion);
            }
            List<Cronograma> attachedCronogramaList = new ArrayList<Cronograma>();
            for (Cronograma cronogramaListCronogramaToAttach : empleados.getCronogramaList()) {
                cronogramaListCronogramaToAttach = em.getReference(cronogramaListCronogramaToAttach.getClass(), cronogramaListCronogramaToAttach.getIdCronograma());
                attachedCronogramaList.add(cronogramaListCronogramaToAttach);
            }
            empleados.setCronogramaList(attachedCronogramaList);
            List<Respuesta> attachedRespuestaList = new ArrayList<Respuesta>();
            for (Respuesta respuestaListRespuestaToAttach : empleados.getRespuestaList()) {
                respuestaListRespuestaToAttach = em.getReference(respuestaListRespuestaToAttach.getClass(), respuestaListRespuestaToAttach.getIdrespuesta());
                attachedRespuestaList.add(respuestaListRespuestaToAttach);
            }
            empleados.setRespuestaList(attachedRespuestaList);
            List<CrearPro> attachedCrearProList = new ArrayList<CrearPro>();
            for (CrearPro crearProListCrearProToAttach : empleados.getCrearProList()) {
                crearProListCrearProToAttach = em.getReference(crearProListCrearProToAttach.getClass(), crearProListCrearProToAttach.getReferenciaProducto());
                attachedCrearProList.add(crearProListCrearProToAttach);
            }
            empleados.setCrearProList(attachedCrearProList);
            em.persist(empleados);
            if (numIdentificacion != null) {
                numIdentificacion.getEmpleadosList().add(empleados);
                numIdentificacion = em.merge(numIdentificacion);
            }
            for (Cronograma cronogramaListCronograma : empleados.getCronogramaList()) {
                Empleados oldIdEmpleadosOfCronogramaListCronograma = cronogramaListCronograma.getIdEmpleados();
                cronogramaListCronograma.setIdEmpleados(empleados);
                cronogramaListCronograma = em.merge(cronogramaListCronograma);
                if (oldIdEmpleadosOfCronogramaListCronograma != null) {
                    oldIdEmpleadosOfCronogramaListCronograma.getCronogramaList().remove(cronogramaListCronograma);
                    oldIdEmpleadosOfCronogramaListCronograma = em.merge(oldIdEmpleadosOfCronogramaListCronograma);
                }
            }
            for (Respuesta respuestaListRespuesta : empleados.getRespuestaList()) {
                Empleados oldIdempleadosOfRespuestaListRespuesta = respuestaListRespuesta.getIdempleados();
                respuestaListRespuesta.setIdempleados(empleados);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
                if (oldIdempleadosOfRespuestaListRespuesta != null) {
                    oldIdempleadosOfRespuestaListRespuesta.getRespuestaList().remove(respuestaListRespuesta);
                    oldIdempleadosOfRespuestaListRespuesta = em.merge(oldIdempleadosOfRespuestaListRespuesta);
                }
            }
            for (CrearPro crearProListCrearPro : empleados.getCrearProList()) {
                Empleados oldIdEmpleadosOfCrearProListCrearPro = crearProListCrearPro.getIdEmpleados();
                crearProListCrearPro.setIdEmpleados(empleados);
                crearProListCrearPro = em.merge(crearProListCrearPro);
                if (oldIdEmpleadosOfCrearProListCrearPro != null) {
                    oldIdEmpleadosOfCrearProListCrearPro.getCrearProList().remove(crearProListCrearPro);
                    oldIdEmpleadosOfCrearProListCrearPro = em.merge(oldIdEmpleadosOfCrearProListCrearPro);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpleados(empleados.getIdEmpleados()) != null) {
                throw new PreexistingEntityException("Empleados " + empleados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getIdEmpleados());
            Usuarios numIdentificacionOld = persistentEmpleados.getNumIdentificacion();
            Usuarios numIdentificacionNew = empleados.getNumIdentificacion();
            List<Cronograma> cronogramaListOld = persistentEmpleados.getCronogramaList();
            List<Cronograma> cronogramaListNew = empleados.getCronogramaList();
            List<Respuesta> respuestaListOld = persistentEmpleados.getRespuestaList();
            List<Respuesta> respuestaListNew = empleados.getRespuestaList();
            List<CrearPro> crearProListOld = persistentEmpleados.getCrearProList();
            List<CrearPro> crearProListNew = empleados.getCrearProList();
            if (numIdentificacionNew != null) {
                numIdentificacionNew = em.getReference(numIdentificacionNew.getClass(), numIdentificacionNew.getNumIdentificacion());
                empleados.setNumIdentificacion(numIdentificacionNew);
            }
            List<Cronograma> attachedCronogramaListNew = new ArrayList<Cronograma>();
            for (Cronograma cronogramaListNewCronogramaToAttach : cronogramaListNew) {
                cronogramaListNewCronogramaToAttach = em.getReference(cronogramaListNewCronogramaToAttach.getClass(), cronogramaListNewCronogramaToAttach.getIdCronograma());
                attachedCronogramaListNew.add(cronogramaListNewCronogramaToAttach);
            }
            cronogramaListNew = attachedCronogramaListNew;
            empleados.setCronogramaList(cronogramaListNew);
            List<Respuesta> attachedRespuestaListNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaListNewRespuestaToAttach : respuestaListNew) {
                respuestaListNewRespuestaToAttach = em.getReference(respuestaListNewRespuestaToAttach.getClass(), respuestaListNewRespuestaToAttach.getIdrespuesta());
                attachedRespuestaListNew.add(respuestaListNewRespuestaToAttach);
            }
            respuestaListNew = attachedRespuestaListNew;
            empleados.setRespuestaList(respuestaListNew);
            List<CrearPro> attachedCrearProListNew = new ArrayList<CrearPro>();
            for (CrearPro crearProListNewCrearProToAttach : crearProListNew) {
                crearProListNewCrearProToAttach = em.getReference(crearProListNewCrearProToAttach.getClass(), crearProListNewCrearProToAttach.getReferenciaProducto());
                attachedCrearProListNew.add(crearProListNewCrearProToAttach);
            }
            crearProListNew = attachedCrearProListNew;
            empleados.setCrearProList(crearProListNew);
            empleados = em.merge(empleados);
            if (numIdentificacionOld != null && !numIdentificacionOld.equals(numIdentificacionNew)) {
                numIdentificacionOld.getEmpleadosList().remove(empleados);
                numIdentificacionOld = em.merge(numIdentificacionOld);
            }
            if (numIdentificacionNew != null && !numIdentificacionNew.equals(numIdentificacionOld)) {
                numIdentificacionNew.getEmpleadosList().add(empleados);
                numIdentificacionNew = em.merge(numIdentificacionNew);
            }
            for (Cronograma cronogramaListOldCronograma : cronogramaListOld) {
                if (!cronogramaListNew.contains(cronogramaListOldCronograma)) {
                    cronogramaListOldCronograma.setIdEmpleados(null);
                    cronogramaListOldCronograma = em.merge(cronogramaListOldCronograma);
                }
            }
            for (Cronograma cronogramaListNewCronograma : cronogramaListNew) {
                if (!cronogramaListOld.contains(cronogramaListNewCronograma)) {
                    Empleados oldIdEmpleadosOfCronogramaListNewCronograma = cronogramaListNewCronograma.getIdEmpleados();
                    cronogramaListNewCronograma.setIdEmpleados(empleados);
                    cronogramaListNewCronograma = em.merge(cronogramaListNewCronograma);
                    if (oldIdEmpleadosOfCronogramaListNewCronograma != null && !oldIdEmpleadosOfCronogramaListNewCronograma.equals(empleados)) {
                        oldIdEmpleadosOfCronogramaListNewCronograma.getCronogramaList().remove(cronogramaListNewCronograma);
                        oldIdEmpleadosOfCronogramaListNewCronograma = em.merge(oldIdEmpleadosOfCronogramaListNewCronograma);
                    }
                }
            }
            for (Respuesta respuestaListOldRespuesta : respuestaListOld) {
                if (!respuestaListNew.contains(respuestaListOldRespuesta)) {
                    respuestaListOldRespuesta.setIdempleados(null);
                    respuestaListOldRespuesta = em.merge(respuestaListOldRespuesta);
                }
            }
            for (Respuesta respuestaListNewRespuesta : respuestaListNew) {
                if (!respuestaListOld.contains(respuestaListNewRespuesta)) {
                    Empleados oldIdempleadosOfRespuestaListNewRespuesta = respuestaListNewRespuesta.getIdempleados();
                    respuestaListNewRespuesta.setIdempleados(empleados);
                    respuestaListNewRespuesta = em.merge(respuestaListNewRespuesta);
                    if (oldIdempleadosOfRespuestaListNewRespuesta != null && !oldIdempleadosOfRespuestaListNewRespuesta.equals(empleados)) {
                        oldIdempleadosOfRespuestaListNewRespuesta.getRespuestaList().remove(respuestaListNewRespuesta);
                        oldIdempleadosOfRespuestaListNewRespuesta = em.merge(oldIdempleadosOfRespuestaListNewRespuesta);
                    }
                }
            }
            for (CrearPro crearProListOldCrearPro : crearProListOld) {
                if (!crearProListNew.contains(crearProListOldCrearPro)) {
                    crearProListOldCrearPro.setIdEmpleados(null);
                    crearProListOldCrearPro = em.merge(crearProListOldCrearPro);
                }
            }
            for (CrearPro crearProListNewCrearPro : crearProListNew) {
                if (!crearProListOld.contains(crearProListNewCrearPro)) {
                    Empleados oldIdEmpleadosOfCrearProListNewCrearPro = crearProListNewCrearPro.getIdEmpleados();
                    crearProListNewCrearPro.setIdEmpleados(empleados);
                    crearProListNewCrearPro = em.merge(crearProListNewCrearPro);
                    if (oldIdEmpleadosOfCrearProListNewCrearPro != null && !oldIdEmpleadosOfCrearProListNewCrearPro.equals(empleados)) {
                        oldIdEmpleadosOfCrearProListNewCrearPro.getCrearProList().remove(crearProListNewCrearPro);
                        oldIdEmpleadosOfCrearProListNewCrearPro = em.merge(oldIdEmpleadosOfCrearProListNewCrearPro);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleados.getIdEmpleados();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getIdEmpleados();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            Usuarios numIdentificacion = empleados.getNumIdentificacion();
            if (numIdentificacion != null) {
                numIdentificacion.getEmpleadosList().remove(empleados);
                numIdentificacion = em.merge(numIdentificacion);
            }
            List<Cronograma> cronogramaList = empleados.getCronogramaList();
            for (Cronograma cronogramaListCronograma : cronogramaList) {
                cronogramaListCronograma.setIdEmpleados(null);
                cronogramaListCronograma = em.merge(cronogramaListCronograma);
            }
            List<Respuesta> respuestaList = empleados.getRespuestaList();
            for (Respuesta respuestaListRespuesta : respuestaList) {
                respuestaListRespuesta.setIdempleados(null);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
            }
            List<CrearPro> crearProList = empleados.getCrearProList();
            for (CrearPro crearProListCrearPro : crearProList) {
                crearProListCrearPro.setIdEmpleados(null);
                crearProListCrearPro = em.merge(crearProListCrearPro);
            }
            em.remove(empleados);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
