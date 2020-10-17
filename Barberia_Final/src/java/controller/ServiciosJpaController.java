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
import entity.TipoServicio;
import entity.Citas;
import entity.Servicios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class ServiciosJpaController implements Serializable {

    public ServiciosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicios servicios) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoServicio idtipoServicio = servicios.getIdtipoServicio();
            if (idtipoServicio != null) {
                idtipoServicio = em.getReference(idtipoServicio.getClass(), idtipoServicio.getIdtipoServicio());
                servicios.setIdtipoServicio(idtipoServicio);
            }
            Citas idcitas = servicios.getIdcitas();
            if (idcitas != null) {
                idcitas = em.getReference(idcitas.getClass(), idcitas.getIdCitas());
                servicios.setIdcitas(idcitas);
            }
            em.persist(servicios);
            if (idtipoServicio != null) {
                idtipoServicio.getServiciosList().add(servicios);
                idtipoServicio = em.merge(idtipoServicio);
            }
            if (idcitas != null) {
                idcitas.getServiciosList().add(servicios);
                idcitas = em.merge(idcitas);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findServicios(servicios.getIdRegisSer()) != null) {
                throw new PreexistingEntityException("Servicios " + servicios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicios servicios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Servicios persistentServicios = em.find(Servicios.class, servicios.getIdRegisSer());
            TipoServicio idtipoServicioOld = persistentServicios.getIdtipoServicio();
            TipoServicio idtipoServicioNew = servicios.getIdtipoServicio();
            Citas idcitasOld = persistentServicios.getIdcitas();
            Citas idcitasNew = servicios.getIdcitas();
            if (idtipoServicioNew != null) {
                idtipoServicioNew = em.getReference(idtipoServicioNew.getClass(), idtipoServicioNew.getIdtipoServicio());
                servicios.setIdtipoServicio(idtipoServicioNew);
            }
            if (idcitasNew != null) {
                idcitasNew = em.getReference(idcitasNew.getClass(), idcitasNew.getIdCitas());
                servicios.setIdcitas(idcitasNew);
            }
            servicios = em.merge(servicios);
            if (idtipoServicioOld != null && !idtipoServicioOld.equals(idtipoServicioNew)) {
                idtipoServicioOld.getServiciosList().remove(servicios);
                idtipoServicioOld = em.merge(idtipoServicioOld);
            }
            if (idtipoServicioNew != null && !idtipoServicioNew.equals(idtipoServicioOld)) {
                idtipoServicioNew.getServiciosList().add(servicios);
                idtipoServicioNew = em.merge(idtipoServicioNew);
            }
            if (idcitasOld != null && !idcitasOld.equals(idcitasNew)) {
                idcitasOld.getServiciosList().remove(servicios);
                idcitasOld = em.merge(idcitasOld);
            }
            if (idcitasNew != null && !idcitasNew.equals(idcitasOld)) {
                idcitasNew.getServiciosList().add(servicios);
                idcitasNew = em.merge(idcitasNew);
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
                String id = servicios.getIdRegisSer();
                if (findServicios(id) == null) {
                    throw new NonexistentEntityException("The servicios with id " + id + " no longer exists.");
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
            Servicios servicios;
            try {
                servicios = em.getReference(Servicios.class, id);
                servicios.getIdRegisSer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicios with id " + id + " no longer exists.", enfe);
            }
            TipoServicio idtipoServicio = servicios.getIdtipoServicio();
            if (idtipoServicio != null) {
                idtipoServicio.getServiciosList().remove(servicios);
                idtipoServicio = em.merge(idtipoServicio);
            }
            Citas idcitas = servicios.getIdcitas();
            if (idcitas != null) {
                idcitas.getServiciosList().remove(servicios);
                idcitas = em.merge(idcitas);
            }
            em.remove(servicios);
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

    public List<Servicios> findServiciosEntities() {
        return findServiciosEntities(true, -1, -1);
    }

    public List<Servicios> findServiciosEntities(int maxResults, int firstResult) {
        return findServiciosEntities(false, maxResults, firstResult);
    }

    private List<Servicios> findServiciosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicios.class));
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

    public Servicios findServicios(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicios.class, id);
        } finally {
            em.close();
        }
    }

    public int getServiciosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicios> rt = cq.from(Servicios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
