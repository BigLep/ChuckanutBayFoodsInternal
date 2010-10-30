package com.chuckanutbay.businessobjects.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link InventoryLotDao} that uses {@link Technology#Hibernate}.
 */
public class InventoryLotHibernateDao extends GenericHibernateDao<InventoryLot,Integer> implements InventoryLotDao {

	@Override
	public List<InventoryLot> findUnused() {
		return findByCriteria(
			Restrictions.isNull("startUseDatetime")
		);
	}

	@Override
	public List<InventoryLot> findInUse() {
		return findByCriteria(
				Restrictions.isNotNull("startUseDatetime"),
				Restrictions.isNull("endUseDatetime")
			);
	}

	@Override
	public List<InventoryLot> findInUseOnDate(Date date) {
		return findByCriteria(
				Restrictions.le("startUseDatetime", date),
				Restrictions.or(
						Restrictions.ge("endUseDatetime", date),
						Restrictions.isNull("endUseDatetime")
				)
			);
	}

	@Override
	public List<InventoryLot> findLotCodeMatch(String code) {
		return findByCriteria(
				Restrictions.eq("code", code)
			);
	}

}
