/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.materjunior.entities;

import br.com.materjunior.entities.exceptions.NonexistentEntityException;
import br.com.materjunior.entities.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author wolmir
 */
public class LancamentoJpaController implements Serializable {

    public LancamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lancamento lancamento) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lancamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLancamento(lancamento.getIdlancamento()) != null) {
                throw new PreexistingEntityException("Lancamento " + lancamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lancamento lancamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lancamento = em.merge(lancamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lancamento.getIdlancamento();
                if (findLancamento(id) == null) {
                    throw new NonexistentEntityException("The lancamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lancamento lancamento;
            try {
                lancamento = em.getReference(Lancamento.class, id);
                lancamento.getIdlancamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lancamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(lancamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lancamento> findLancamentoEntities() {
        return findLancamentoEntities(true, -1, -1);
    }

    public List<Lancamento> findLancamentoEntities(int maxResults, int firstResult) {
        return findLancamentoEntities(false, maxResults, firstResult);
    }

    private List<Lancamento> findLancamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lancamento.class));
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

    public Lancamento findLancamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lancamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getLancamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lancamento> rt = cq.from(Lancamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
