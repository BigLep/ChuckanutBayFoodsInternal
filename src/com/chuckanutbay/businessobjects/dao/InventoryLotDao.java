package com.chuckanutbay.businessobjects.dao;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link InventoryLot}.
 */
public interface InventoryLotDao extends GenericDao<InventoryLot, Integer> {

	List<InventoryLot> findUnused();
	
	List<InventoryLot> findInUse();
	
	List<InventoryLot> findInUseOnDate(Date date);
	
	List<InventoryLot> findLotCodeMatch(String code);
	
}
