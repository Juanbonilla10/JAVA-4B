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
import entity.Empleados;
import entity.Cliente;
import entity.CategoriaCronograma;
import entity.Citas;
import entity.Cronograma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class CronogramaJpaController implements Serializable {

    public CronogramaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cronograma cronograma) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (cronograma.getCitasList() == null) {
            cronograma.setCitasList(new ArrayList<Citas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleados idEmpleados = cronograma.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados = em.getReference(idEmpleados.getClass(), idEmpleados.getIdEmpleados());
                cronograma.setIdEmpleados(idEmpleados);
            }
            Cliente idCliente = cronograma.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdcliente());
                cronograma.setIdCliente(idCliente);
            }
            CategoriaCronograma idcatecro = cronograma.getIdcatecro();
            if (idcatecro != null) {
                idcatecro = em.getReference(idcatecro.getClass(), idcatecro.getIdcatecro());
                cronograma.setIdcatecro(idcatecro);
            }
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : cronograma.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getIdCitas());
                attachedCitasList.add(citasListCitasToAttach);
            }
            cronograma.setCitasList(attachedCitasList);
            em.persist(cronograma);
            if (idEmpleados != null) {
                idEmpleados.getCronogramaList().add(cronograma);
                idEmpleados = em.merge(idEmpleados);
            }
            if (idCliente != null) {
                idCliente.getCronogramaList().add(cronograma);
                idCliente = em.merge(idCliente);
            }
            if (idcatecro != null) {
                idcatecro.getCronogramaList().add(cronograma);
                idcatecro = em.merge(idcatecro);
            }
            for (Citas citasListCitas : cronograma.getCitasList()) {
                Cronograma oldIdcronogramaOfCitasListCitas = citasListCitas.getIdcronograma();
                citasListCitas.setIdcronograma(cronograma);
                citasListCitas = em.merge(citasListCitas);
                if (oldIdcronogramaOfCitasListCitas != null) {
                    oldIdcronogramaOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldIdcronogramaOfCitasListCitas = em.merge(oldIdcronogramaOfCitasListCitas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCronograma(cronograma.getIdCronograma()) != null) {
                throw new PreexistingEntityException("Cronograma " + cronograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cronograma cronograma) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cronograma persistentCronograma = em.find(Cronograma.class, cronograma.getIdCronograma());
            Empleados idEmpleadosOld = persistentCronograma.getIdEmpleados();
            Empleados idEmpleadosNew = cronograma.getIdEmpleados();
            Cliente idClienteOld = persistentCronograma.getIdCliente();
            Cliente idClienteNew = cronograma.getIdCliente();
            CategoriaCronograma idcatecroOld = persistentCronograma.getIdcatecro();
            CategoriaCronograma idcatecroNew = cronograma.getIdcatecro();
            List<Citas> citasListOld = persistentCronograma.getCitasList();
            List<Citas> citasListNew = cronograma.getCitasList();
            if (idEmpleadosNew != null) {
                idEmpleadosNew = em.getReference(idEmpleadosNew.getClass(), idEmpleadosNew.getIdEmpleados());
                cronograma.setIdEmpleados(idEmpleadosNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdcliente());
                cronograma.setIdCliente(idClienteNew);
            }
            if (idcatecroNew != null) {
                idcatecroNew = em.getReference(idcatecroNew.getClass(), idcatecroNew.getIdcatecro());
                cronograma.setIdcatecro(idcatecroNew);
            }
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getIdCitas());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            cronograma.setCitasList(citasListNew);
            cronograma = em.merge(cronograma);
            if (idEmpleadosOld != null && !idEmpleadosOld.equals(idEmpleadosNew)) {
                idEmpleadosOld.getCronogramaList().remove(cronograma);
                idEmpleadosOld = em.merge(idEmpleadosOld);
            }
            if (idEmpleadosNew != null && !idEmpleadosNew.equals(idEmpleadosOld)) {
                idEmpleadosNew.getCronogramaList().add(cronograma);
                idEmpleadosNew = em.merge(idEmpleadosNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getCronogramaList().remove(cronograma);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getCronogramaList().add(cronograma);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idcatecroOld != null && !idcatecroOld.equals(idcatecroNew)) {
                idcatecroOld.getCronogramaList().remove(cronograma);
                idcatecroOld = em.merge(idcatecroOld);
            }
            if (idcatecroNew != null && !idcatecroNew.equals(idcatecroOld)) {
                idcatecroNew.getCronogramaList().add(cronograma);
                idcatecroNew = em.merge(idcatecroNew);
            }
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    citasListOldCitas.setIdcronograma(null);
                    citasListOldCitas = em.merge(citasListOldCitas);
                }
            }
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Cronograma oldIdcronogramaOfCitasListNewCitas = citasListNewCitas.getIdcronograma();
                    citasListNewCitas.setIdcronograma(cronograma);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldIdcronogramaOfCitasListNewCitas != null && !oldIdcronogramaOfCitasListNewCitas.equals(cronograma)) {
                        oldIdcronogramaOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldIdcronogramaOfCitasListNewCitas = em.merge(oldIdcronogramaOfCitasListNewCitas);
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
                String id = cronograma.getIdCronograma();
                if (findCronograma(id) == null) {
                    throw new NonexistentEntityException("The cronograma with id " + id + " no longer exists.");
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
            Cronograma cronograma;
            try {
                cronograma = em.getReference(Cronograma.class, id);
                cronograma.getIdCronograma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cronograma with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleados = cronograma.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados.getCronogramaList().remove(cronograma);
                idEmpleados = em.merge(idEmpleados);
            }
            Cliente idCliente = cronograma.getIdCliente();
            if (idCliente != null) {
                idCliente.getCronogramaList().remove(cronograma);
                idCliente = em.merge(idCliente);
            }
            CategoriaCronograma idcatecro = cronograma.getIdcatecro();
            if (idcatecro != null) {
                idcatecro.getCronogramaList().remove(cronograma);
                idcatecro = em.merge(idcatecro);
            }
            List<Citas> citasList = cronograma.getCitasList();
            for (Citas citasListCitas : citasList) {
                citasListCitas.setIdcronograma(null);
                citasListCitas = em.merge(citasListCitas);
            }
            em.remove(cronograma);
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

    public List<Cronograma> findCronogramaEntities() {
        return findCronogramaEntities(true, -1, -1);
    }

    public List<Cronograma> findCronogramaEntities(int maxResults, int firstResult) {
        return findCronogramaEntities(false, maxResults, firstResult);
    }

    private List<Cronograma> findCronogramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cronograma.class));
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

    public Cronograma findCronograma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cronograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getCronogramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cronograma> rt = cq.from(Cronograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
