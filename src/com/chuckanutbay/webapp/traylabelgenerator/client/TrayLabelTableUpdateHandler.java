package com.chuckanutbay.webapp.traylabelgenerator.client;

import com.chuckanutbay.webapp.common.shared.OrderTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface TrayLabelTableUpdateHandler {
	/**
	 * Called when the given {@link OrderTrayLabelDto} is modified by a cellTable;
	 * @param object Note: If {@link OrderTrayLabelDto} was removed from the table, its {@link QuickbooksItemDto} will be null.
	 */
	public void onTrayLabelUpdate(TrayLabelDto object);
}
