package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.businessobjects.BusinessObjects.addSubItems;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSubItem;
import static com.chuckanutbay.businessobjects.util.BusinessObjectsUtil.getCakesPerCase;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toQuickbooksItemDtos;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;

/**
 * @see DtoUtils
 */
public class DtoUtilsTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	/**
	 * @see DtoUtils#toQuickbooksItemDtos
	 */
	@Test
	public void testToQuickbooksItemDtos() {
		//Test two basic QuickbooksItems
		QuickbooksItem qbItem1 = oneQuickbooksItem("27001-6", null, "7Inch", "NewYork", 1.0);
		QuickbooksItem qbItem2 = oneQuickbooksItem("27002-6", null, "7Inch", "VeryBerry", 1.0);
		QuickbooksItem qbItem3 = oneQuickbooksItem("27003-6", null, "7Inch", "WhiteChocolateRasp", 1.0);
		QuickbooksItem qbItem4 = oneQuickbooksItem("27004-6", null, "7Inch", "ChocolateTruffle", 1.0);
		
		List<QuickbooksItem> qbItems = newArrayList(qbItem1, qbItem2);
		
		Map<String, QuickbooksItemDto> dtos = toQuickbooksItemDtos(qbItems);
		
		assertQuickbooksItemsEqual(dtos.get("27001-6"), qbItem1, getCakesPerCase(qbItem1));
		assertQuickbooksItemsEqual(dtos.get("27002-6"), qbItem2, getCakesPerCase(qbItem2));
		
		//Test a QuickbooksItem with subItems
		QuickbooksItem qbItemWithSubs = oneQuickbooksItem("27039-6", null, "7Inch", "Fab4", 1.0);

		addSubItems(qbItemWithSubs, 
				oneSubItem(qbItemWithSubs, qbItem1, 1.5),
				oneSubItem(qbItemWithSubs, qbItem2, 1.5),
				oneSubItem(qbItemWithSubs, qbItem3, 1.5),
				oneSubItem(qbItemWithSubs, qbItem4, 1.5)
		);
		
		dtos = toQuickbooksItemDtos(newArrayList(qbItemWithSubs));
		
		assertQuickbooksItemsEqual(dtos.get("27039-6 NewYork"), oneQuickbooksItem("27039-6 NewYork", null, "7Inch", "NewYork", 4.0), 1.5);
		
	}
	
	private void assertQuickbooksItemsEqual(QuickbooksItemDto qbItemDto, QuickbooksItem qbItem, double cakesPerCase) {
		Assert.assertEquals(qbItemDto.getId(), qbItem.getId());
		Assert.assertEquals(qbItemDto.getInstructions(), qbItem.getInstructions());
		Assert.assertEquals(qbItemDto.getSize(), qbItem.getSize());
		Assert.assertEquals(qbItemDto.getFlavor(), qbItem.getFlavor());
		Assert.assertEquals(qbItemDto.getCasesPerTray(), qbItem.getCasesPerTray());
		Assert.assertEquals(qbItemDto.getCakesPerCase(), cakesPerCase);
	}

}
