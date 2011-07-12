package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link EmployeeDao} that uses {@link Technology#Hibernate}.
 */
public class EmployeeHibernateDao extends GenericHibernateDao<Employee,Integer> implements EmployeeDao {

	@Override
	public Employee findEmployeeWithBarcodeNumber(Integer barcodeNumber) {
		// http://stackoverflow.com/questions/870029/hibernate-order-by-association
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("barcodeNumber", barcodeNumber));
		List<Employee> matchingEmployees = crit.list();
		if (matchingEmployees.size() == 1) {
			return matchingEmployees.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Employee> findEmployeesByShift(Integer shift) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("shift", shift));
		crit.addOrder(Order.asc("lastName"));
		return crit.list();
	}
}
