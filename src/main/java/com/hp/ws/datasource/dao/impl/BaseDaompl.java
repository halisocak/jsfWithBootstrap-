package com.hp.ws.datasource.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hp.ws.datasource.dao.BaseDao;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:51:53
 */
@Repository
public class BaseDaompl implements BaseDao {

	@PersistenceContext
	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional
	@Override
	public <T> T saveEntity(T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	@Transactional
	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		String criteriaQuery = "Select t FROM " + entityClass.getName() + " t";
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	@Transactional
	public <T> List<T> findByProperty(Class<T> entityClass, String property, Object value) {
		String criteriaQuery = "Select t FROM " + entityClass.getName() + " t WHERE t." + property + " =:" + property;
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery, entityClass);
		typedQuery.setParameter(property, value);
		return typedQuery.getResultList();
	}

}
