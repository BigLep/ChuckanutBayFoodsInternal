package com.chuckanutbay.businessobjects.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link ActivityDao} that uses {@link Technology#Hibernate}.
 */
public class InventoryLotStickerColorHibernateDao extends GenericHibernateDao<InventoryLotStickerColor,Integer> implements InventoryLotStickerColorDao {

	@Override
	public InventoryLotStickerColor findNextColor(
			InventoryLotStickerColor inputColor) {
		List<InventoryLotStickerColor> colors = findAll();
		int indexOfInputColor = colors.indexOf(inputColor);
		if(indexOfInputColor + 1 >= colors.size()) {
			return colors.get(0);
		} else {
			return colors.get(indexOfInputColor + 1);
		}
	}

	@Override
	public InventoryLotStickerColor findFirstColor() {
		return findAll().get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryLotStickerColor> findAll() {
		return getCriteria().addOrder(Order.asc("id")).list();
	}
	
}
