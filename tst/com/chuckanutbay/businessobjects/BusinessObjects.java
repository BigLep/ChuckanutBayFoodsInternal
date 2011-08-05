package com.chuckanutbay.businessobjects;

import java.util.Date;
import java.util.Set;

import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalHibernateDao;
import com.google.gwt.dev.util.collect.HashSet;

/**
 * Utility methods for creating business objects.
 */
public class BusinessObjects {

	/**
	 * @param barcodeNumber
	 * @param firstName
	 * @param lastName
	 * @return one persisted {@link Employee}.
	 */
	public static Employee oneEmployee(Integer barcodeNumber, String firstName, String lastName) {
		Employee employee = new Employee(null, firstName, lastName, barcodeNumber);
		EmployeeDao dao = new EmployeeHibernateDao();
		dao.makePersistent(employee);
		return employee;
	}
	
	/**
	 * @param barcodeNumber
	 * @param firstName
	 * @param lastName
	 * @param shift
	 * @return
	 */
	public static Employee oneEmployee(Integer barcodeNumber, String firstName, String lastName, Integer shift) {
		Employee employee = oneEmployee(barcodeNumber, firstName, lastName);
		employee.setShift(shift);
		return employee;
	}
	
	/**
	 * @param employee
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static EmployeeWorkInterval oneEmployeeWorkInterval(Employee employee, Date startDate, Date endDate) {
		EmployeeWorkInterval ewi = new EmployeeWorkInterval(employee, startDate, endDate);
		EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
		dao.makePersistent(ewi);
		return ewi;
	}
	
	public static Set<EmployeeWorkIntervalActivityPercentage> oneEmployeeWorkIntervalActivityPercentage(EmployeeWorkInterval ewi, Activity activity, Integer percentage) {
		EmployeeWorkIntervalActivityPercentage ewiap = new EmployeeWorkIntervalActivityPercentage(activity, percentage, ewi);
		EmployeeWorkIntervalActivityPercentageDao dao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
		dao.makePersistent(ewiap);
		Set<EmployeeWorkIntervalActivityPercentage> set = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		set.add(ewiap);
		return set;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static Activity oneActivity(String name) {
		Activity activity = new Activity(name);
		ActivityDao dao = new ActivityHibernateDao();
		dao.makePersistent(activity);
		return activity;
	}
}
