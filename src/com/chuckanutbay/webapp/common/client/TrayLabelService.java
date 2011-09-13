package com.chuckanutbay.webapp.common.client;

import java.util.List;
import java.util.Set;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.webapp.common.shared.InventoryTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Since this {@link RemoteService} is defined within a base module,
 * doing {@link GWT#create(Class)} within a sub-module will yield an instance with a path relative to that base module.
 * In order to get an instances with a path that is not relative to the sub-module,
 * use {@link ServiceUtils#createPanLabelService()}.
 * This is the preferred/support method of access.
 */
public interface TrayLabelService extends RemoteService {
	
	/**
	 * 
	 * @return {@link SalesOrderLineItemDto}s that are a part of open orders and aren't completely on {@link TrayLabel}s.
	 */
	List<SalesOrderLineItemDto> getSalesOrderLineItems();
	
	/**
	 * 
	 * @return All {@link TrayLabelDto}s in the database.
	 */
	List<TrayLabelDto> getTrayLabelHistroy();
	
	/**
	 * Adds newTrayLabels to the database and prints them.
	 * @param newTrayLabels
	 */
	void setTrayLabels(List<TrayLabelDto> newTrayLabels);
	
	/**
	 * 
	 * @return All {@link QuickbooksItem} ids in the database ending in -##.
	 */
	List<String> getQuickbooksItemIds();
	
	/**
	 * Creates an {@link InventoryTrayLabelDto} for the given qbItem
	 * @param qbItemId If the qbItem has any sub items, then the qbItemId should follow the format: 'qbItemId subItemFlavor' (e.g. '27039-6 NewYork')
	 * @return
	 */
	InventoryTrayLabelDto getInventoryTrayLabelDto(String qbItemId);
	
	/**
	 * 
	 * @return Example: "2C192A1" meaning crew 2C, day 192 of the year, batch A, year ###1 (such as 2011).
	 */
	String getCurrentLotCode();
	
	/**
	 * Updates the given {@link TrayLabelDto} on the database. If the {@link TrayLabelDto}'s lotcode is null then make the object
	 * transient. Otherwise only the lotcode and cases values should change. 
	 * @param trayLabel
	 */
	void updateTrayLabel(TrayLabelDto trayLabel);
	
	/**
	 * Prints the given {@link TrayLabels}
	 * @param trayLabels
	 */
	void printTrayLabels(Set<TrayLabelDto> trayLabels);
}
