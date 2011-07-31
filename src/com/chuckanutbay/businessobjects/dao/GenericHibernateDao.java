package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link GenericDao} that uses {@link Technology#Hibernate}.
 * @see "http://community.jboss.org/wiki/GenericDataAccessObjects"
 * @param <T> See {@link GenericDao}.
 * @param <ID> See {@link GenericDao}.
 */
public abstract class GenericHibernateDao<T, ID extends Serializable> implements GenericDao<T, ID> {

	private final Class<T> persistentClass;

	public GenericHibernateDao() {
		this.persistentClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T)getSession().load(getPersistentClass(), id);
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example =  Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	@Override
	public T makePersistent(T entity) {
		//getSession().merge(entity);
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public List<T> makePersistent(List<T> entities) {
		List<T> perisistedEntities = newArrayList();
		for (T entity : entities) {
			perisistedEntities.add(makePersistent(entity));
		}
		return perisistedEntities;
	}

	@Override
	public void makeTransient(T entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = getCriteria();
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}
	
	protected Criteria getCriteria() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		return crit;
	}
}
