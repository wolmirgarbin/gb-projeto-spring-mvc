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
public class AlunoprojetoJpaController implements Serializable {

    public AlunoprojetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alunoprojeto alunoprojeto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(alunoprojeto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlunoprojeto(alunoprojeto.getIdprojeto()) != null) {
                throw new PreexistingEntityException("Alunoprojeto " + alunoprojeto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alunoprojeto alunoprojeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            alunoprojeto = em.merge(alunoprojeto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alunoprojeto.getIdprojeto();
                if (findAlunoprojeto(id) == null) {
                    throw new NonexistentEntityException("The alunoprojeto with id " + id + " no longer exists.");
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
            Alunoprojeto alunoprojeto;
            try {
                alunoprojeto = em.getReference(Alunoprojeto.class, id);
                alunoprojeto.getIdprojeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alunoprojeto with id " + id + " no longer exists.", enfe);
            }
            em.remove(alunoprojeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alunoprojeto> findAlunoprojetoEntities() {
        return findAlunoprojetoEntities(true, -1, -1);
    }

    public List<Alunoprojeto> findAlunoprojetoEntities(int maxResults, int firstResult) {
        return findAlunoprojetoEntities(false, maxResults, firstResult);
    }

    private List<Alunoprojeto> findAlunoprojetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alunoprojeto.class));
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

    public Alunoprojeto findAlunoprojeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alunoprojeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlunoprojetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alunoprojeto> rt = cq.from(Alunoprojeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
