package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link EmployeeWorkIntervalDao} that uses {@link Technology#Hibernate}.
 */
public class EmployeeWorkIntervalHibernateDao extends GenericHibernateDao<EmployeeWorkInterval,Integer> implements EmployeeWorkIntervalDao {

}
