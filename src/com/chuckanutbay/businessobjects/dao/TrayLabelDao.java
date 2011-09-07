package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link TrayLabel}.
 */
public interface TrayLabelDao extends GenericDao<TrayLabel, Integer>{
	
	public List<TrayLabel> findBySalesOrderLineItemId(Integer id);

	public List<TrayLabel> findFirst30();
}
