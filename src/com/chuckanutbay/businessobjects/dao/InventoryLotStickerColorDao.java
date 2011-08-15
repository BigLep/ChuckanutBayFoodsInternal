package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DAO} for {@link Activity}.
 */
public interface InventoryLotStickerColorDao extends GenericDao<InventoryLotStickerColor, Integer> {
	public InventoryLotStickerColor findNextColor(InventoryLotStickerColor inputColor);

	public InventoryLotStickerColor findFirstColor();
}
