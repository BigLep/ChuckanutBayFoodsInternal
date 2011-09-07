package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.common.server.Timer;
/**
 * {@link QuickbooksItemDao} that uses {@link Technology#Hibernate}.
 */
public class QuickbooksItemHibernateDao extends GenericHibernateDao<QuickbooksItem,Integer> implements QuickbooksItemDao {

	@Override
	public List<QuickbooksItem> findCaseItems() {
		Logger logger = Logger.getLogger(QuickbooksItemHibernateDao.class.getName());
		Timer timer = new Timer(logger).start();
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

	@Override
	public QuickbooksItem findById(String id) {
		return (QuickbooksItem) getCriteria()
				.add(Restrictions.eq("id", id))
				.list().get(0);
	}

}
