/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.PagoServicio;
import java.util.ArrayList;
import java.util.List;
import entity.Servicios;
import entity.Pqrs;
import entity.TipoServicio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class TipoServicioJpaController implements Serializable {

    public TipoServicioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoServicio tipoServicio) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoServicio.getPagoServicioList() == null) {
            tipoServicio.setPagoServicioList(new ArrayList<PagoServicio>());
        }
        if (tipoServicio.getServiciosList() == null) {
            tipoServicio.setServiciosList(new ArrayList<Servicios>());
        }
        if (tipoServicio.getPqrsList() == null) {
            tipoServicio.setPqrsList(new ArrayList<Pqrs>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<PagoServicio> attachedPagoServicioList = new ArrayList<PagoServicio>();
            for (PagoServicio pagoServicioListPagoServicioToAttach : tipoServicio.getPagoServicioList()) {
                pagoServicioListPagoServicioToAttach = em.getReference(pagoServicioListPagoServicioToAttach.getClass(), pagoServicioListPagoServicioToAttach.getCodigodelpago());
                attachedPagoServicioList.add(pagoServicioListPagoServicioToAttach);
            }
            tipoServicio.setPagoServicioList(attachedPagoServicioList);
            List<Servicios> attachedServiciosList = new ArrayList<Servicios>();
            for (Servicios serviciosListServiciosToAttach : tipoServicio.getServiciosList()) {
                serviciosListServiciosToAttach = em.getReference(serviciosListServiciosToAttach.getClass(), serviciosListServiciosToAttach.getIdRegisSer());
                attachedServiciosList.add(serviciosListServiciosToAttach);
            }
            tipoServicio.setServiciosList(attachedServiciosList);
            List<Pqrs> attachedPqrsList = new ArrayList<Pqrs>();
            for (Pqrs pqrsListPqrsToAttach : tipoServicio.getPqrsList()) {
                pqrsListPqrsToAttach = em.getReference(pqrsListPqrsToAttach.getClass(), pqrsListPqrsToAttach.getIdPqrs());
                attachedPqrsList.add(pqrsListPqrsToAttach);
            }
            tipoServicio.setPqrsList(attachedPqrsList);
            em.persist(tipoServicio);
            for (PagoServicio pagoServicioListPagoServicio : tipoServicio.getPagoServicioList()) {
                TipoServicio oldIdtipoServicioOfPagoServicioListPagoServicio = pagoServicioListPagoServicio.getIdtipoServicio();
                pagoServicioListPagoServicio.setIdtipoServicio(tipoServicio);
                pagoServicioListPagoServicio = em.merge(pagoServicioListPagoServicio);
                if (oldIdtipoServicioOfPagoServicioListPagoServicio != null) {
                    oldIdtipoServicioOfPagoServicioListPagoServicio.getPagoServicioList().remove(pagoServicioListPagoServicio);
                    oldIdtipoServicioOfPagoServicioListPagoServicio = em.merge(oldIdtipoServicioOfPagoServicioListPagoServicio);
                }
            }
            for (Servicios serviciosListServicios : tipoServicio.getServiciosList()) {
                TipoServicio oldIdtipoServicioOfServiciosListServicios = serviciosListServicios.getIdtipoServicio();
                serviciosListServicios.setIdtipoServicio(tipoServicio);
                serviciosListServicios = em.merge(serviciosListServicios);
                if (oldIdtipoServicioOfServiciosListServicios != null) {
                    oldIdtipoServicioOfServiciosListServicios.getServiciosList().remove(serviciosListServicios);
                    oldIdtipoServicioOfServiciosListServicios = em.merge(oldIdtipoServicioOfServiciosListServicios);
                }
            }
            for (Pqrs pqrsListPqrs : tipoServicio.getPqrsList()) {
                TipoServicio oldIdtipoServicioOfPqrsListPqrs = pqrsListPqrs.getIdtipoServicio();
                pqrsListPqrs.setIdtipoServicio(tipoServicio);
                pqrsListPqrs = em.merge(pqrsListPqrs);
                if (oldIdtipoServicioOfPqrsListPqrs != null) {
                    oldIdtipoServicioOfPqrsListPqrs.getPqrsList().remove(pqrsListPqrs);
                    oldIdtipoServicioOfPqrsListPqrs = em.merge(oldIdtipoServicioOfPqrsListPqrs);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoServicio(tipoServicio.getIdtipoServicio()) != null) {
                throw new PreexistingEntityException("TipoServicio " + tipoServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoServicio tipoServicio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoServicio persistentTipoServicio = em.find(TipoServicio.class, tipoServicio.getIdtipoServicio());
            List<PagoServicio> pagoServicioListOld = persistentTipoServicio.getPagoServicioList();
            List<PagoServicio> pagoServicioListNew = tipoServicio.getPagoServicioList();
            List<Servicios> serviciosListOld = persistentTipoServicio.getServiciosList();
            List<Servicios> serviciosListNew = tipoServicio.getServiciosList();
            List<Pqrs> pqrsListOld = persistentTipoServicio.getPqrsList();
            List<Pqrs> pqrsListNew = tipoServicio.getPqrsList();
            List<String> illegalOrphanMessages = null;
            for (Servicios serviciosListOldServicios : serviciosListOld) {
                if (!serviciosListNew.contains(serviciosListOldServicios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Servicios " + serviciosListOldServicios + " since its idtipoServicio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PagoServicio> attachedPagoServicioListNew = new ArrayList<PagoServicio>();
            for (PagoServicio pagoServicioListNewPagoServicioToAttach : pagoServicioListNew) {
                pagoServicioListNewPagoServicioToAttach = em.getReference(pagoServicioListNewPagoServicioToAttach.getClass(), pagoServicioListNewPagoServicioToAttach.getCodigodelpago());
                attachedPagoServicioListNew.add(pagoServicioListNewPagoServicioToAttach);
            }
            pagoServicioListNew = attachedPagoServicioListNew;
            tipoServicio.setPagoServicioList(pagoServicioListNew);
            List<Servicios> attachedServiciosListNew = new ArrayList<Servicios>();
            for (Servicios serviciosListNewServiciosToAttach : serviciosListNew) {
                serviciosListNewServiciosToAttach = em.getReference(serviciosListNewServiciosToAttach.getClass(), serviciosListNewServiciosToAttach.getIdRegisSer());
                attachedServiciosListNew.add(serviciosListNewServiciosToAttach);
            }
            serviciosListNew = attachedServiciosListNew;
            tipoServicio.setServiciosList(serviciosListNew);
            List<Pqrs> attachedPqrsListNew = new ArrayList<Pqrs>();
            for (Pqrs pqrsListNewPqrsToAttach : pqrsListNew) {
                pqrsListNewPqrsToAttach = em.getReference(pqrsListNewPqrsToAttach.getClass(), pqrsListNewPqrsToAttach.getIdPqrs());
                attachedPqrsListNew.add(pqrsListNewPqrsToAttach);
            }
            pqrsListNew = attachedPqrsListNew;
            tipoServicio.setPqrsList(pqrsListNew);
            tipoServicio = em.merge(tipoServicio);
            for (PagoServicio pagoServicioListOldPagoServicio : pagoServicioListOld) {
                if (!pagoServicioListNew.contains(pagoServicioListOldPagoServicio)) {
                    pagoServicioListOldPagoServicio.setIdtipoServicio(null);
                    pagoServicioListOldPagoServicio = em.merge(pagoServicioListOldPagoServicio);
                }
            }
            for (PagoServicio pagoServicioListNewPagoServicio : pagoServicioListNew) {
                if (!pagoServicioListOld.contains(pagoServicioListNewPagoServicio)) {
                    TipoServicio oldIdtipoServicioOfPagoServicioListNewPagoServicio = pagoServicioListNewPagoServicio.getIdtipoServicio();
                    pagoServicioListNewPagoServicio.setIdtipoServicio(tipoServicio);
                    pagoServicioListNewPagoServicio = em.merge(pagoServicioListNewPagoServicio);
                    if (oldIdtipoServicioOfPagoServicioListNewPagoServicio != null && !oldIdtipoServicioOfPagoServicioListNewPagoServicio.equals(tipoServicio)) {
                        oldIdtipoServicioOfPagoServicioListNewPagoServicio.getPagoServicioList().remove(pagoServicioListNewPagoServicio);
                        oldIdtipoServicioOfPagoServicioListNewPagoServicio = em.merge(oldIdtipoServicioOfPagoServicioListNewPagoServicio);
                    }
                }
            }
            for (Servicios serviciosListNewServicios : serviciosListNew) {
                if (!serviciosListOld.contains(serviciosListNewServicios)) {
                    TipoServicio oldIdtipoServicioOfServiciosListNewServicios = serviciosListNewServicios.getIdtipoServicio();
                    serviciosListNewServicios.setIdtipoServicio(tipoServicio);
                    serviciosListNewServicios = em.merge(serviciosListNewServicios);
                    if (oldIdtipoServicioOfServiciosListNewServicios != null && !oldIdtipoServicioOfServiciosListNewServicios.equals(tipoServicio)) {
                        oldIdtipoServicioOfServiciosListNewServicios.getServiciosList().remove(serviciosListNewServicios);
                        oldIdtipoServicioOfServiciosListNewServicios = em.merge(oldIdtipoServicioOfServiciosListNewServicios);
                    }
                }
            }
            for (Pqrs pqrsListOldPqrs : pqrsListOld) {
                if (!pqrsListNew.contains(pqrsListOldPqrs)) {
                    pqrsListOldPqrs.setIdtipoServicio(null);
                    pqrsListOldPqrs = em.merge(pqrsListOldPqrs);
                }
            }
            for (Pqrs pqrsListNewPqrs : pqrsListNew) {
                if (!pqrsListOld.contains(pqrsListNewPqrs)) {
                    TipoServicio oldIdtipoServicioOfPqrsListNewPqrs = pqrsListNewPqrs.getIdtipoServicio();
                    pqrsListNewPqrs.setIdtipoServicio(tipoServicio);
                    pqrsListNewPqrs = em.merge(pqrsListNewPqrs);
                    if (oldIdtipoServicioOfPqrsListNewPqrs != null && !oldIdtipoServicioOfPqrsListNewPqrs.equals(tipoServicio)) {
                        oldIdtipoServicioOfPqrsListNewPqrs.getPqrsList().remove(pqrsListNewPqrs);
                        oldIdtipoServicioOfPqrsListNewPqrs = em.merge(oldIdtipoServicioOfPqrsListNewPqrs);
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
                String id = tipoServicio.getIdtipoServicio();
                if (findTipoServicio(id) == null) {
                    throw new NonexistentEntityException("The tipoServicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoServicio tipoServicio;
            try {
                tipoServicio = em.getReference(TipoServicio.class, id);
                tipoServicio.getIdtipoServicio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoServicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Servicios> serviciosListOrphanCheck = tipoServicio.getServiciosList();
            for (Servicios serviciosListOrphanCheckServicios : serviciosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoServicio (" + tipoServicio + ") cannot be destroyed since the Servicios " + serviciosListOrphanCheckServicios + " in its serviciosList field has a non-nullable idtipoServicio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PagoServicio> pagoServicioList = tipoServicio.getPagoServicioList();
            for (PagoServicio pagoServicioListPagoServicio : pagoServicioList) {
                pagoServicioListPagoServicio.setIdtipoServicio(null);
                pagoServicioListPagoServicio = em.merge(pagoServicioListPagoServicio);
            }
            List<Pqrs> pqrsList = tipoServicio.getPqrsList();
            for (Pqrs pqrsListPqrs : pqrsList) {
                pqrsListPqrs.setIdtipoServicio(null);
                pqrsListPqrs = em.merge(pqrsListPqrs);
            }
            em.remove(tipoServicio);
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

    public List<TipoServicio> findTipoServicioEntities() {
        return findTipoServicioEntities(true, -1, -1);
    }

    public List<TipoServicio> findTipoServicioEntities(int maxResults, int firstResult) {
        return findTipoServicioEntities(false, maxResults, firstResult);
    }

    private List<TipoServicio> findTipoServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoServicio.class));
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

    public TipoServicio findTipoServicio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoServicio> rt = cq.from(TipoServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
