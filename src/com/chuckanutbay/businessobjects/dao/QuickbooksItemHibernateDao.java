package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.documentation.Technology;
/**
 * {@link QuickbooksItemDao} that uses {@link Technology#Hibernate}.
 */
public class QuickbooksItemHibernateDao extends GenericHibernateDao<QuickbooksItem,Integer> implements QuickbooksItemDao {

}
