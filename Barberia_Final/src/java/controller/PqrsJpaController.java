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
import entity.TipoServicio;
import entity.Cliente;
import entity.Pqrs;
import entity.Respuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author soporte
 */
public class PqrsJpaController implements Serializable {

    public PqrsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pqrs pqrs) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (pqrs.getRespuestaList() == null) {
            pqrs.setRespuestaList(new ArrayList<Respuesta>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoServicio idtipoServicio = pqrs.getIdtipoServicio();
            if (idtipoServicio != null) {
                idtipoServicio = em.getReference(idtipoServicio.getClass(), idtipoServicio.getIdtipoServicio());
                pqrs.setIdtipoServicio(idtipoServicio);
            }
            Cliente idcliente = pqrs.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                pqrs.setIdcliente(idcliente);
            }
            List<Respuesta> attachedRespuestaList = new ArrayList<Respuesta>();
            for (Respuesta respuestaListRespuestaToAttach : pqrs.getRespuestaList()) {
                respuestaListRespuestaToAttach = em.getReference(respuestaListRespuestaToAttach.getClass(), respuestaListRespuestaToAttach.getIdrespuesta());
                attachedRespuestaList.add(respuestaListRespuestaToAttach);
            }
            pqrs.setRespuestaList(attachedRespuestaList);
            em.persist(pqrs);
            if (idtipoServicio != null) {
                idtipoServicio.getPqrsList().add(pqrs);
                idtipoServicio = em.merge(idtipoServicio);
            }
            if (idcliente != null) {
                idcliente.getPqrsList().add(pqrs);
                idcliente = em.merge(idcliente);
            }
            for (Respuesta respuestaListRespuesta : pqrs.getRespuestaList()) {
                Pqrs oldIdPqrsOfRespuestaListRespuesta = respuestaListRespuesta.getIdPqrs();
                respuestaListRespuesta.setIdPqrs(pqrs);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
                if (oldIdPqrsOfRespuestaListRespuesta != null) {
                    oldIdPqrsOfRespuestaListRespuesta.getRespuestaList().remove(respuestaListRespuesta);
                    oldIdPqrsOfRespuestaListRespuesta = em.merge(oldIdPqrsOfRespuestaListRespuesta);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPqrs(pqrs.getIdPqrs()) != null) {
                throw new PreexistingEntityException("Pqrs " + pqrs + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pqrs pqrs) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pqrs persistentPqrs = em.find(Pqrs.class, pqrs.getIdPqrs());
            TipoServicio idtipoServicioOld = persistentPqrs.getIdtipoServicio();
            TipoServicio idtipoServicioNew = pqrs.getIdtipoServicio();
            Cliente idclienteOld = persistentPqrs.getIdcliente();
            Cliente idclienteNew = pqrs.getIdcliente();
            List<Respuesta> respuestaListOld = persistentPqrs.getRespuestaList();
            List<Respuesta> respuestaListNew = pqrs.getRespuestaList();
            List<String> illegalOrphanMessages = null;
            for (Respuesta respuestaListOldRespuesta : respuestaListOld) {
                if (!respuestaListNew.contains(respuestaListOldRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuesta " + respuestaListOldRespuesta + " since its idPqrs field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idtipoServicioNew != null) {
                idtipoServicioNew = em.getReference(idtipoServicioNew.getClass(), idtipoServicioNew.getIdtipoServicio());
                pqrs.setIdtipoServicio(idtipoServicioNew);
            }
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                pqrs.setIdcliente(idclienteNew);
            }
            List<Respuesta> attachedRespuestaListNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaListNewRespuestaToAttach : respuestaListNew) {
                respuestaListNewRespuestaToAttach = em.getReference(respuestaListNewRespuestaToAttach.getClass(), respuestaListNewRespuestaToAttach.getIdrespuesta());
                attachedRespuestaListNew.add(respuestaListNewRespuestaToAttach);
            }
            respuestaListNew = attachedRespuestaListNew;
            pqrs.setRespuestaList(respuestaListNew);
            pqrs = em.merge(pqrs);
            if (idtipoServicioOld != null && !idtipoServicioOld.equals(idtipoServicioNew)) {
                idtipoServicioOld.getPqrsList().remove(pqrs);
                idtipoServicioOld = em.merge(idtipoServicioOld);
            }
            if (idtipoServicioNew != null && !idtipoServicioNew.equals(idtipoServicioOld)) {
                idtipoServicioNew.getPqrsList().add(pqrs);
                idtipoServicioNew = em.merge(idtipoServicioNew);
            }
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getPqrsList().remove(pqrs);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getPqrsList().add(pqrs);
                idclienteNew = em.merge(idclienteNew);
            }
            for (Respuesta respuestaListNewRespuesta : respuestaListNew) {
                if (!respuestaListOld.contains(respuestaListNewRespuesta)) {
                    Pqrs oldIdPqrsOfRespuestaListNewRespuesta = respuestaListNewRespuesta.getIdPqrs();
                    respuestaListNewRespuesta.setIdPqrs(pqrs);
                    respuestaListNewRespuesta = em.merge(respuestaListNewRespuesta);
                    if (oldIdPqrsOfRespuestaListNewRespuesta != null && !oldIdPqrsOfRespuestaListNewRespuesta.equals(pqrs)) {
                        oldIdPqrsOfRespuestaListNewRespuesta.getRespuestaList().remove(respuestaListNewRespuesta);
                        oldIdPqrsOfRespuestaListNewRespuesta = em.merge(oldIdPqrsOfRespuestaListNewRespuesta);
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
                String id = pqrs.getIdPqrs();
                if (findPqrs(id) == null) {
                    throw new NonexistentEntityException("The pqrs with id " + id + " no longer exists.");
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
            Pqrs pqrs;
            try {
                pqrs = em.getReference(Pqrs.class, id);
                pqrs.getIdPqrs();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pqrs with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Respuesta> respuestaListOrphanCheck = pqrs.getRespuestaList();
            for (Respuesta respuestaListOrphanCheckRespuesta : respuestaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pqrs (" + pqrs + ") cannot be destroyed since the Respuesta " + respuestaListOrphanCheckRespuesta + " in its respuestaList field has a non-nullable idPqrs field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoServicio idtipoServicio = pqrs.getIdtipoServicio();
            if (idtipoServicio != null) {
                idtipoServicio.getPqrsList().remove(pqrs);
                idtipoServicio = em.merge(idtipoServicio);
            }
            Cliente idcliente = pqrs.getIdcliente();
            if (idcliente != null) {
                idcliente.getPqrsList().remove(pqrs);
                idcliente = em.merge(idcliente);
            }
            em.remove(pqrs);
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

    public List<Pqrs> findPqrsEntities() {
        return findPqrsEntities(true, -1, -1);
    }

    public List<Pqrs> findPqrsEntities(int maxResults, int firstResult) {
        return findPqrsEntities(false, maxResults, firstResult);
    }

    private List<Pqrs> findPqrsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pqrs.class));
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

    public Pqrs findPqrs(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pqrs.class, id);
        } finally {
            em.close();
        }
    }

    public int getPqrsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pqrs> rt = cq.from(Pqrs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
