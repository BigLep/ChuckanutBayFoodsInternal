package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link TrayLabel}.
 */
public interface TrayLabelDao extends GenericDao<TrayLabel, Integer>{
	
	public List<TrayLabel> findBySalesOrderLineItemId(Integer id);
	
	/**
	 * Finds the most recently created {@link TrayLabel}s in the database
	 * @param numberOfRecords The number of records to get from the database such that numberOfRecords == return.size()
	 * @return
	 */
	public List<TrayLabel> findRecent(int numberOfRecords);
}
