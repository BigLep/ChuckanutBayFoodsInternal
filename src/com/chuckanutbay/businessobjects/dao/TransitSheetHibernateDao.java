package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.TransitSheet;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link TransitSheetDao} that uses {@link Technology#Hibernate}.
 */
public class TransitSheetHibernateDao extends GenericHibernateDao<TransitSheet,Integer> implements TransitSheetDao {

}
