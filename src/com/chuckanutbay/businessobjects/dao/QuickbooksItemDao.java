package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link QuickbooksItem}.
 */
public interface QuickbooksItemDao extends GenericDao<QuickbooksItem, Integer> {
	
	/**
	 * 
	 * @return The {@link QuickbooksItem}s that end in a dash and a number other than 1 (i.e. 27001-6)
	 */
	public List<QuickbooksItem> findCaseItems();
	
	/**
	 * 
	 * @param id
	 * @return The {@link QuickbooksItem} with the given id
	 */
	public QuickbooksItem findById(String id);
	
}
