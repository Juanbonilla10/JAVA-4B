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
import entity.Roles;
import entity.Empleados;
import java.util.ArrayList;
import java.util.List;
import entity.Cliente;
import entity.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuarios.getEmpleadosList() == null) {
            usuarios.setEmpleadosList(new ArrayList<Empleados>());
        }
        if (usuarios.getClienteList() == null) {
            usuarios.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Roles codrol = usuarios.getCodrol();
            if (codrol != null) {
                codrol = em.getReference(codrol.getClass(), codrol.getCodrol());
                usuarios.setCodrol(codrol);
            }
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : usuarios.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleados());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            usuarios.setEmpleadosList(attachedEmpleadosList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : usuarios.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdcliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            usuarios.setClienteList(attachedClienteList);
            em.persist(usuarios);
            if (codrol != null) {
                codrol.getUsuariosList().add(usuarios);
                codrol = em.merge(codrol);
            }
            for (Empleados empleadosListEmpleados : usuarios.getEmpleadosList()) {
                Usuarios oldNumIdentificacionOfEmpleadosListEmpleados = empleadosListEmpleados.getNumIdentificacion();
                empleadosListEmpleados.setNumIdentificacion(usuarios);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldNumIdentificacionOfEmpleadosListEmpleados != null) {
                    oldNumIdentificacionOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldNumIdentificacionOfEmpleadosListEmpleados = em.merge(oldNumIdentificacionOfEmpleadosListEmpleados);
                }
            }
            for (Cliente clienteListCliente : usuarios.getClienteList()) {
                Usuarios oldNumIdentificacionOfClienteListCliente = clienteListCliente.getNumIdentificacion();
                clienteListCliente.setNumIdentificacion(usuarios);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldNumIdentificacionOfClienteListCliente != null) {
                    oldNumIdentificacionOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldNumIdentificacionOfClienteListCliente = em.merge(oldNumIdentificacionOfClienteListCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarios(usuarios.getNumIdentificacion()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getNumIdentificacion());
            Roles codrolOld = persistentUsuarios.getCodrol();
            Roles codrolNew = usuarios.getCodrol();
            List<Empleados> empleadosListOld = persistentUsuarios.getEmpleadosList();
            List<Empleados> empleadosListNew = usuarios.getEmpleadosList();
            List<Cliente> clienteListOld = persistentUsuarios.getClienteList();
            List<Cliente> clienteListNew = usuarios.getClienteList();
            if (codrolNew != null) {
                codrolNew = em.getReference(codrolNew.getClass(), codrolNew.getCodrol());
                usuarios.setCodrol(codrolNew);
            }
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleados());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            usuarios.setEmpleadosList(empleadosListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdcliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            usuarios.setClienteList(clienteListNew);
            usuarios = em.merge(usuarios);
            if (codrolOld != null && !codrolOld.equals(codrolNew)) {
                codrolOld.getUsuariosList().remove(usuarios);
                codrolOld = em.merge(codrolOld);
            }
            if (codrolNew != null && !codrolNew.equals(codrolOld)) {
                codrolNew.getUsuariosList().add(usuarios);
                codrolNew = em.merge(codrolNew);
            }
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    empleadosListOldEmpleados.setNumIdentificacion(null);
                    empleadosListOldEmpleados = em.merge(empleadosListOldEmpleados);
                }
            }
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    Usuarios oldNumIdentificacionOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getNumIdentificacion();
                    empleadosListNewEmpleados.setNumIdentificacion(usuarios);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldNumIdentificacionOfEmpleadosListNewEmpleados != null && !oldNumIdentificacionOfEmpleadosListNewEmpleados.equals(usuarios)) {
                        oldNumIdentificacionOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldNumIdentificacionOfEmpleadosListNewEmpleados = em.merge(oldNumIdentificacionOfEmpleadosListNewEmpleados);
                    }
                }
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setNumIdentificacion(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Usuarios oldNumIdentificacionOfClienteListNewCliente = clienteListNewCliente.getNumIdentificacion();
                    clienteListNewCliente.setNumIdentificacion(usuarios);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldNumIdentificacionOfClienteListNewCliente != null && !oldNumIdentificacionOfClienteListNewCliente.equals(usuarios)) {
                        oldNumIdentificacionOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldNumIdentificacionOfClienteListNewCliente = em.merge(oldNumIdentificacionOfClienteListNewCliente);
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
                Integer id = usuarios.getNumIdentificacion();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getNumIdentificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Roles codrol = usuarios.getCodrol();
            if (codrol != null) {
                codrol.getUsuariosList().remove(usuarios);
                codrol = em.merge(codrol);
            }
            List<Empleados> empleadosList = usuarios.getEmpleadosList();
            for (Empleados empleadosListEmpleados : empleadosList) {
                empleadosListEmpleados.setNumIdentificacion(null);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
            }
            List<Cliente> clienteList = usuarios.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setNumIdentificacion(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(usuarios);
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

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
