package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.businessobjects.BusinessObjects.addLineItems;
import static com.chuckanutbay.businessobjects.BusinessObjects.addSubItems;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrder;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSubItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneTrayLabel;
import static com.chuckanutbay.util.testing.AssertExtensions.assertSize;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toTrayLabelDtoFunction;
import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.BusinessObjects;
import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.businessobjects.SalesOrderLineItem;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemHibernateDaoTest;
import com.chuckanutbay.businessobjects.dao.TrayLabelHibernateDao;
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

/**
 * @see TimeClockServiceImpl
 */
public class TrayLabelServiceImplTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();

	/**
	 *  @see TrayLabelServiceImpl#getQuickbooksItems()
	 */
	@Test
	public void testGetQuickbooksItems() {
		new QuickbooksItemHibernateDaoTest().testFindCaseItems();
		new DtoUtilsTest().testToQuickbooksItemDtos();
	}
	
	/**
	 *  @see TrayLabelServiceImpl#getCurrentLotCode()
	 */
	@Test
	public void testGetCurrentLotCode() {
		TrayLabelServiceImpl server = new TrayLabelServiceImpl();
		DateTime dt = new DateTime()
				.withYear(2011)
				.withHourOfDay(7)
				.withDayOfYear(111);
		assertEquals("2C111A1", server.getLotCode(dt));
	}
	
	/**
	 *  @see TrayLabelServiceImpl#updateTrayLabel(com.chuckanutbay.webapp.common.shared.TrayLabelDto)
	 */
	@Test
	public void testUpdateTrayLabel() {
		TrayLabelServiceImpl server = new TrayLabelServiceImpl();
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(oneSalesOrder("Haggens", false), oneQuickbooksItem("1111-11"), 10);
		SalesOrderLineItem soli2 = BusinessObjects.oneSalesOrderLineItem(oneSalesOrder("Freds", true), oneQuickbooksItem("1112-12"), 20);
		
		TrayLabel tl1 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl2 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl3 = oneTrayLabel(5, "4A111A1", soli2);
		TrayLabel tl4 = oneTrayLabel(5, "4A111A1", soli2);
		
		//Modify tl1 as a Dto
		TrayLabelDto tlDto = toTrayLabelDtoFunction.apply(tl1);
		tlDto.setCases(4.0);
		tlDto.setLotCode("4A222A2");
		
		server.updateTrayLabel(tlDto);
		
		assertEquals(tl1.getCases(), 4.0);
		assertEquals(tl1.getLotCode(), "4A222A2");
		
		tlDto.getSalesOrderLineItemDto().setQuickbooksItemDto(null);
		
		server.updateTrayLabel(tlDto);
		
		assertSize(new TrayLabelHibernateDao().findAll(), 3);
		
		
	}
	
	/**
	 *  @see TrayLabelServiceImpl#getSalesOrderLineItems()
	 */
	@Test
	public void testGetSalesOrderLineItmes() {
		TrayLabelServiceImpl server = new TrayLabelServiceImpl();
		
		SalesOrder so1 = oneSalesOrder("Haggens", false);
		SalesOrder so2 = oneSalesOrder("Freds", false);
		SalesOrder so3 = oneSalesOrder("DPI", true);
		
		QuickbooksItem qbItem1 = oneQuickbooksItem("27001-6", null, "7Inch", "NewYork", new Double(1));
		QuickbooksItem qbItem2 = oneQuickbooksItem("27002-6", null, "7Inch", "VeryBerry", new Double(1));
		QuickbooksItem qbItem3 = oneQuickbooksItem("27003-6", null, "7Inch", "WhiteChocolateRasp", new Double(1));
		QuickbooksItem qbItem4 = oneQuickbooksItem("27004-6", null, "7Inch", "ChocolateTruffle", new Double(1));
		
		QuickbooksItem qbItemWithSubs = oneQuickbooksItem("27039-6", null, "7Inch", "Fab4", new Double(1));

		addSubItems(qbItemWithSubs, 
				oneSubItem(qbItemWithSubs, qbItem1, 1.5),
				oneSubItem(qbItemWithSubs, qbItem2, 1.5),
				oneSubItem(qbItemWithSubs, qbItem3, 1.5),
				oneSubItem(qbItemWithSubs, qbItem4, 1.5)
		);
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(so1, qbItem1, 10);
		SalesOrderLineItem soli2 = BusinessObjects.oneSalesOrderLineItem(so2, qbItem2, 20);
		SalesOrderLineItem soli3 = BusinessObjects.oneSalesOrderLineItem(so2, qbItemWithSubs, 20);
		SalesOrderLineItem soli4 = BusinessObjects.oneSalesOrderLineItem(so2, qbItem4, 20);
		
		addLineItems(so1, soli1);
		addLineItems(so2, soli2, soli3);
		addLineItems(so3, soli4);
		
		TrayLabel tl1 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl2 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl3 = oneTrayLabel(5, "4A111A1", soli2);
		TrayLabel tl4 = oneTrayLabel(5, "4A111A1", soli2);
		TrayLabel tl5 = oneTrayLabel(20, "4A111A1", soli3, qbItem1);
		
		
		//soli2, soli3(x3 incomplete sub items) = 4 total
		assertSize(server.getSalesOrderLineItems(), 4);
		
	}
	
	/**
	 *  @see TrayLabelServiceImpl#getTrayLabelHistroy()
	 */
	 @Test
	 public void testGetTrayLabelHistory() {
		TrayLabelServiceImpl server = new TrayLabelServiceImpl();
		
		SalesOrder so1 = oneSalesOrder("Haggens", false);
		SalesOrder so2 = oneSalesOrder("Freds", false);
		SalesOrder so3 = oneSalesOrder("DPI", true);
		
		QuickbooksItem qbItem1 = oneQuickbooksItem("27001-6", null, "7Inch", "NewYork", new Double(1));
		QuickbooksItem qbItem2 = oneQuickbooksItem("27002-6", null, "7Inch", "VeryBerry", new Double(1));
		QuickbooksItem qbItem3 = oneQuickbooksItem("27003-6", null, "7Inch", "WhiteChocolateRasp", new Double(1));
		QuickbooksItem qbItem4 = oneQuickbooksItem("27004-6", null, "7Inch", "ChocolateTruffle", new Double(1));
		
		QuickbooksItem qbItemWithSubs = oneQuickbooksItem("27039-6", null, "7Inch", "Fab4", new Double(1));

		addSubItems(qbItemWithSubs, 
				oneSubItem(qbItemWithSubs, qbItem1, 1.5),
				oneSubItem(qbItemWithSubs, qbItem2, 1.5),
				oneSubItem(qbItemWithSubs, qbItem3, 1.5),
				oneSubItem(qbItemWithSubs, qbItem4, 1.5)
		);
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(so1, qbItem1, 10);
		SalesOrderLineItem soli2 = BusinessObjects.oneSalesOrderLineItem(so2, qbItem2, 20);
		SalesOrderLineItem soli3 = BusinessObjects.oneSalesOrderLineItem(so2, qbItemWithSubs, 20);
		SalesOrderLineItem soli4 = BusinessObjects.oneSalesOrderLineItem(so2, qbItem4, 20);
		
		addLineItems(so1, soli1);
		addLineItems(so2, soli2, soli3);
		addLineItems(so3, soli4);
		
		TrayLabel tl1 = oneTrayLabel(5, "4A115A1", soli1);
		TrayLabel tl2 = oneTrayLabel(5, "4A114A1", soli1);
		TrayLabel tl3 = oneTrayLabel(5, "4A114A1", soli2);
		TrayLabel tl4 = oneTrayLabel(5, "4A113A1", soli2);
		TrayLabel tl5 = oneTrayLabel(20, "4A112A1", soli3, qbItem1);
		
		List<TrayLabelDto> tlDtos = server.getTrayLabelHistroy();
		assertEquals(1, tlDtos.get(0).getId().intValue());
		assertEquals(3, tlDtos.get(1).getId().intValue());
		assertEquals(2, tlDtos.get(2).getId().intValue());
		assertEquals(4, tlDtos.get(3).getId().intValue());
		assertEquals(5, tlDtos.get(4).getId().intValue());
		 
	 }

	 /**
	  *  @see TrayLabelServiceImpl#setTrayLabels(List)
	  */
	 @Test
	 public void testSetTrayLabels() {
		 TrayLabelServiceImpl server = new TrayLabelServiceImpl();
			
		SalesOrder so1 = oneSalesOrder("Haggens", false);
		SalesOrder so2 = oneSalesOrder("Freds", false);
		SalesOrder so3 = oneSalesOrder("DPI", true);
		
		QuickbooksItem qbItem1 = oneQuickbooksItem("27001-6", null, "7Inch", "NewYork", new Double(1));
		QuickbooksItem qbItem2 = oneQuickbooksItem("27002-6", null, "7Inch", "VeryBerry", new Double(1));
		QuickbooksItem qbItem3 = oneQuickbooksItem("27003-6", null, "7Inch", "WhiteChocolateRasp", new Double(1));
		QuickbooksItem qbItem4 = oneQuickbooksItem("27004-6", null, "7Inch", "ChocolateTruffle", new Double(1));
		
		QuickbooksItem qbItemWithSubs = oneQuickbooksItem("27039-6", null, "7Inch", "Fab4", new Double(1));

		addSubItems(qbItemWithSubs, 
				oneSubItem(qbItemWithSubs, qbItem1, 1.5),
				oneSubItem(qbItemWithSubs, qbItem2, 1.5),
				oneSubItem(qbItemWithSubs, qbItem3, 1.5),
				oneSubItem(qbItemWithSubs, qbItem4, 1.5)
		);
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(so1, qbItem1, 10);
		SalesOrderLineItem soli2 = BusinessObjects.oneSalesOrderLineItem(so2, qbItem2, 20);
		SalesOrderLineItem soli3 = BusinessObjects.oneSalesOrderLineItem(so2, qbItemWithSubs, 20);
		SalesOrderLineItem soli4 = BusinessObjects.oneSalesOrderLineItem(so2, qbItem4, 20);
		
		addLineItems(so1, soli1);
		addLineItems(so2, soli2, soli3);
		addLineItems(so3, soli4);
		
		TrayLabelDto tlDto1 = new TrayLabelDto(null, new SalesOrderLineItemDto(1, new SalesOrderDto("Haggens"), new QuickbooksItemDto("27001-6", new Double(1)), null, 10), "2B111A1", 5);
		TrayLabelDto tlDto2 = new TrayLabelDto(null, new SalesOrderLineItemDto(null, new SalesOrderDto("INVENTORY"), new QuickbooksItemDto("27039-6", new Double(1)), new QuickbooksItemDto("27001-6", 4), 20), "2B111A1", 5);
		
		server.setTrayLabels(newArrayList(tlDto1, tlDto2));
		
		//tlDto1 x5 + tlDto1 x2
		assertSize(new TrayLabelHibernateDao().findAll(), 7);
	 }
	 
}
