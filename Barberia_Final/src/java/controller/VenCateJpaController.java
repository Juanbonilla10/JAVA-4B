/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.VenCate;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Venta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class VenCateJpaController implements Serializable {

    public VenCateJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VenCate venCate) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (venCate.getVentaList() == null) {
            venCate.setVentaList(new ArrayList<Venta>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : venCate.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdventa());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            venCate.setVentaList(attachedVentaList);
            em.persist(venCate);
            for (Venta ventaListVenta : venCate.getVentaList()) {
                VenCate oldIdVenCateOfVentaListVenta = ventaListVenta.getIdVenCate();
                ventaListVenta.setIdVenCate(venCate);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldIdVenCateOfVentaListVenta != null) {
                    oldIdVenCateOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldIdVenCateOfVentaListVenta = em.merge(oldIdVenCateOfVentaListVenta);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVenCate(venCate.getIdVenCate()) != null) {
                throw new PreexistingEntityException("VenCate " + venCate + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VenCate venCate) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VenCate persistentVenCate = em.find(VenCate.class, venCate.getIdVenCate());
            List<Venta> ventaListOld = persistentVenCate.getVentaList();
            List<Venta> ventaListNew = venCate.getVentaList();
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdventa());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            venCate.setVentaList(ventaListNew);
            venCate = em.merge(venCate);
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setIdVenCate(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    VenCate oldIdVenCateOfVentaListNewVenta = ventaListNewVenta.getIdVenCate();
                    ventaListNewVenta.setIdVenCate(venCate);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldIdVenCateOfVentaListNewVenta != null && !oldIdVenCateOfVentaListNewVenta.equals(venCate)) {
                        oldIdVenCateOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldIdVenCateOfVentaListNewVenta = em.merge(oldIdVenCateOfVentaListNewVenta);
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
                String id = venCate.getIdVenCate();
                if (findVenCate(id) == null) {
                    throw new NonexistentEntityException("The venCate with id " + id + " no longer exists.");
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
            VenCate venCate;
            try {
                venCate = em.getReference(VenCate.class, id);
                venCate.getIdVenCate();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venCate with id " + id + " no longer exists.", enfe);
            }
            List<Venta> ventaList = venCate.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setIdVenCate(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            em.remove(venCate);
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

    public List<VenCate> findVenCateEntities() {
        return findVenCateEntities(true, -1, -1);
    }

    public List<VenCate> findVenCateEntities(int maxResults, int firstResult) {
        return findVenCateEntities(false, maxResults, firstResult);
    }

    private List<VenCate> findVenCateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VenCate.class));
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

    public VenCate findVenCate(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VenCate.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenCateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VenCate> rt = cq.from(VenCate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
