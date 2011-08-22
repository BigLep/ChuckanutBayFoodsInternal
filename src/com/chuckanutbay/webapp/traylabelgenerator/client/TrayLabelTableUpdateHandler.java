package com.chuckanutbay.webapp.traylabelgenerator.client;

import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface TrayLabelTableUpdateHandler {
	/**
	 * Called when the given {@link TrayLabelDto} is modified by a cellTable;
	 * @param updatedObject Note: If {@link TrayLabelDto} was removed from the table, its {@link QuickbooksItemDto} will be null.
	 */
	public void onTrayLabelUpdate(TrayLabelDto updatedObject);
}
