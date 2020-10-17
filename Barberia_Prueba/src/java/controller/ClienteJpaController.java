/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Usuarios;
import entity.Cronograma;
import java.util.ArrayList;
import java.util.List;
import entity.Pqrs;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (cliente.getCronogramaList() == null) {
            cliente.setCronogramaList(new ArrayList<Cronograma>());
        }
        if (cliente.getPqrsList() == null) {
            cliente.setPqrsList(new ArrayList<Pqrs>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios numIdentificacion = cliente.getNumIdentificacion();
            if (numIdentificacion != null) {
                numIdentificacion = em.getReference(numIdentificacion.getClass(), numIdentificacion.getNumIdentificacion());
                cliente.setNumIdentificacion(numIdentificacion);
            }
            List<Cronograma> attachedCronogramaList = new ArrayList<Cronograma>();
            for (Cronograma cronogramaListCronogramaToAttach : cliente.getCronogramaList()) {
                cronogramaListCronogramaToAttach = em.getReference(cronogramaListCronogramaToAttach.getClass(), cronogramaListCronogramaToAttach.getIdCronograma());
                attachedCronogramaList.add(cronogramaListCronogramaToAttach);
            }
            cliente.setCronogramaList(attachedCronogramaList);
            List<Pqrs> attachedPqrsList = new ArrayList<Pqrs>();
            for (Pqrs pqrsListPqrsToAttach : cliente.getPqrsList()) {
                pqrsListPqrsToAttach = em.getReference(pqrsListPqrsToAttach.getClass(), pqrsListPqrsToAttach.getIdPqrs());
                attachedPqrsList.add(pqrsListPqrsToAttach);
            }
            cliente.setPqrsList(attachedPqrsList);
            em.persist(cliente);
            if (numIdentificacion != null) {
                numIdentificacion.getClienteList().add(cliente);
                numIdentificacion = em.merge(numIdentificacion);
            }
            for (Cronograma cronogramaListCronograma : cliente.getCronogramaList()) {
                Cliente oldIdClienteOfCronogramaListCronograma = cronogramaListCronograma.getIdCliente();
                cronogramaListCronograma.setIdCliente(cliente);
                cronogramaListCronograma = em.merge(cronogramaListCronograma);
                if (oldIdClienteOfCronogramaListCronograma != null) {
                    oldIdClienteOfCronogramaListCronograma.getCronogramaList().remove(cronogramaListCronograma);
                    oldIdClienteOfCronogramaListCronograma = em.merge(oldIdClienteOfCronogramaListCronograma);
                }
            }
            for (Pqrs pqrsListPqrs : cliente.getPqrsList()) {
                Cliente oldIdclienteOfPqrsListPqrs = pqrsListPqrs.getIdcliente();
                pqrsListPqrs.setIdcliente(cliente);
                pqrsListPqrs = em.merge(pqrsListPqrs);
                if (oldIdclienteOfPqrsListPqrs != null) {
                    oldIdclienteOfPqrsListPqrs.getPqrsList().remove(pqrsListPqrs);
                    oldIdclienteOfPqrsListPqrs = em.merge(oldIdclienteOfPqrsListPqrs);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCliente(cliente.getIdcliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            Usuarios numIdentificacionOld = persistentCliente.getNumIdentificacion();
            Usuarios numIdentificacionNew = cliente.getNumIdentificacion();
            List<Cronograma> cronogramaListOld = persistentCliente.getCronogramaList();
            List<Cronograma> cronogramaListNew = cliente.getCronogramaList();
            List<Pqrs> pqrsListOld = persistentCliente.getPqrsList();
            List<Pqrs> pqrsListNew = cliente.getPqrsList();
            if (numIdentificacionNew != null) {
                numIdentificacionNew = em.getReference(numIdentificacionNew.getClass(), numIdentificacionNew.getNumIdentificacion());
                cliente.setNumIdentificacion(numIdentificacionNew);
            }
            List<Cronograma> attachedCronogramaListNew = new ArrayList<Cronograma>();
            for (Cronograma cronogramaListNewCronogramaToAttach : cronogramaListNew) {
                cronogramaListNewCronogramaToAttach = em.getReference(cronogramaListNewCronogramaToAttach.getClass(), cronogramaListNewCronogramaToAttach.getIdCronograma());
                attachedCronogramaListNew.add(cronogramaListNewCronogramaToAttach);
            }
            cronogramaListNew = attachedCronogramaListNew;
            cliente.setCronogramaList(cronogramaListNew);
            List<Pqrs> attachedPqrsListNew = new ArrayList<Pqrs>();
            for (Pqrs pqrsListNewPqrsToAttach : pqrsListNew) {
                pqrsListNewPqrsToAttach = em.getReference(pqrsListNewPqrsToAttach.getClass(), pqrsListNewPqrsToAttach.getIdPqrs());
                attachedPqrsListNew.add(pqrsListNewPqrsToAttach);
            }
            pqrsListNew = attachedPqrsListNew;
            cliente.setPqrsList(pqrsListNew);
            cliente = em.merge(cliente);
            if (numIdentificacionOld != null && !numIdentificacionOld.equals(numIdentificacionNew)) {
                numIdentificacionOld.getClienteList().remove(cliente);
                numIdentificacionOld = em.merge(numIdentificacionOld);
            }
            if (numIdentificacionNew != null && !numIdentificacionNew.equals(numIdentificacionOld)) {
                numIdentificacionNew.getClienteList().add(cliente);
                numIdentificacionNew = em.merge(numIdentificacionNew);
            }
            for (Cronograma cronogramaListOldCronograma : cronogramaListOld) {
                if (!cronogramaListNew.contains(cronogramaListOldCronograma)) {
                    cronogramaListOldCronograma.setIdCliente(null);
                    cronogramaListOldCronograma = em.merge(cronogramaListOldCronograma);
                }
            }
            for (Cronograma cronogramaListNewCronograma : cronogramaListNew) {
                if (!cronogramaListOld.contains(cronogramaListNewCronograma)) {
                    Cliente oldIdClienteOfCronogramaListNewCronograma = cronogramaListNewCronograma.getIdCliente();
                    cronogramaListNewCronograma.setIdCliente(cliente);
                    cronogramaListNewCronograma = em.merge(cronogramaListNewCronograma);
                    if (oldIdClienteOfCronogramaListNewCronograma != null && !oldIdClienteOfCronogramaListNewCronograma.equals(cliente)) {
                        oldIdClienteOfCronogramaListNewCronograma.getCronogramaList().remove(cronogramaListNewCronograma);
                        oldIdClienteOfCronogramaListNewCronograma = em.merge(oldIdClienteOfCronogramaListNewCronograma);
                    }
                }
            }
            for (Pqrs pqrsListOldPqrs : pqrsListOld) {
                if (!pqrsListNew.contains(pqrsListOldPqrs)) {
                    pqrsListOldPqrs.setIdcliente(null);
                    pqrsListOldPqrs = em.merge(pqrsListOldPqrs);
                }
            }
            for (Pqrs pqrsListNewPqrs : pqrsListNew) {
                if (!pqrsListOld.contains(pqrsListNewPqrs)) {
                    Cliente oldIdclienteOfPqrsListNewPqrs = pqrsListNewPqrs.getIdcliente();
                    pqrsListNewPqrs.setIdcliente(cliente);
                    pqrsListNewPqrs = em.merge(pqrsListNewPqrs);
                    if (oldIdclienteOfPqrsListNewPqrs != null && !oldIdclienteOfPqrsListNewPqrs.equals(cliente)) {
                        oldIdclienteOfPqrsListNewPqrs.getPqrsList().remove(pqrsListNewPqrs);
                        oldIdclienteOfPqrsListNewPqrs = em.merge(oldIdclienteOfPqrsListNewPqrs);
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
                String id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Usuarios numIdentificacion = cliente.getNumIdentificacion();
            if (numIdentificacion != null) {
                numIdentificacion.getClienteList().remove(cliente);
                numIdentificacion = em.merge(numIdentificacion);
            }
            List<Cronograma> cronogramaList = cliente.getCronogramaList();
            for (Cronograma cronogramaListCronograma : cronogramaList) {
                cronogramaListCronograma.setIdCliente(null);
                cronogramaListCronograma = em.merge(cronogramaListCronograma);
            }
            List<Pqrs> pqrsList = cliente.getPqrsList();
            for (Pqrs pqrsListPqrs : pqrsList) {
                pqrsListPqrs.setIdcliente(null);
                pqrsListPqrs = em.merge(pqrsListPqrs);
            }
            em.remove(cliente);
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

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
