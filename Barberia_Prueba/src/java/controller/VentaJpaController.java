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
import entity.Inventarios;
import entity.VenCate;
import entity.Venta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inventarios idInventario = venta.getIdInventario();
            if (idInventario != null) {
                idInventario = em.getReference(idInventario.getClass(), idInventario.getIdInventario());
                venta.setIdInventario(idInventario);
            }
            VenCate idVenCate = venta.getIdVenCate();
            if (idVenCate != null) {
                idVenCate = em.getReference(idVenCate.getClass(), idVenCate.getIdVenCate());
                venta.setIdVenCate(idVenCate);
            }
            em.persist(venta);
            if (idInventario != null) {
                idInventario.getVentaList().add(venta);
                idInventario = em.merge(idInventario);
            }
            if (idVenCate != null) {
                idVenCate.getVentaList().add(venta);
                idVenCate = em.merge(idVenCate);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVenta(venta.getIdventa()) != null) {
                throw new PreexistingEntityException("Venta " + venta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Venta persistentVenta = em.find(Venta.class, venta.getIdventa());
            Inventarios idInventarioOld = persistentVenta.getIdInventario();
            Inventarios idInventarioNew = venta.getIdInventario();
            VenCate idVenCateOld = persistentVenta.getIdVenCate();
            VenCate idVenCateNew = venta.getIdVenCate();
            if (idInventarioNew != null) {
                idInventarioNew = em.getReference(idInventarioNew.getClass(), idInventarioNew.getIdInventario());
                venta.setIdInventario(idInventarioNew);
            }
            if (idVenCateNew != null) {
                idVenCateNew = em.getReference(idVenCateNew.getClass(), idVenCateNew.getIdVenCate());
                venta.setIdVenCate(idVenCateNew);
            }
            venta = em.merge(venta);
            if (idInventarioOld != null && !idInventarioOld.equals(idInventarioNew)) {
                idInventarioOld.getVentaList().remove(venta);
                idInventarioOld = em.merge(idInventarioOld);
            }
            if (idInventarioNew != null && !idInventarioNew.equals(idInventarioOld)) {
                idInventarioNew.getVentaList().add(venta);
                idInventarioNew = em.merge(idInventarioNew);
            }
            if (idVenCateOld != null && !idVenCateOld.equals(idVenCateNew)) {
                idVenCateOld.getVentaList().remove(venta);
                idVenCateOld = em.merge(idVenCateOld);
            }
            if (idVenCateNew != null && !idVenCateNew.equals(idVenCateOld)) {
                idVenCateNew.getVentaList().add(venta);
                idVenCateNew = em.merge(idVenCateNew);
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
                String id = venta.getIdventa();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getIdventa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Inventarios idInventario = venta.getIdInventario();
            if (idInventario != null) {
                idInventario.getVentaList().remove(venta);
                idInventario = em.merge(idInventario);
            }
            VenCate idVenCate = venta.getIdVenCate();
            if (idVenCate != null) {
                idVenCate.getVentaList().remove(venta);
                idVenCate = em.merge(idVenCate);
            }
            em.remove(venta);
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

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
