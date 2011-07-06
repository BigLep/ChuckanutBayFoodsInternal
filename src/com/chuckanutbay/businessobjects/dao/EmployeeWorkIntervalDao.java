package com.chuckanutbay.businessobjects.dao;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link EmployeeWorkInterval}.
 */
public interface EmployeeWorkIntervalDao extends GenericDao<EmployeeWorkInterval, Integer> {

	EmployeeWorkInterval findOpenEmployeeWorkInterval(Employee employee);

	List<EmployeeWorkInterval> findOpenEmployeeWorkIntervals();
	
	List<EmployeeWorkInterval> findEmployeeWorkIntervalsSinceDate(Date date, Employee employee);
}
