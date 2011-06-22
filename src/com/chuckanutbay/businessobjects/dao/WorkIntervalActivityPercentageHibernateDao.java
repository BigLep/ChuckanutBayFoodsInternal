package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.WorkIntervalActivityPercentage;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link EmployeeDao} that uses {@link Technology#Hibernate}.
 */
public class WorkIntervalActivityPercentageHibernateDao extends GenericHibernateDao<WorkIntervalActivityPercentage,Integer> implements WorkIntervalActivityPercentageDao {

}
