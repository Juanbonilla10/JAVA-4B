/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.CategoProve;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class CategoProveJpaController implements Serializable {

    public CategoProveJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoProve categoProve) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (categoProve.getProveedorList() == null) {
            categoProve.setProveedorList(new ArrayList<Proveedor>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Proveedor> attachedProveedorList = new ArrayList<Proveedor>();
            for (Proveedor proveedorListProveedorToAttach : categoProve.getProveedorList()) {
                proveedorListProveedorToAttach = em.getReference(proveedorListProveedorToAttach.getClass(), proveedorListProveedorToAttach.getNit());
                attachedProveedorList.add(proveedorListProveedorToAttach);
            }
            categoProve.setProveedorList(attachedProveedorList);
            em.persist(categoProve);
            for (Proveedor proveedorListProveedor : categoProve.getProveedorList()) {
                CategoProve oldIdCateproveOfProveedorListProveedor = proveedorListProveedor.getIdCateprove();
                proveedorListProveedor.setIdCateprove(categoProve);
                proveedorListProveedor = em.merge(proveedorListProveedor);
                if (oldIdCateproveOfProveedorListProveedor != null) {
                    oldIdCateproveOfProveedorListProveedor.getProveedorList().remove(proveedorListProveedor);
                    oldIdCateproveOfProveedorListProveedor = em.merge(oldIdCateproveOfProveedorListProveedor);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCategoProve(categoProve.getIdCateprove()) != null) {
                throw new PreexistingEntityException("CategoProve " + categoProve + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoProve categoProve) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoProve persistentCategoProve = em.find(CategoProve.class, categoProve.getIdCateprove());
            List<Proveedor> proveedorListOld = persistentCategoProve.getProveedorList();
            List<Proveedor> proveedorListNew = categoProve.getProveedorList();
            List<Proveedor> attachedProveedorListNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorListNewProveedorToAttach : proveedorListNew) {
                proveedorListNewProveedorToAttach = em.getReference(proveedorListNewProveedorToAttach.getClass(), proveedorListNewProveedorToAttach.getNit());
                attachedProveedorListNew.add(proveedorListNewProveedorToAttach);
            }
            proveedorListNew = attachedProveedorListNew;
            categoProve.setProveedorList(proveedorListNew);
            categoProve = em.merge(categoProve);
            for (Proveedor proveedorListOldProveedor : proveedorListOld) {
                if (!proveedorListNew.contains(proveedorListOldProveedor)) {
                    proveedorListOldProveedor.setIdCateprove(null);
                    proveedorListOldProveedor = em.merge(proveedorListOldProveedor);
                }
            }
            for (Proveedor proveedorListNewProveedor : proveedorListNew) {
                if (!proveedorListOld.contains(proveedorListNewProveedor)) {
                    CategoProve oldIdCateproveOfProveedorListNewProveedor = proveedorListNewProveedor.getIdCateprove();
                    proveedorListNewProveedor.setIdCateprove(categoProve);
                    proveedorListNewProveedor = em.merge(proveedorListNewProveedor);
                    if (oldIdCateproveOfProveedorListNewProveedor != null && !oldIdCateproveOfProveedorListNewProveedor.equals(categoProve)) {
                        oldIdCateproveOfProveedorListNewProveedor.getProveedorList().remove(proveedorListNewProveedor);
                        oldIdCateproveOfProveedorListNewProveedor = em.merge(oldIdCateproveOfProveedorListNewProveedor);
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
                String id = categoProve.getIdCateprove();
                if (findCategoProve(id) == null) {
                    throw new NonexistentEntityException("The categoProve with id " + id + " no longer exists.");
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
            CategoProve categoProve;
            try {
                categoProve = em.getReference(CategoProve.class, id);
                categoProve.getIdCateprove();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoProve with id " + id + " no longer exists.", enfe);
            }
            List<Proveedor> proveedorList = categoProve.getProveedorList();
            for (Proveedor proveedorListProveedor : proveedorList) {
                proveedorListProveedor.setIdCateprove(null);
                proveedorListProveedor = em.merge(proveedorListProveedor);
            }
            em.remove(categoProve);
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

    public List<CategoProve> findCategoProveEntities() {
        return findCategoProveEntities(true, -1, -1);
    }

    public List<CategoProve> findCategoProveEntities(int maxResults, int firstResult) {
        return findCategoProveEntities(false, maxResults, firstResult);
    }

    private List<CategoProve> findCategoProveEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoProve.class));
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

    public CategoProve findCategoProve(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoProve.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoProveCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoProve> rt = cq.from(CategoProve.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
