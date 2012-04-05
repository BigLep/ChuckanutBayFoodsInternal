package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link InventoryItemDao} that uses {@link Technology#Hibernate}.
 */
public class InventoryItemHibernateDao extends GenericHibernateDao<InventoryItem,String> implements InventoryItemDao {

}
