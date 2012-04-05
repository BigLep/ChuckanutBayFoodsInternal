package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.common.server.Timer;
/**
 * {@link QuickbooksItemDao} that uses {@link Technology#Hibernate}.
 */
public class QuickbooksItemHibernateDao extends GenericHibernateDao<QuickbooksItem,String> implements QuickbooksItemDao {

	@Override
	public List<QuickbooksItem> findCaseItems() {
		Timer timer = new Timer(LoggerFactory.getLogger(QuickbooksItemHibernateDao.class.getName())).start();
		List<QuickbooksItem> caseItems = newArrayList(findAll());
		timer.logTime("Query done");
		for (QuickbooksItem qbItem : newArrayList(caseItems)) {
			String id = qbItem.getId();
			int lastDashIndex = id.lastIndexOf("-");
			int length = id.length();
					
			if (lastDashIndex != -1) {//There is a dash
				if (lastDashIndex == length - 2) {//The dash is the second to last digit
					if (id.endsWith(String.valueOf(1))) {//The last two digits are -1
						caseItems.remove(qbItem);
					}
				}
			} else {//There is no dash
				caseItems.remove(qbItem);
			}
		}
		timer.stop("Method finnished");
		return caseItems;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllIds() {
		List<String> ids = newArrayList();
		
		//Query 1: Get the regular ids
		ids.addAll(
			getSession()
			.createQuery(
				"SELECT qbItem.id " + //Select the quickbooks item ids
				"FROM com.chuckanutbay.businessobjects.QuickbooksItem AS qbItem " + //qbItem is a QuickbooksItem
				"WHERE qbItem.id LIKE '%-%' " + //Where the id has a -
				"AND qbItem.id NOT LIKE '%-1' " + //And where the id doesn't end in -1
				"AND NOT EXISTS ( FROM com.chuckanutbay.businessobjects.QuickbooksSubItem AS subItem WHERE qbItem.id = subItem.quickbooksItem ) " + //And where the id doesn't have any subItems (the sub items will added in the next query
				"ORDER BY qbItem.id asc") //Sorted
			.list()
		);
		
		//Query 2: Get the subitem ids
		ids.addAll(
			getSession()
			.createQuery(
				"SELECT qbSubItem.quickbooksItem || ' ' || qbSubItem.subItem.quickbooksItemSupplement.flavor " + //Select the quickbooks sub items. Format example: 27039-6 NewYork
				"FROM com.chuckanutbay.businessobjects.QuickbooksSubItem AS qbSubItem " + //qbSubItem is a QuickbooksSubItem
				"ORDER BY qbSubItem.quickbooksItem.id asc") //Sorted
			.list()
		);
				
		return ids;
	}
	



}
