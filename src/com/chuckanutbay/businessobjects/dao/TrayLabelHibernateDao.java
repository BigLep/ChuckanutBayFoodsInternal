package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link TrayLabelDao} that uses {@link Technology#Hibernate}.
 */
public class TrayLabelHibernateDao extends GenericHibernateDao<TrayLabel,Integer> implements TrayLabelDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TrayLabel> findBySalesOrderLineItemId(Integer id) {
		return getCriteria()
				.createCriteria("salesOrderLineItem")
				.add(Restrictions.eq("id", id))
				.list();
	}
	
}
