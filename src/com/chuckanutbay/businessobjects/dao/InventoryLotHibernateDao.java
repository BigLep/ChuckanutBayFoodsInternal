package com.chuckanutbay.businessobjects.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link InventoryLotDao} that uses {@link Technology#Hibernate}.
 */
public class InventoryLotHibernateDao extends GenericHibernateDao<InventoryLot,Integer> implements InventoryLotDao {

	@Override
	public List<InventoryLot> findUnused() {
		return findByCriteriaDefaultSort(
			Restrictions.isNull("startUseDatetime")
		);
	}

	@Override
	public List<InventoryLot> findInUse() {
		return findByCriteriaDefaultSort(
				Restrictions.isNotNull("startUseDatetime"),
				Restrictions.isNull("endUseDatetime")
			);
	}

	@Override
	public List<InventoryLot> findInUseOnDate(Date date) {
		return findByCriteriaDefaultSort(
			Restrictions.le("startUseDatetime", date),
			Restrictions.or(
				Restrictions.ge("endUseDatetime", date),
				Restrictions.isNull("endUseDatetime")
			)
		);
	}

	@Override
	public List<InventoryLot> findLotCodeMatch(String code) {
		return findByCriteriaDefaultSort(
					Restrictions.eq("code", code)
				);
	}

	@SuppressWarnings("unchecked")
	private List<InventoryLot> findByCriteriaDefaultSort(Criterion...criterion) {
		// http://stackoverflow.com/questions/870029/hibernate-order-by-association
		Criteria crit = getCriteria();
		for(Criterion c: criterion) {
			crit.add(c);
		}
		return crit.createCriteria("inventoryItem")
			.addOrder(Order.asc("description"))
			.list();
	}

	@Override
	public InventoryLot findActiveMatch(String code, InventoryItem item) {
		List<InventoryLot> mathes = findByCriteriaDefaultSort(
				Restrictions.eq("code", code),
				Restrictions.eq("inventoryItem", item),
				Restrictions.isNull("endUseDatetime")
			);
		if (!mathes.isEmpty()) {
			return mathes.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryLot> findByInventoryItem(InventoryItem item) {
		return getCriteria()
				.add(Restrictions.eq("inventoryItem", item))
				.addOrder(Order.desc("receivedDatetime"))
				.addOrder(Order.desc("id"))
				.list();
	}

}
