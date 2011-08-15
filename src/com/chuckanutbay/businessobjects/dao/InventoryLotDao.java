package com.chuckanutbay.businessobjects.dao;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link InventoryLot}.
 */
public interface InventoryLotDao extends GenericDao<InventoryLot, Integer> {

	/**
	 * @return {@link InventoryLot}s that haven't started to be used yet.
	 */
	List<InventoryLot> findUnused();

	List<InventoryLot> findInUse();

	List<InventoryLot> findInUseOnDate(Date date);

	List<InventoryLot> findLotCodeMatch(String code);
	
	/**
	 * 
	 * @param code
	 * @return an {@link InventoryLot} with the same lotcode that isn't marked as used up yet. Returns null if to matches are found.
	 */
	InventoryLot findActiveMatch(String code, InventoryItem item);
	
	/**
	 * 
	 * @param item
	 * @return {@link InventoryLot}s with a matching {@link InventoryItem} to item.
	 */
	List<InventoryLot> findByInventoryItem(InventoryItem item);

}
