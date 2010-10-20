package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link InventoryLotDao} that uses {@link Technology#Hibernate}.
 */
public class InventoryLotHibernateDao extends GenericHibernateDao<InventoryLot,Integer> implements InventoryLotDao {

}
