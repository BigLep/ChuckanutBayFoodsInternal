package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.QuickbooksSubItem;
import com.chuckanutbay.documentation.Technology;
/**
 * {@link QuickbooksSubItemDao} that uses {@link Technology#Hibernate}.
 */
public class QuickbooksSubItemHibernateDao extends GenericHibernateDao<QuickbooksSubItem,Integer> implements QuickbooksSubItemDao {
	
}
