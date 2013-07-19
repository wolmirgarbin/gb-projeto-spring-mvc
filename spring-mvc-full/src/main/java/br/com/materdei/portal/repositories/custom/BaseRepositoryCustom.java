/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.viasoft.portaldef.repositories.custom;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public abstract class BaseRepositoryCustom<T extends Serializable> {
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    protected Root<T> root;
    protected CriteriaQuery<T> query;
    protected CriteriaBuilder criterio;
    protected Predicate predicate;
    
    protected static final Integer MAX_RESULT_COMPLETE = 10;
    protected static final Integer MAX_RESULT_COMPLETE2 = 30;
    protected static final Integer MAX_RESULT_SEARCH = 100;
    
    private final Class<T> type;
    
    public BaseRepositoryCustom() {
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    protected void initialize() {
        criterio = entityManager.getCriteriaBuilder();
        query = criterio.createQuery(type);
        root = query.from(type);
        predicate = null;
    }
    
    protected Predicate or(final Predicate predicate) {
        if (this.predicate == null) {
            this.predicate = predicate;
        } else {
            this.predicate = criterio.or(this.predicate, predicate);
        }
        addWhere();
        
        return this.predicate;
    }
    
    protected Predicate and(final Predicate predicate) {
        if (this.predicate == null) {
            this.predicate = predicate;
        } else {
            this.predicate = criterio.and(this.predicate, predicate);
        }
        addWhere();
        
        return this.predicate;
    }
    
    protected void addWhere() {
        if (predicate != null) {
            query.where(predicate);
        }
    }
    
    protected String containing(final String valor) {
        return "%"+valor+"%";
    }
    
}
