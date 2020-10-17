/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import entity.CategoriaCronograma;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class CategoriaCronogramaJpaController implements Serializable {

    public CategoriaCronogramaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaCronograma categoriaCronograma) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (categoriaCronograma.getCronogramaList() == null) {
            categoriaCronograma.setCronogramaList(new ArrayList<Cronograma>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cronograma> attachedCronogramaList = new ArrayList<Cronograma>();
            for (Cronograma cronogramaListCronogramaToAttach : categoriaCronograma.getCronogramaList()) {
                cronogramaListCronogramaToAttach = em.getReference(cronogramaListCronogramaToAttach.getClass(), cronogramaListCronogramaToAttach.getIdCronograma());
                attachedCronogramaList.add(cronogramaListCronogramaToAttach);
            }
            categoriaCronograma.setCronogramaList(attachedCronogramaList);
            em.persist(categoriaCronograma);
            for (Cronograma cronogramaListCronograma : categoriaCronograma.getCronogramaList()) {
                CategoriaCronograma oldIdcatecroOfCronogramaListCronograma = cronogramaListCronograma.getIdcatecro();
                cronogramaListCronograma.setIdcatecro(categoriaCronograma);
                cronogramaListCronograma = em.merge(cronogramaListCronograma);
                if (oldIdcatecroOfCronogramaListCronograma != null) {
                    oldIdcatecroOfCronogramaListCronograma.getCronogramaList().remove(cronogramaListCronograma);
                    oldIdcatecroOfCronogramaListCronograma = em.merge(oldIdcatecroOfCronogramaListCronograma);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCategoriaCronograma(categoriaCronograma.getIdcatecro()) != null) {
                throw new PreexistingEntityException("CategoriaCronograma " + categoriaCronograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaCronograma categoriaCronograma) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaCronograma persistentCategoriaCronograma = em.find(CategoriaCronograma.class, categoriaCronograma.getIdcatecro());
            List<Cronograma> cronogramaListOld = persistentCategoriaCronograma.getCronogramaList();
            List<Cronograma> cronogramaListNew = categoriaCronograma.getCronogramaList();
            List<Cronograma> attachedCronogramaListNew = new ArrayList<Cronograma>();
            for (Cronograma cronogramaListNewCronogramaToAttach : cronogramaListNew) {
                cronogramaListNewCronogramaToAttach = em.getReference(cronogramaListNewCronogramaToAttach.getClass(), cronogramaListNewCronogramaToAttach.getIdCronograma());
                attachedCronogramaListNew.add(cronogramaListNewCronogramaToAttach);
            }
            cronogramaListNew = attachedCronogramaListNew;
            categoriaCronograma.setCronogramaList(cronogramaListNew);
            categoriaCronograma = em.merge(categoriaCronograma);
            for (Cronograma cronogramaListOldCronograma : cronogramaListOld) {
                if (!cronogramaListNew.contains(cronogramaListOldCronograma)) {
                    cronogramaListOldCronograma.setIdcatecro(null);
                    cronogramaListOldCronograma = em.merge(cronogramaListOldCronograma);
                }
            }
            for (Cronograma cronogramaListNewCronograma : cronogramaListNew) {
                if (!cronogramaListOld.contains(cronogramaListNewCronograma)) {
                    CategoriaCronograma oldIdcatecroOfCronogramaListNewCronograma = cronogramaListNewCronograma.getIdcatecro();
                    cronogramaListNewCronograma.setIdcatecro(categoriaCronograma);
                    cronogramaListNewCronograma = em.merge(cronogramaListNewCronograma);
                    if (oldIdcatecroOfCronogramaListNewCronograma != null && !oldIdcatecroOfCronogramaListNewCronograma.equals(categoriaCronograma)) {
                        oldIdcatecroOfCronogramaListNewCronograma.getCronogramaList().remove(cronogramaListNewCronograma);
                        oldIdcatecroOfCronogramaListNewCronograma = em.merge(oldIdcatecroOfCronogramaListNewCronograma);
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
                String id = categoriaCronograma.getIdcatecro();
                if (findCategoriaCronograma(id) == null) {
                    throw new NonexistentEntityException("The categoriaCronograma with id " + id + " no longer exists.");
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
            CategoriaCronograma categoriaCronograma;
            try {
                categoriaCronograma = em.getReference(CategoriaCronograma.class, id);
                categoriaCronograma.getIdcatecro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaCronograma with id " + id + " no longer exists.", enfe);
            }
            List<Cronograma> cronogramaList = categoriaCronograma.getCronogramaList();
            for (Cronograma cronogramaListCronograma : cronogramaList) {
                cronogramaListCronograma.setIdcatecro(null);
                cronogramaListCronograma = em.merge(cronogramaListCronograma);
            }
            em.remove(categoriaCronograma);
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

    public List<CategoriaCronograma> findCategoriaCronogramaEntities() {
        return findCategoriaCronogramaEntities(true, -1, -1);
    }

    public List<CategoriaCronograma> findCategoriaCronogramaEntities(int maxResults, int firstResult) {
        return findCategoriaCronogramaEntities(false, maxResults, firstResult);
    }

    private List<CategoriaCronograma> findCategoriaCronogramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaCronograma.class));
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

    public CategoriaCronograma findCategoriaCronograma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaCronograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCronogramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaCronograma> rt = cq.from(CategoriaCronograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
