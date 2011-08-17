package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link TransitSheetDao} that uses {@link Technology#Hibernate}.
 */
public class TransitSheetHibernateDao extends GenericHibernateDao<TrayLabel,Integer> implements TransitSheetDao {

}
