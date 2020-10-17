/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.Citas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Cronograma;
import entity.Servicios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class CitasJpaController implements Serializable {

    public CitasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citas citas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (citas.getServiciosList() == null) {
            citas.setServiciosList(new ArrayList<Servicios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cronograma idcronograma = citas.getIdcronograma();
            if (idcronograma != null) {
                idcronograma = em.getReference(idcronograma.getClass(), idcronograma.getIdCronograma());
                citas.setIdcronograma(idcronograma);
            }
            List<Servicios> attachedServiciosList = new ArrayList<Servicios>();
            for (Servicios serviciosListServiciosToAttach : citas.getServiciosList()) {
                serviciosListServiciosToAttach = em.getReference(serviciosListServiciosToAttach.getClass(), serviciosListServiciosToAttach.getIdRegisSer());
                attachedServiciosList.add(serviciosListServiciosToAttach);
            }
            citas.setServiciosList(attachedServiciosList);
            em.persist(citas);
            if (idcronograma != null) {
                idcronograma.getCitasList().add(citas);
                idcronograma = em.merge(idcronograma);
            }
            for (Servicios serviciosListServicios : citas.getServiciosList()) {
                Citas oldIdcitasOfServiciosListServicios = serviciosListServicios.getIdcitas();
                serviciosListServicios.setIdcitas(citas);
                serviciosListServicios = em.merge(serviciosListServicios);
                if (oldIdcitasOfServiciosListServicios != null) {
                    oldIdcitasOfServiciosListServicios.getServiciosList().remove(serviciosListServicios);
                    oldIdcitasOfServiciosListServicios = em.merge(oldIdcitasOfServiciosListServicios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCitas(citas.getIdCitas()) != null) {
                throw new PreexistingEntityException("Citas " + citas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citas citas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Citas persistentCitas = em.find(Citas.class, citas.getIdCitas());
            Cronograma idcronogramaOld = persistentCitas.getIdcronograma();
            Cronograma idcronogramaNew = citas.getIdcronograma();
            List<Servicios> serviciosListOld = persistentCitas.getServiciosList();
            List<Servicios> serviciosListNew = citas.getServiciosList();
            if (idcronogramaNew != null) {
                idcronogramaNew = em.getReference(idcronogramaNew.getClass(), idcronogramaNew.getIdCronograma());
                citas.setIdcronograma(idcronogramaNew);
            }
            List<Servicios> attachedServiciosListNew = new ArrayList<Servicios>();
            for (Servicios serviciosListNewServiciosToAttach : serviciosListNew) {
                serviciosListNewServiciosToAttach = em.getReference(serviciosListNewServiciosToAttach.getClass(), serviciosListNewServiciosToAttach.getIdRegisSer());
                attachedServiciosListNew.add(serviciosListNewServiciosToAttach);
            }
            serviciosListNew = attachedServiciosListNew;
            citas.setServiciosList(serviciosListNew);
            citas = em.merge(citas);
            if (idcronogramaOld != null && !idcronogramaOld.equals(idcronogramaNew)) {
                idcronogramaOld.getCitasList().remove(citas);
                idcronogramaOld = em.merge(idcronogramaOld);
            }
            if (idcronogramaNew != null && !idcronogramaNew.equals(idcronogramaOld)) {
                idcronogramaNew.getCitasList().add(citas);
                idcronogramaNew = em.merge(idcronogramaNew);
            }
            for (Servicios serviciosListOldServicios : serviciosListOld) {
                if (!serviciosListNew.contains(serviciosListOldServicios)) {
                    serviciosListOldServicios.setIdcitas(null);
                    serviciosListOldServicios = em.merge(serviciosListOldServicios);
                }
            }
            for (Servicios serviciosListNewServicios : serviciosListNew) {
                if (!serviciosListOld.contains(serviciosListNewServicios)) {
                    Citas oldIdcitasOfServiciosListNewServicios = serviciosListNewServicios.getIdcitas();
                    serviciosListNewServicios.setIdcitas(citas);
                    serviciosListNewServicios = em.merge(serviciosListNewServicios);
                    if (oldIdcitasOfServiciosListNewServicios != null && !oldIdcitasOfServiciosListNewServicios.equals(citas)) {
                        oldIdcitasOfServiciosListNewServicios.getServiciosList().remove(serviciosListNewServicios);
                        oldIdcitasOfServiciosListNewServicios = em.merge(oldIdcitasOfServiciosListNewServicios);
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
                String id = citas.getIdCitas();
                if (findCitas(id) == null) {
                    throw new NonexistentEntityException("The citas with id " + id + " no longer exists.");
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
            Citas citas;
            try {
                citas = em.getReference(Citas.class, id);
                citas.getIdCitas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citas with id " + id + " no longer exists.", enfe);
            }
            Cronograma idcronograma = citas.getIdcronograma();
            if (idcronograma != null) {
                idcronograma.getCitasList().remove(citas);
                idcronograma = em.merge(idcronograma);
            }
            List<Servicios> serviciosList = citas.getServiciosList();
            for (Servicios serviciosListServicios : serviciosList) {
                serviciosListServicios.setIdcitas(null);
                serviciosListServicios = em.merge(serviciosListServicios);
            }
            em.remove(citas);
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

    public List<Citas> findCitasEntities() {
        return findCitasEntities(true, -1, -1);
    }

    public List<Citas> findCitasEntities(int maxResults, int firstResult) {
        return findCitasEntities(false, maxResults, firstResult);
    }

    private List<Citas> findCitasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citas.class));
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

    public Citas findCitas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citas> rt = cq.from(Citas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
