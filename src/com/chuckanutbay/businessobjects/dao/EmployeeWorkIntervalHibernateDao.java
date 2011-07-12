package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.documentation.Technology;
import com.google.common.base.Objects;

/**
 * {@link EmployeeWorkIntervalDao} that uses {@link Technology#Hibernate}.
 */
public class EmployeeWorkIntervalHibernateDao extends GenericHibernateDao<EmployeeWorkInterval,Integer> implements EmployeeWorkIntervalDao {
	
	@Override
	public List<EmployeeWorkInterval> findOpenEmployeeWorkIntervals() {
	return findByCriteriaDefaultSort(
			Restrictions.isNull("endDateTime")
		);
}
	
	@Override
	public EmployeeWorkInterval findOpenEmployeeWorkInterval(Employee employee) {
		System.out.println("Searching for intervals belonging to " + employee.getFirstName());
		for (EmployeeWorkInterval interval : findByCriteriaDefaultSort(Restrictions.isNull("endDateTime"))) {
			System.out.println("Found Open Employee Work Interval belonging to: " + interval.getEmployee().getFirstName() + interval.getEmployee().getBarcodeNumber());
			if (Objects.equal(interval.getEmployee().getBarcodeNumber(), employee.getBarcodeNumber())) {
				return interval;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<EmployeeWorkInterval> findByCriteriaDefaultSort(Criterion...criterion) {
		// http://stackoverflow.com/questions/870029/hibernate-order-by-association
		Criteria crit = getCriteria();
		for(Criterion c: criterion) {
			crit.add(c);
		}
		return crit.addOrder(Order.asc("startDateTime"))
			.list();
	}

	@Override
	public List<EmployeeWorkInterval> findEmployeeWorkIntervalsBetweenDates(Employee employee, DateTime start, DateTime end) {
		List<EmployeeWorkInterval> intervals = newArrayList();
		System.out.println("Start: " + start.getDayOfYear());
		System.out.println("End: " + end.getDayOfYear());
		for (EmployeeWorkInterval interval : findByCriteriaDefaultSort(Restrictions.ge("startDateTime", start.toDate()), Restrictions.le("startDateTime", end.toDate()))) {
			System.out.println("Work interval within range belonging to employee: " + interval.getEmployee().getFirstName());
			if (Objects.equal(interval.getEmployee().getBarcodeNumber(), employee.getBarcodeNumber())) {
				System.out.println("Added work interval to list belonging to employee: " + interval.getEmployee().getFirstName());
				intervals.add(interval);
			}
		}
		return intervals;
	}
}
