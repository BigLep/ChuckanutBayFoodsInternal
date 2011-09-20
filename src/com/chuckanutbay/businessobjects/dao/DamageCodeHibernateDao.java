package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.DamageCode;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link DamageCodeDao} that uses {@link Technology#Hibernate}.
 */
public class DamageCodeHibernateDao extends GenericHibernateDao<DamageCode,Integer> implements DamageCodeDao {
	
}
