package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link ActivityHibernateDao} that uses {@link Technology#Hibernate}.
 */
public class ActivityHibernateDao extends GenericHibernateDao<Activity,Integer> implements ActivityDao {

}
