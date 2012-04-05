package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.SalesOrderLineItem;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link SalesOrderLineItemDao} that uses {@link Technology#Hibernate}.
 */
public class SalesOrderLineItemHibernateDao extends GenericHibernateDao<SalesOrderLineItem,Integer> implements SalesOrderLineItemDao {

}