package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link SalesOrder}.
 */
public interface SalesOrderDao extends GenericDao<SalesOrder, Integer> {
	
	/**
	 * Finds all {@link SalesOrder}'s that are flagged as open.
	 * @return
	 */
	public List<SalesOrder> findAllOpen();
	
	/**
	 * Finds the flavors of all the products of the open {@link SalesOrder}s.
	 * @return
	 */
	public List<String> findAllOpenOrderFlavors();

}
