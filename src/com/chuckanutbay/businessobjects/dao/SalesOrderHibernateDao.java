package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link SalesOrderDao} that uses {@link Technology#Hibernate}.
 */
public class SalesOrderHibernateDao extends GenericHibernateDao<SalesOrder,Integer> implements SalesOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesOrder> findAllOpen() {
		return getCriteria()
			.add(Restrictions.eq("orderClosed", false))
			//.setFetchMode("salesOrderLineItems", FetchMode.JOIN)
			.list();
	}
}