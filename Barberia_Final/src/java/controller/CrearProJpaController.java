/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.CrearPro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Empleados;
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
public class CrearProJpaController implements Serializable {

    public CrearProJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CrearPro crearPro) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (crearPro.getInventariosList() == null) {
            crearPro.setInventariosList(new ArrayList<Inventarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleados idEmpleados = crearPro.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados = em.getReference(idEmpleados.getClass(), idEmpleados.getIdEmpleados());
                crearPro.setIdEmpleados(idEmpleados);
            }
            List<Inventarios> attachedInventariosList = new ArrayList<Inventarios>();
            for (Inventarios inventariosListInventariosToAttach : crearPro.getInventariosList()) {
                inventariosListInventariosToAttach = em.getReference(inventariosListInventariosToAttach.getClass(), inventariosListInventariosToAttach.getIdInventario());
                attachedInventariosList.add(inventariosListInventariosToAttach);
            }
            crearPro.setInventariosList(attachedInventariosList);
            em.persist(crearPro);
            if (idEmpleados != null) {
                idEmpleados.getCrearProList().add(crearPro);
                idEmpleados = em.merge(idEmpleados);
            }
            for (Inventarios inventariosListInventarios : crearPro.getInventariosList()) {
                CrearPro oldReferenciaProductoOfInventariosListInventarios = inventariosListInventarios.getReferenciaProducto();
                inventariosListInventarios.setReferenciaProducto(crearPro);
                inventariosListInventarios = em.merge(inventariosListInventarios);
                if (oldReferenciaProductoOfInventariosListInventarios != null) {
                    oldReferenciaProductoOfInventariosListInventarios.getInventariosList().remove(inventariosListInventarios);
                    oldReferenciaProductoOfInventariosListInventarios = em.merge(oldReferenciaProductoOfInventariosListInventarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCrearPro(crearPro.getReferenciaProducto()) != null) {
                throw new PreexistingEntityException("CrearPro " + crearPro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CrearPro crearPro) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CrearPro persistentCrearPro = em.find(CrearPro.class, crearPro.getReferenciaProducto());
            Empleados idEmpleadosOld = persistentCrearPro.getIdEmpleados();
            Empleados idEmpleadosNew = crearPro.getIdEmpleados();
            List<Inventarios> inventariosListOld = persistentCrearPro.getInventariosList();
            List<Inventarios> inventariosListNew = crearPro.getInventariosList();
            if (idEmpleadosNew != null) {
                idEmpleadosNew = em.getReference(idEmpleadosNew.getClass(), idEmpleadosNew.getIdEmpleados());
                crearPro.setIdEmpleados(idEmpleadosNew);
            }
            List<Inventarios> attachedInventariosListNew = new ArrayList<Inventarios>();
            for (Inventarios inventariosListNewInventariosToAttach : inventariosListNew) {
                inventariosListNewInventariosToAttach = em.getReference(inventariosListNewInventariosToAttach.getClass(), inventariosListNewInventariosToAttach.getIdInventario());
                attachedInventariosListNew.add(inventariosListNewInventariosToAttach);
            }
            inventariosListNew = attachedInventariosListNew;
            crearPro.setInventariosList(inventariosListNew);
            crearPro = em.merge(crearPro);
            if (idEmpleadosOld != null && !idEmpleadosOld.equals(idEmpleadosNew)) {
                idEmpleadosOld.getCrearProList().remove(crearPro);
                idEmpleadosOld = em.merge(idEmpleadosOld);
            }
            if (idEmpleadosNew != null && !idEmpleadosNew.equals(idEmpleadosOld)) {
                idEmpleadosNew.getCrearProList().add(crearPro);
                idEmpleadosNew = em.merge(idEmpleadosNew);
            }
            for (Inventarios inventariosListOldInventarios : inventariosListOld) {
                if (!inventariosListNew.contains(inventariosListOldInventarios)) {
                    inventariosListOldInventarios.setReferenciaProducto(null);
                    inventariosListOldInventarios = em.merge(inventariosListOldInventarios);
                }
            }
            for (Inventarios inventariosListNewInventarios : inventariosListNew) {
                if (!inventariosListOld.contains(inventariosListNewInventarios)) {
                    CrearPro oldReferenciaProductoOfInventariosListNewInventarios = inventariosListNewInventarios.getReferenciaProducto();
                    inventariosListNewInventarios.setReferenciaProducto(crearPro);
                    inventariosListNewInventarios = em.merge(inventariosListNewInventarios);
                    if (oldReferenciaProductoOfInventariosListNewInventarios != null && !oldReferenciaProductoOfInventariosListNewInventarios.equals(crearPro)) {
                        oldReferenciaProductoOfInventariosListNewInventarios.getInventariosList().remove(inventariosListNewInventarios);
                        oldReferenciaProductoOfInventariosListNewInventarios = em.merge(oldReferenciaProductoOfInventariosListNewInventarios);
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
                String id = crearPro.getReferenciaProducto();
                if (findCrearPro(id) == null) {
                    throw new NonexistentEntityException("The crearPro with id " + id + " no longer exists.");
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
            CrearPro crearPro;
            try {
                crearPro = em.getReference(CrearPro.class, id);
                crearPro.getReferenciaProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crearPro with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleados = crearPro.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados.getCrearProList().remove(crearPro);
                idEmpleados = em.merge(idEmpleados);
            }
            List<Inventarios> inventariosList = crearPro.getInventariosList();
            for (Inventarios inventariosListInventarios : inventariosList) {
                inventariosListInventarios.setReferenciaProducto(null);
                inventariosListInventarios = em.merge(inventariosListInventarios);
            }
            em.remove(crearPro);
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

    public List<CrearPro> findCrearProEntities() {
        return findCrearProEntities(true, -1, -1);
    }

    public List<CrearPro> findCrearProEntities(int maxResults, int firstResult) {
        return findCrearProEntities(false, maxResults, firstResult);
    }

    private List<CrearPro> findCrearProEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CrearPro.class));
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

    public CrearPro findCrearPro(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CrearPro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCrearProCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CrearPro> rt = cq.from(CrearPro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
