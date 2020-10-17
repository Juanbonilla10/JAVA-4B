/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.IngresoProd;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Inventarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class IngresoProdJpaController implements Serializable {

    public IngresoProdJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IngresoProd ingresoProd) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (ingresoProd.getInventariosList() == null) {
            ingresoProd.setInventariosList(new ArrayList<Inventarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Inventarios> attachedInventariosList = new ArrayList<Inventarios>();
            for (Inventarios inventariosListInventariosToAttach : ingresoProd.getInventariosList()) {
                inventariosListInventariosToAttach = em.getReference(inventariosListInventariosToAttach.getClass(), inventariosListInventariosToAttach.getIdInventario());
                attachedInventariosList.add(inventariosListInventariosToAttach);
            }
            ingresoProd.setInventariosList(attachedInventariosList);
            em.persist(ingresoProd);
            for (Inventarios inventariosListInventarios : ingresoProd.getInventariosList()) {
                IngresoProd oldIdingresoOfInventariosListInventarios = inventariosListInventarios.getIdingreso();
                inventariosListInventarios.setIdingreso(ingresoProd);
                inventariosListInventarios = em.merge(inventariosListInventarios);
                if (oldIdingresoOfInventariosListInventarios != null) {
                    oldIdingresoOfInventariosListInventarios.getInventariosList().remove(inventariosListInventarios);
                    oldIdingresoOfInventariosListInventarios = em.merge(oldIdingresoOfInventariosListInventarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findIngresoProd(ingresoProd.getIdingreso()) != null) {
                throw new PreexistingEntityException("IngresoProd " + ingresoProd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IngresoProd ingresoProd) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            IngresoProd persistentIngresoProd = em.find(IngresoProd.class, ingresoProd.getIdingreso());
            List<Inventarios> inventariosListOld = persistentIngresoProd.getInventariosList();
            List<Inventarios> inventariosListNew = ingresoProd.getInventariosList();
            List<Inventarios> attachedInventariosListNew = new ArrayList<Inventarios>();
            for (Inventarios inventariosListNewInventariosToAttach : inventariosListNew) {
                inventariosListNewInventariosToAttach = em.getReference(inventariosListNewInventariosToAttach.getClass(), inventariosListNewInventariosToAttach.getIdInventario());
                attachedInventariosListNew.add(inventariosListNewInventariosToAttach);
            }
            inventariosListNew = attachedInventariosListNew;
            ingresoProd.setInventariosList(inventariosListNew);
            ingresoProd = em.merge(ingresoProd);
            for (Inventarios inventariosListOldInventarios : inventariosListOld) {
                if (!inventariosListNew.contains(inventariosListOldInventarios)) {
                    inventariosListOldInventarios.setIdingreso(null);
                    inventariosListOldInventarios = em.merge(inventariosListOldInventarios);
                }
            }
            for (Inventarios inventariosListNewInventarios : inventariosListNew) {
                if (!inventariosListOld.contains(inventariosListNewInventarios)) {
                    IngresoProd oldIdingresoOfInventariosListNewInventarios = inventariosListNewInventarios.getIdingreso();
                    inventariosListNewInventarios.setIdingreso(ingresoProd);
                    inventariosListNewInventarios = em.merge(inventariosListNewInventarios);
                    if (oldIdingresoOfInventariosListNewInventarios != null && !oldIdingresoOfInventariosListNewInventarios.equals(ingresoProd)) {
                        oldIdingresoOfInventariosListNewInventarios.getInventariosList().remove(inventariosListNewInventarios);
                        oldIdingresoOfInventariosListNewInventarios = em.merge(oldIdingresoOfInventariosListNewInventarios);
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
                String id = ingresoProd.getIdingreso();
                if (findIngresoProd(id) == null) {
                    throw new NonexistentEntityException("The ingresoProd with id " + id + " no longer exists.");
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
            IngresoProd ingresoProd;
            try {
                ingresoProd = em.getReference(IngresoProd.class, id);
                ingresoProd.getIdingreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingresoProd with id " + id + " no longer exists.", enfe);
            }
            List<Inventarios> inventariosList = ingresoProd.getInventariosList();
            for (Inventarios inventariosListInventarios : inventariosList) {
                inventariosListInventarios.setIdingreso(null);
                inventariosListInventarios = em.merge(inventariosListInventarios);
            }
            em.remove(ingresoProd);
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

    public List<IngresoProd> findIngresoProdEntities() {
        return findIngresoProdEntities(true, -1, -1);
    }

    public List<IngresoProd> findIngresoProdEntities(int maxResults, int firstResult) {
        return findIngresoProdEntities(false, maxResults, firstResult);
    }

    private List<IngresoProd> findIngresoProdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IngresoProd.class));
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

    public IngresoProd findIngresoProd(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IngresoProd.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresoProdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IngresoProd> rt = cq.from(IngresoProd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
