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
import entity.Proveedor;
import entity.CrearPro;
import entity.IngresoProd;
import entity.Inventarios;
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
public class InventariosJpaController implements Serializable {

    public InventariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventarios inventarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (inventarios.getVentaList() == null) {
            inventarios.setVentaList(new ArrayList<Venta>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedor nit = inventarios.getNit();
            if (nit != null) {
                nit = em.getReference(nit.getClass(), nit.getNit());
                inventarios.setNit(nit);
            }
            CrearPro referenciaProducto = inventarios.getReferenciaProducto();
            if (referenciaProducto != null) {
                referenciaProducto = em.getReference(referenciaProducto.getClass(), referenciaProducto.getReferenciaProducto());
                inventarios.setReferenciaProducto(referenciaProducto);
            }
            IngresoProd idingreso = inventarios.getIdingreso();
            if (idingreso != null) {
                idingreso = em.getReference(idingreso.getClass(), idingreso.getIdingreso());
                inventarios.setIdingreso(idingreso);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : inventarios.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdventa());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            inventarios.setVentaList(attachedVentaList);
            em.persist(inventarios);
            if (nit != null) {
                nit.getInventariosList().add(inventarios);
                nit = em.merge(nit);
            }
            if (referenciaProducto != null) {
                referenciaProducto.getInventariosList().add(inventarios);
                referenciaProducto = em.merge(referenciaProducto);
            }
            if (idingreso != null) {
                idingreso.getInventariosList().add(inventarios);
                idingreso = em.merge(idingreso);
            }
            for (Venta ventaListVenta : inventarios.getVentaList()) {
                Inventarios oldIdInventarioOfVentaListVenta = ventaListVenta.getIdInventario();
                ventaListVenta.setIdInventario(inventarios);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldIdInventarioOfVentaListVenta != null) {
                    oldIdInventarioOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldIdInventarioOfVentaListVenta = em.merge(oldIdInventarioOfVentaListVenta);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findInventarios(inventarios.getIdInventario()) != null) {
                throw new PreexistingEntityException("Inventarios " + inventarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventarios inventarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Inventarios persistentInventarios = em.find(Inventarios.class, inventarios.getIdInventario());
            Proveedor nitOld = persistentInventarios.getNit();
            Proveedor nitNew = inventarios.getNit();
            CrearPro referenciaProductoOld = persistentInventarios.getReferenciaProducto();
            CrearPro referenciaProductoNew = inventarios.getReferenciaProducto();
            IngresoProd idingresoOld = persistentInventarios.getIdingreso();
            IngresoProd idingresoNew = inventarios.getIdingreso();
            List<Venta> ventaListOld = persistentInventarios.getVentaList();
            List<Venta> ventaListNew = inventarios.getVentaList();
            if (nitNew != null) {
                nitNew = em.getReference(nitNew.getClass(), nitNew.getNit());
                inventarios.setNit(nitNew);
            }
            if (referenciaProductoNew != null) {
                referenciaProductoNew = em.getReference(referenciaProductoNew.getClass(), referenciaProductoNew.getReferenciaProducto());
                inventarios.setReferenciaProducto(referenciaProductoNew);
            }
            if (idingresoNew != null) {
                idingresoNew = em.getReference(idingresoNew.getClass(), idingresoNew.getIdingreso());
                inventarios.setIdingreso(idingresoNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdventa());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            inventarios.setVentaList(ventaListNew);
            inventarios = em.merge(inventarios);
            if (nitOld != null && !nitOld.equals(nitNew)) {
                nitOld.getInventariosList().remove(inventarios);
                nitOld = em.merge(nitOld);
            }
            if (nitNew != null && !nitNew.equals(nitOld)) {
                nitNew.getInventariosList().add(inventarios);
                nitNew = em.merge(nitNew);
            }
            if (referenciaProductoOld != null && !referenciaProductoOld.equals(referenciaProductoNew)) {
                referenciaProductoOld.getInventariosList().remove(inventarios);
                referenciaProductoOld = em.merge(referenciaProductoOld);
            }
            if (referenciaProductoNew != null && !referenciaProductoNew.equals(referenciaProductoOld)) {
                referenciaProductoNew.getInventariosList().add(inventarios);
                referenciaProductoNew = em.merge(referenciaProductoNew);
            }
            if (idingresoOld != null && !idingresoOld.equals(idingresoNew)) {
                idingresoOld.getInventariosList().remove(inventarios);
                idingresoOld = em.merge(idingresoOld);
            }
            if (idingresoNew != null && !idingresoNew.equals(idingresoOld)) {
                idingresoNew.getInventariosList().add(inventarios);
                idingresoNew = em.merge(idingresoNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setIdInventario(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Inventarios oldIdInventarioOfVentaListNewVenta = ventaListNewVenta.getIdInventario();
                    ventaListNewVenta.setIdInventario(inventarios);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldIdInventarioOfVentaListNewVenta != null && !oldIdInventarioOfVentaListNewVenta.equals(inventarios)) {
                        oldIdInventarioOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldIdInventarioOfVentaListNewVenta = em.merge(oldIdInventarioOfVentaListNewVenta);
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
                String id = inventarios.getIdInventario();
                if (findInventarios(id) == null) {
                    throw new NonexistentEntityException("The inventarios with id " + id + " no longer exists.");
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
            Inventarios inventarios;
            try {
                inventarios = em.getReference(Inventarios.class, id);
                inventarios.getIdInventario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventarios with id " + id + " no longer exists.", enfe);
            }
            Proveedor nit = inventarios.getNit();
            if (nit != null) {
                nit.getInventariosList().remove(inventarios);
                nit = em.merge(nit);
            }
            CrearPro referenciaProducto = inventarios.getReferenciaProducto();
            if (referenciaProducto != null) {
                referenciaProducto.getInventariosList().remove(inventarios);
                referenciaProducto = em.merge(referenciaProducto);
            }
            IngresoProd idingreso = inventarios.getIdingreso();
            if (idingreso != null) {
                idingreso.getInventariosList().remove(inventarios);
                idingreso = em.merge(idingreso);
            }
            List<Venta> ventaList = inventarios.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setIdInventario(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            em.remove(inventarios);
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

    public List<Inventarios> findInventariosEntities() {
        return findInventariosEntities(true, -1, -1);
    }

    public List<Inventarios> findInventariosEntities(int maxResults, int firstResult) {
        return findInventariosEntities(false, maxResults, firstResult);
    }

    private List<Inventarios> findInventariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventarios.class));
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

    public Inventarios findInventarios(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventarios> rt = cq.from(Inventarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
