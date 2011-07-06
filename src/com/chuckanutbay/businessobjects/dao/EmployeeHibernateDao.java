package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link EmployeeDao} that uses {@link Technology#Hibernate}.
 */
public class EmployeeHibernateDao extends GenericHibernateDao<Employee,Integer> implements EmployeeDao {
	
}
