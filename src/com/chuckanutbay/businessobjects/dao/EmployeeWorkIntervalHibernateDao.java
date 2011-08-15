package com.chuckanutbay.businessobjects.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link EmployeeWorkIntervalDao} that uses {@link Technology#Hibernate}.
 */
public class EmployeeWorkIntervalHibernateDao extends GenericHibernateDao<EmployeeWorkInterval,Integer> implements EmployeeWorkIntervalDao {
	
	// TODO: remove System.out.println calls and instead use logger
	private static final Logger logger = LoggerFactory.getLogger(EmployeeWorkIntervalHibernateDao.class);
	
	@Override
	public List<EmployeeWorkInterval> findOpenEmployeeWorkIntervals() {
		return findByCriteriaDefaultSort(
			Restrictions.isNull("endDateTime")
		);
}
	
	@Override
	public EmployeeWorkInterval findOpenEmployeeWorkInterval(Employee employee) {
		logger.debug("Searching for intervals belonging to " + employee.getFirstName());
		return (EmployeeWorkInterval)getCriteria()
			.add(Restrictions.eq("employee", employee))
			.add(Restrictions.isNull("endDateTime"))
			.uniqueResult();
	}
	
	

	@Override
	public List<EmployeeWorkInterval> findEmployeeWorkIntervalsBetweenDates(Employee employee, DateTime start, DateTime end) {
		System.out.println("Start: " + start.getDayOfYear());
		System.out.println("End: " + end.getDayOfYear());
		return findByCriteriaDefaultSort(
				Restrictions.ge("startDateTime", start.toDate()), 
				Restrictions.le("startDateTime", end.toDate()),
				Restrictions.eq("employee", employee));
		
	}
	
	@SuppressWarnings("unchecked")
	private List<EmployeeWorkInterval> findByCriteriaDefaultSort(Criterion...criterion) {
		// http://stackoverflow.com/questions/870029/hibernate-order-by-association
		Criteria crit = getCriteria();
		for(Criterion c: criterion) {
			crit.add(c);
		}
		return crit
			.addOrder(Order.asc("startDateTime"))
			.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<EmployeeWorkIntervalActivityPercentage> findLastEnteredPercentages(
			Employee employee) {
		List<EmployeeWorkInterval> intervals = getCriteria()
				.add(Restrictions.isNotNull("endDateTime"))
				.add(Restrictions.eq("employee", employee))
				.addOrder(Order.desc("endDateTime"))
				.list();
		if (!intervals.isEmpty()) {
			return intervals.get(0).getEmployeeWorkIntervalActivityPercentages();
		} else {
			return null;
		}
	}
}
