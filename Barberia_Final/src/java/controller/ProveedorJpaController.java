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
import entity.CategoProve;
import entity.Inventarios;
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
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (proveedor.getInventariosList() == null) {
            proveedor.setInventariosList(new ArrayList<Inventarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoProve idCateprove = proveedor.getIdCateprove();
            if (idCateprove != null) {
                idCateprove = em.getReference(idCateprove.getClass(), idCateprove.getIdCateprove());
                proveedor.setIdCateprove(idCateprove);
            }
            List<Inventarios> attachedInventariosList = new ArrayList<Inventarios>();
            for (Inventarios inventariosListInventariosToAttach : proveedor.getInventariosList()) {
                inventariosListInventariosToAttach = em.getReference(inventariosListInventariosToAttach.getClass(), inventariosListInventariosToAttach.getIdInventario());
                attachedInventariosList.add(inventariosListInventariosToAttach);
            }
            proveedor.setInventariosList(attachedInventariosList);
            em.persist(proveedor);
            if (idCateprove != null) {
                idCateprove.getProveedorList().add(proveedor);
                idCateprove = em.merge(idCateprove);
            }
            for (Inventarios inventariosListInventarios : proveedor.getInventariosList()) {
                Proveedor oldNitOfInventariosListInventarios = inventariosListInventarios.getNit();
                inventariosListInventarios.setNit(proveedor);
                inventariosListInventarios = em.merge(inventariosListInventarios);
                if (oldNitOfInventariosListInventarios != null) {
                    oldNitOfInventariosListInventarios.getInventariosList().remove(inventariosListInventarios);
                    oldNitOfInventariosListInventarios = em.merge(oldNitOfInventariosListInventarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProveedor(proveedor.getNit()) != null) {
                throw new PreexistingEntityException("Proveedor " + proveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getNit());
            CategoProve idCateproveOld = persistentProveedor.getIdCateprove();
            CategoProve idCateproveNew = proveedor.getIdCateprove();
            List<Inventarios> inventariosListOld = persistentProveedor.getInventariosList();
            List<Inventarios> inventariosListNew = proveedor.getInventariosList();
            if (idCateproveNew != null) {
                idCateproveNew = em.getReference(idCateproveNew.getClass(), idCateproveNew.getIdCateprove());
                proveedor.setIdCateprove(idCateproveNew);
            }
            List<Inventarios> attachedInventariosListNew = new ArrayList<Inventarios>();
            for (Inventarios inventariosListNewInventariosToAttach : inventariosListNew) {
                inventariosListNewInventariosToAttach = em.getReference(inventariosListNewInventariosToAttach.getClass(), inventariosListNewInventariosToAttach.getIdInventario());
                attachedInventariosListNew.add(inventariosListNewInventariosToAttach);
            }
            inventariosListNew = attachedInventariosListNew;
            proveedor.setInventariosList(inventariosListNew);
            proveedor = em.merge(proveedor);
            if (idCateproveOld != null && !idCateproveOld.equals(idCateproveNew)) {
                idCateproveOld.getProveedorList().remove(proveedor);
                idCateproveOld = em.merge(idCateproveOld);
            }
            if (idCateproveNew != null && !idCateproveNew.equals(idCateproveOld)) {
                idCateproveNew.getProveedorList().add(proveedor);
                idCateproveNew = em.merge(idCateproveNew);
            }
            for (Inventarios inventariosListOldInventarios : inventariosListOld) {
                if (!inventariosListNew.contains(inventariosListOldInventarios)) {
                    inventariosListOldInventarios.setNit(null);
                    inventariosListOldInventarios = em.merge(inventariosListOldInventarios);
                }
            }
            for (Inventarios inventariosListNewInventarios : inventariosListNew) {
                if (!inventariosListOld.contains(inventariosListNewInventarios)) {
                    Proveedor oldNitOfInventariosListNewInventarios = inventariosListNewInventarios.getNit();
                    inventariosListNewInventarios.setNit(proveedor);
                    inventariosListNewInventarios = em.merge(inventariosListNewInventarios);
                    if (oldNitOfInventariosListNewInventarios != null && !oldNitOfInventariosListNewInventarios.equals(proveedor)) {
                        oldNitOfInventariosListNewInventarios.getInventariosList().remove(inventariosListNewInventarios);
                        oldNitOfInventariosListNewInventarios = em.merge(oldNitOfInventariosListNewInventarios);
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
                Integer id = proveedor.getNit();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            CategoProve idCateprove = proveedor.getIdCateprove();
            if (idCateprove != null) {
                idCateprove.getProveedorList().remove(proveedor);
                idCateprove = em.merge(idCateprove);
            }
            List<Inventarios> inventariosList = proveedor.getInventariosList();
            for (Inventarios inventariosListInventarios : inventariosList) {
                inventariosListInventarios.setNit(null);
                inventariosListInventarios = em.merge(inventariosListInventarios);
            }
            em.remove(proveedor);
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

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
