package com.hp.ws.datasource.dao;

import java.util.List;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:46:59
 */
public interface BaseDao {

	public <T> T find(Class<T> entityClass, Object primaryKey);

	public <T> List<T> findByProperty(Class<T> entityClass, String property, Object value);

	public <T> T saveEntity(T entity);

	public <T> List<T> findAll(Class<T> entityClass);

}
