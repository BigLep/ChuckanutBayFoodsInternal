package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link SalesOrderDao} that uses {@link Technology#Hibernate}.
 */
public class SalesOrderHibernateDao extends GenericHibernateDao<SalesOrder,Integer> implements SalesOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesOrder> findAllOpen() {
		return getCriteria()
			.add(Restrictions.eq("orderClosed", false))
			//.setFetchMode("salesOrderLineItems", FetchMode.JOIN)
			.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllOpenOrderFlavors() {
		SortedSet<String> flavors = new TreeSet<String>();
		flavors.addAll(getSession()
				.createQuery(
					"SELECT DISTINCT qbItem.quickbooksItemSupplement.flavor " + //Select the quickbooks item ids
					"FROM com.chuckanutbay.businessobjects.QuickbooksItem AS qbItem " + //qbItem is a QuickbooksItem
					"WHERE NOT EXISTS ( " +
						"FROM com.chuckanutbay.businessobjects.QuickbooksSubItem AS subItem " +
						"WHERE qbItem.id = subItem.quickbooksItem.id ) " + //And where the id doesn't have any subItems (the sub items will added in the next query
					"AND EXISTS ( " +
						"FROM com.chuckanutbay.businessobjects.SalesOrderLineItem AS lineItem " +
						"WHERE lineItem.salesOrder.orderClosed = false " +
						"AND qbItem.quickbooksItemSupplement.flavor = lineItem.quickbooksItem.quickbooksItemSupplement.flavor ) ")
				.list());
		flavors.addAll(getSession()
				.createQuery(
					"SELECT DISTINCT qbSubItem.subItem.quickbooksItemSupplement.flavor " + //Select the quickbooks item ids
					"FROM com.chuckanutbay.businessobjects.QuickbooksSubItem AS qbSubItem, com.chuckanutbay.businessobjects.SalesOrderLineItem AS lineItem " + //qbItem is a QuickbooksItem
					"WHERE lineItem.salesOrder.orderClosed = false " +
					"AND qbSubItem.quickbooksItem.id = lineItem.quickbooksItem.id ) ")
				.list());
		 return newArrayList(flavors);
	}
}