package com.chuckanutbay.businessobjects.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import com.chuckanutbay.documentation.Terminology;

/**
 * Generic @link {@link Terminology#DAO} for working with business objects.
 * @see "http://community.jboss.org/wiki/GenericDataAccessObjects"
 * @param <T> Type of object this DAO works on.
 * @param <ID> Type of the id of the object this DAO works on.
 * This should correspond with {@link Id}.
 */
public interface GenericDao<T, ID extends Serializable> {

    T findById(ID id);

    List<T> findAll();

    List<T> findByExample(T exampleInstance, String[] excludeProperty);

    T makePersistent(T entity);

    List<T> makePersistent(List<T> entities);

    void makeTransient(T entity);
}
