/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.PagoServicio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.TipoServicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class PagoServicioJpaController implements Serializable {

    public PagoServicioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoServicio pagoServicio) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoServicio idtipoServicio = pagoServicio.getIdtipoServicio();
            if (idtipoServicio != null) {
                idtipoServicio = em.getReference(idtipoServicio.getClass(), idtipoServicio.getIdtipoServicio());
                pagoServicio.setIdtipoServicio(idtipoServicio);
            }
            em.persist(pagoServicio);
            if (idtipoServicio != null) {
                idtipoServicio.getPagoServicioList().add(pagoServicio);
                idtipoServicio = em.merge(idtipoServicio);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPagoServicio(pagoServicio.getCodigodelpago()) != null) {
                throw new PreexistingEntityException("PagoServicio " + pagoServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoServicio pagoServicio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PagoServicio persistentPagoServicio = em.find(PagoServicio.class, pagoServicio.getCodigodelpago());
            TipoServicio idtipoServicioOld = persistentPagoServicio.getIdtipoServicio();
            TipoServicio idtipoServicioNew = pagoServicio.getIdtipoServicio();
            if (idtipoServicioNew != null) {
                idtipoServicioNew = em.getReference(idtipoServicioNew.getClass(), idtipoServicioNew.getIdtipoServicio());
                pagoServicio.setIdtipoServicio(idtipoServicioNew);
            }
            pagoServicio = em.merge(pagoServicio);
            if (idtipoServicioOld != null && !idtipoServicioOld.equals(idtipoServicioNew)) {
                idtipoServicioOld.getPagoServicioList().remove(pagoServicio);
                idtipoServicioOld = em.merge(idtipoServicioOld);
            }
            if (idtipoServicioNew != null && !idtipoServicioNew.equals(idtipoServicioOld)) {
                idtipoServicioNew.getPagoServicioList().add(pagoServicio);
                idtipoServicioNew = em.merge(idtipoServicioNew);
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
                String id = pagoServicio.getCodigodelpago();
                if (findPagoServicio(id) == null) {
                    throw new NonexistentEntityException("The pagoServicio with id " + id + " no longer exists.");
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
            PagoServicio pagoServicio;
            try {
                pagoServicio = em.getReference(PagoServicio.class, id);
                pagoServicio.getCodigodelpago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoServicio with id " + id + " no longer exists.", enfe);
            }
            TipoServicio idtipoServicio = pagoServicio.getIdtipoServicio();
            if (idtipoServicio != null) {
                idtipoServicio.getPagoServicioList().remove(pagoServicio);
                idtipoServicio = em.merge(idtipoServicio);
            }
            em.remove(pagoServicio);
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

    public List<PagoServicio> findPagoServicioEntities() {
        return findPagoServicioEntities(true, -1, -1);
    }

    public List<PagoServicio> findPagoServicioEntities(int maxResults, int firstResult) {
        return findPagoServicioEntities(false, maxResults, firstResult);
    }

    private List<PagoServicio> findPagoServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoServicio.class));
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

    public PagoServicio findPagoServicio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoServicio> rt = cq.from(PagoServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
