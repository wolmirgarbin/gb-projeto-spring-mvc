package br.com.viasoft.portaldef.repositories;

import java.io.Serializable;

import org.springframework.data.repository.Repository;

public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {
	
	T findOne(ID id);
	
	T save(T entity);
	
}