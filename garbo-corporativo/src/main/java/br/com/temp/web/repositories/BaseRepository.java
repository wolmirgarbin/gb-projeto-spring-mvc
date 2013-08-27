package br.com.viasoft.portaldef.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {
	
	T findOne(ID id);
	
	T save(T entity);
	
	List<T> findAll();

    Page<T> findAll(Pageable pageable);

    Long count();

    void delete(T entity);

    boolean exists(ID primaryKey);
	
}