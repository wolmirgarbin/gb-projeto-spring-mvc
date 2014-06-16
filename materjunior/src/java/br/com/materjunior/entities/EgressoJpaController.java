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
public class EgressoJpaController implements Serializable {

    public EgressoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egresso egresso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(egresso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEgresso(egresso.getIdegresso()) != null) {
                throw new PreexistingEntityException("Egresso " + egresso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egresso egresso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            egresso = em.merge(egresso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egresso.getIdegresso();
                if (findEgresso(id) == null) {
                    throw new NonexistentEntityException("The egresso with id " + id + " no longer exists.");
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
            Egresso egresso;
            try {
                egresso = em.getReference(Egresso.class, id);
                egresso.getIdegresso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egresso with id " + id + " no longer exists.", enfe);
            }
            em.remove(egresso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egresso> findEgressoEntities() {
        return findEgressoEntities(true, -1, -1);
    }

    public List<Egresso> findEgressoEntities(int maxResults, int firstResult) {
        return findEgressoEntities(false, maxResults, firstResult);
    }

    private List<Egresso> findEgressoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egresso.class));
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

    public Egresso findEgresso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egresso.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgressoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egresso> rt = cq.from(Egresso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
