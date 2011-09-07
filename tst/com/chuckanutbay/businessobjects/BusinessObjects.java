package com.chuckanutbay.businessobjects;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.Set;

import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotStickerColorDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotStickerColorHibernateDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemSupplementDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemSupplementHibernateDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksSubItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.SalesOrderDao;
import com.chuckanutbay.businessobjects.dao.SalesOrderHibernateDao;
import com.chuckanutbay.businessobjects.dao.SalesOrderLineItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelHibernateDao;
import com.google.gwt.dev.util.collect.HashSet;

/**
 * Utility methods for creating business objects.
 */
public class BusinessObjects {

	/**
	 * @param barcodeNumber
	 * @param firstName
	 * @param lastName
	 * @return one persisted {@link Employee}.
	 */
	public static Employee oneEmployee(Integer barcodeNumber, String firstName, String lastName) {
		Employee employee = new Employee(null, firstName, lastName, barcodeNumber);
		EmployeeDao dao = new EmployeeHibernateDao();
		dao.makePersistent(employee);
		return employee;
	}
	
	/**
	 * @param barcodeNumber
	 * @param firstName
	 * @param lastName
	 * @param shift
	 * @return
	 */
	public static Employee oneEmployee(Integer barcodeNumber, String firstName, String lastName, Integer shift) {
		Employee employee = oneEmployee(barcodeNumber, firstName, lastName);
		employee.setShift(shift);
		return employee;
	}
	
	/**
	 * @param employee
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static EmployeeWorkInterval oneEmployeeWorkInterval(Employee employee, Date startDate, Date endDate) {
		EmployeeWorkInterval ewi = new EmployeeWorkInterval(employee, startDate, endDate);
		EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
		dao.makePersistent(ewi);
		return ewi;
	}
	
	public static Set<EmployeeWorkIntervalActivityPercentage> oneEmployeeWorkIntervalActivityPercentage(EmployeeWorkInterval ewi, Activity activity, Integer percentage) {
		EmployeeWorkIntervalActivityPercentage ewiap = new EmployeeWorkIntervalActivityPercentage(activity, percentage, ewi);
		EmployeeWorkIntervalActivityPercentageDao dao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
		dao.makePersistent(ewiap);
		Set<EmployeeWorkIntervalActivityPercentage> set = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		set.add(ewiap);
		return set;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static Activity oneActivity(String name) {
		Activity activity = new Activity(name);
		ActivityDao dao = new ActivityHibernateDao();
		dao.makePersistent(activity);
		return activity;
	}
	
	public static InventoryItem oneInventoryItem(String id, String description) {
		InventoryItem inventoryItem = new InventoryItem(id, description);
		InventoryItemDao dao = new InventoryItemHibernateDao();
		dao.makePersistent(inventoryItem);
		return inventoryItem;
	}
	
	public static InventoryLotStickerColor oneInventoryLotStickerColor(String name) {
		InventoryLotStickerColor color = new InventoryLotStickerColor(name);
		InventoryLotStickerColorDao dao = new InventoryLotStickerColorHibernateDao();
		dao.makePersistent(color);
		return color;
	}
	
	public static InventoryLot oneInventoryLot(InventoryLotStickerColor color, InventoryItem inventoryItem, String code, Integer qty, Date checkedIn, Date inUse, Date usedUp) {
		InventoryLot lot = new InventoryLot(color, inventoryItem, code, qty, checkedIn, inUse, usedUp);
		InventoryLotDao dao = new InventoryLotHibernateDao();
		dao.makePersistent(lot);
		return lot;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static QuickbooksItem oneQuickbooksItem(String id) {
		QuickbooksItem qbItem = new QuickbooksItem();
		qbItem.setId(id);
		qbItem.setQuickbooksItemSupplement(oneQuickbooksItemSupplement(id));
		QuickbooksItemDao dao = new QuickbooksItemHibernateDao();
		dao.makePersistent(qbItem);
		return qbItem;
	}
	
	public static QuickbooksItem oneQuickbooksItem(String id, String description, String size, String flavor, Double casesPerTray) {
		QuickbooksItem qbItem = oneQuickbooksItem(id);
		qbItem.setDescription(description);
		qbItem.setSize(size);
		qbItem.setFlavor(flavor);
		qbItem.setCasesPerTray(casesPerTray);
		return qbItem;
		
	}
	
	public static QuickbooksItemSupplement oneQuickbooksItemSupplement(String id) {
		QuickbooksItemSupplement qbItemSup = new QuickbooksItemSupplement();
		qbItemSup.setId(id);
		QuickbooksItemSupplementDao dao = new QuickbooksItemSupplementHibernateDao();
		dao.makePersistent(qbItemSup);
		return qbItemSup;
	}
	
	public static TrayLabel oneTrayLabel(double cases, String lotCode, SalesOrderLineItem salesOrderLineItem) {
		TrayLabel trayLabel = new TrayLabel();
		trayLabel.setCases(cases);
		trayLabel.setLotCode(lotCode);
		trayLabel.setSalesOrderLineItem(salesOrderLineItem);
		TrayLabelDao dao = new TrayLabelHibernateDao();
		dao.makePersistent(trayLabel);
		return trayLabel;
	}
	
	public static TrayLabel oneTrayLabel(double cases, String lotCode, SalesOrderLineItem salesOrderLineItem, QuickbooksItem subItem) {
		TrayLabel trayLabel = oneTrayLabel(cases, lotCode, salesOrderLineItem);
		trayLabel.setQuickbooksSubItem(subItem);
		return trayLabel;
	}
	
	public static SalesOrder oneSalesOrder(String customerName, boolean closed) {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setCustomerName(customerName);
		salesOrder.setOrderClosed(closed);
		SalesOrderDao dao = new SalesOrderHibernateDao();
		dao.makePersistent(salesOrder);
		return salesOrder;
	}
	
	public static SalesOrderLineItem oneSalesOrderLineItem(SalesOrder salesOrder, QuickbooksItem quickbooksItem, int cases) {
		SalesOrderLineItem lineItem = new SalesOrderLineItem();
		lineItem.setSalesOrder(salesOrder);
		lineItem.setQuickbooksItem(quickbooksItem);
		lineItem.setCases(cases);
		new SalesOrderLineItemHibernateDao().makePersistent(lineItem);
		return lineItem;
	}
	
	public static QuickbooksSubItem oneSubItem(QuickbooksItem qbItem, QuickbooksItem qbSubItem, double cakesPerCase) {
		QuickbooksSubItem subItem = new QuickbooksSubItem();
		subItem.setQuickbooksItem(qbItem);
		subItem.setSubItem(qbSubItem);
		subItem.setCakesPerCase(cakesPerCase);
		new QuickbooksSubItemHibernateDao().makePersistent(subItem);
		return subItem;
	}
	
	public static void addSubItems(QuickbooksItem qbItem, QuickbooksSubItem...subItems) {
		qbItem.setSubItems(new HashSet<QuickbooksSubItem>(newArrayList(subItems)));
	}
	
	public static void addLineItems(SalesOrder salesOrder, SalesOrderLineItem...lineItems) {
		salesOrder.setSalesOrderLineItems(new HashSet<SalesOrderLineItem>(newArrayList(lineItems)));
	}
}
