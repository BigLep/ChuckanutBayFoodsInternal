package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link Employee}.
 */
public interface EmployeeDao extends GenericDao<Employee, Integer> {
	
	Employee findEmployeeWithBarcodeNumber(Integer barcodeNumber);
	
	/**
	 * @param shift 
	 * @return A list of {@link Employee}s with matching shift number.
	 */
	List<Employee> findEmployeesByShift(Integer shift);
}
