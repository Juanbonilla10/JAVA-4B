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
import entity.Pqrs;
import entity.Empleados;
import entity.Respuesta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class RespuestaJpaController implements Serializable {

    public RespuestaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuesta respuesta) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pqrs idPqrs = respuesta.getIdPqrs();
            if (idPqrs != null) {
                idPqrs = em.getReference(idPqrs.getClass(), idPqrs.getIdPqrs());
                respuesta.setIdPqrs(idPqrs);
            }
            Empleados idempleados = respuesta.getIdempleados();
            if (idempleados != null) {
                idempleados = em.getReference(idempleados.getClass(), idempleados.getIdEmpleados());
                respuesta.setIdempleados(idempleados);
            }
            em.persist(respuesta);
            if (idPqrs != null) {
                idPqrs.getRespuestaList().add(respuesta);
                idPqrs = em.merge(idPqrs);
            }
            if (idempleados != null) {
                idempleados.getRespuestaList().add(respuesta);
                idempleados = em.merge(idempleados);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRespuesta(respuesta.getIdrespuesta()) != null) {
                throw new PreexistingEntityException("Respuesta " + respuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Respuesta respuesta) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Respuesta persistentRespuesta = em.find(Respuesta.class, respuesta.getIdrespuesta());
            Pqrs idPqrsOld = persistentRespuesta.getIdPqrs();
            Pqrs idPqrsNew = respuesta.getIdPqrs();
            Empleados idempleadosOld = persistentRespuesta.getIdempleados();
            Empleados idempleadosNew = respuesta.getIdempleados();
            if (idPqrsNew != null) {
                idPqrsNew = em.getReference(idPqrsNew.getClass(), idPqrsNew.getIdPqrs());
                respuesta.setIdPqrs(idPqrsNew);
            }
            if (idempleadosNew != null) {
                idempleadosNew = em.getReference(idempleadosNew.getClass(), idempleadosNew.getIdEmpleados());
                respuesta.setIdempleados(idempleadosNew);
            }
            respuesta = em.merge(respuesta);
            if (idPqrsOld != null && !idPqrsOld.equals(idPqrsNew)) {
                idPqrsOld.getRespuestaList().remove(respuesta);
                idPqrsOld = em.merge(idPqrsOld);
            }
            if (idPqrsNew != null && !idPqrsNew.equals(idPqrsOld)) {
                idPqrsNew.getRespuestaList().add(respuesta);
                idPqrsNew = em.merge(idPqrsNew);
            }
            if (idempleadosOld != null && !idempleadosOld.equals(idempleadosNew)) {
                idempleadosOld.getRespuestaList().remove(respuesta);
                idempleadosOld = em.merge(idempleadosOld);
            }
            if (idempleadosNew != null && !idempleadosNew.equals(idempleadosOld)) {
                idempleadosNew.getRespuestaList().add(respuesta);
                idempleadosNew = em.merge(idempleadosNew);
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
                String id = respuesta.getIdrespuesta();
                if (findRespuesta(id) == null) {
                    throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.");
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
            Respuesta respuesta;
            try {
                respuesta = em.getReference(Respuesta.class, id);
                respuesta.getIdrespuesta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.", enfe);
            }
            Pqrs idPqrs = respuesta.getIdPqrs();
            if (idPqrs != null) {
                idPqrs.getRespuestaList().remove(respuesta);
                idPqrs = em.merge(idPqrs);
            }
            Empleados idempleados = respuesta.getIdempleados();
            if (idempleados != null) {
                idempleados.getRespuestaList().remove(respuesta);
                idempleados = em.merge(idempleados);
            }
            em.remove(respuesta);
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

    public List<Respuesta> findRespuestaEntities() {
        return findRespuestaEntities(true, -1, -1);
    }

    public List<Respuesta> findRespuestaEntities(int maxResults, int firstResult) {
        return findRespuestaEntities(false, maxResults, firstResult);
    }

    private List<Respuesta> findRespuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuesta.class));
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

    public Respuesta findRespuesta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuesta> rt = cq.from(Respuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
