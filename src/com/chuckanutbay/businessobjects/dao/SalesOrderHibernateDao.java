package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link SalesOrderDao} that uses {@link Technology#Hibernate}.
 */
public class SalesOrderHibernateDao extends GenericHibernateDao<SalesOrder,Integer> implements SalesOrderDao {

}