package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.businessobjects.util.BusinessObjectsUtil.getCakesPerCase;
import static com.chuckanutbay.print.Print.HP_WIRELESS_P1102W;
import static com.chuckanutbay.print.ReportUtil.TRAY_LABEL;
import static com.chuckanutbay.print.ReportUtil.getCompiledReportImportFilePath;
import static com.chuckanutbay.webapp.common.server.DtoUtils.fromTrayLabelDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toQuickbooksItemDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toTrayLabelDtoFunction;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.businessobjects.QuickbooksSubItem;
import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.SalesOrderHibernateDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelHibernateDao;
import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.print.Print;
import com.chuckanutbay.print.ReportGenerator;
import com.chuckanutbay.webapp.common.client.CollectionsUtils;
import com.chuckanutbay.webapp.common.client.TrayLabelService;
import com.chuckanutbay.webapp.common.shared.InventoryTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TrayLabelServiceImpl extends RemoteServiceServlet implements TrayLabelService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TrayLabelServiceImpl.class.getName());

	@Override
	public List<SalesOrderLineItemDto> getSalesOrderLineItems() {
		Timer timer = new Timer(LOGGER).start();
		List<SalesOrderLineItemDto> lineItemDtos = newArrayList();
		
		List<SalesOrder> salesOrders = new SalesOrderHibernateDao().findAllOpen();
		timer.logTime("DONE QUERY");
			
		for (SalesOrderLineItemDto lineItemDto : DtoUtils.toSalesOrderLineItemDtosFunction.apply(salesOrders)) {
			double casesRemaining = lineItemDto.getCases();
			for (TrayLabel trayLabel : new TrayLabelHibernateDao().findBySalesOrderLineItemId(lineItemDto.getId())) {
				if (trayLabel.getQuickbooksSubItem() != null) {//The tray label has a sub item
					if (lineItemDto.getSubItemDto().getId().equals(trayLabel.getQuickbooksSubItem().getId())) {//The tray label and line item have the same sub item
						casesRemaining -= trayLabel.getCases();
					}
				} else {//The tray label has no sub item
					casesRemaining -= trayLabel.getCases();
				}
			}
			if (casesRemaining > 0) {//The line item is not completed
				lineItemDto.setCases(casesRemaining);
				lineItemDtos.add(lineItemDto);
			} else {
				//Do nothing because the line item is completed
			}
		}
		
		timer.stop("METHOD FINISHED");
		return lineItemDtos;
	}

	@Override
	public List<TrayLabelDto> getTrayLabelHistroy() {
		Timer timer = new Timer(LOGGER).start();
		List<TrayLabel> trayLabels = new TrayLabelHibernateDao().findRecent(30);
		timer.logTime("DONE QUERY");
		Collections.sort(trayLabels, new Comparator<TrayLabel>() {
			@Override
			public int compare(TrayLabel tl1, TrayLabel tl2) {
				DateTime dt1 = getDateTime(tl1.getLotCode());
				DateTime dt2 = getDateTime(tl2.getLotCode());
				if (dt1 == null) {
					return 1;
				} else if (dt2 == null) {
					return -1;
				} else {
					long difference = dt2.getMillis() - dt1.getMillis();
					if (difference == 0) {
						return tl2.getId() - tl1.getId();
					} else {
						return (int) difference;
					}
				}
			}
		});
		
		List<TrayLabelDto> trayLabelDtos = DtoUtils.transform(trayLabels, toTrayLabelDtoFunction);
		timer.stop("METHOD FINISHED");
		return trayLabelDtos;
		
	}

	@Override
	public void setTrayLabels(List<TrayLabelDto> newTrayLabels) {
		Timer timer = new Timer(LOGGER).start();
		TrayLabelDao dao = new TrayLabelHibernateDao();
		List<TrayLabel> trayLabels = newArrayList();
		for (TrayLabelDto dto : newTrayLabels) {
			trayLabels.addAll(fromTrayLabelDtoFunction.apply(dto));
		}
		List<TrayLabel> persistedTrayLabels = dao.makePersistent(trayLabels);
		
		HibernateUtil.getSession().getTransaction().commit();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.logTime("DONE QUERY");
		
		print(formatForQuery(getIds(persistedTrayLabels)));
		timer.stop("METHOD FINISHED");
	}

	@Override
	public List<String> getQuickbooksItemIds() {
		Timer timer = new Timer(LOGGER).start();
		QuickbooksItemDao dao = new QuickbooksItemHibernateDao();
		List<String> qbItems = dao.findAllIds();
		timer.stop("METHOD FINISHED");
		return qbItems;
	}
	
	@Override
	public String getCurrentLotCode() {
		Timer timer = new Timer(LOGGER).start();
		String lotcode = getLotCode(new DateTime());
		timer.stop("METHOD FINISHED");
		return lotcode;
	}
	
	public String getLotCode(DateTime dt) {
		//LotCode format: crew (4A, 3B, 2C), day of year, A, year of decade
		String crew;
		int hour = dt.getHourOfDay();
		if (hour >= 5 && hour < 14) {
			crew = "2C";
		} else if (hour >= 14 && hour < 24) {
			crew = "3B";
		} else {
			crew = "4A";
		}
		return crew + dt.getDayOfYear() + "A" + String.valueOf(dt.getYear()).substring(3, 4);
	}

	@Override
	public void updateTrayLabel(TrayLabelDto trayLabel) {
		Timer timer = new Timer(LOGGER).start();
		TrayLabelDao dao = new TrayLabelHibernateDao();
		TrayLabel tl = dao.findById(trayLabel.getId());
		if (trayLabel.getLotCode() == null) {//The tray label is to be deleted
			dao.makeTransient(tl);
		} else {
			tl.setCases(trayLabel.getCases());
			tl.setLotCode(trayLabel.getLotCode());
		}		
		timer.stop("METHOD FINISHED");
	}
	
	private DateTime getDateTime(String lotCode) {
		int lcYearOfDecade = Integer.parseInt(lotCode.substring(6));
		int dtYear = new DateTime().getYear();
		String digit4 = String.valueOf(dtYear).substring(3, 4);
		int dtYearOfDecade = Integer.parseInt(digit4);
		int difference = dtYearOfDecade - lcYearOfDecade;
		DateTime dt = new DateTime();
		if (difference >= 0) {
			dt = dt.minusYears(difference);
		} else {//We need to go back a decade first
			dt = dt.minusYears(10 + difference);
		}
		int lcDayOfYear = Integer.parseInt(lotCode.substring(2, 5));
		dt = dt.withDayOfYear(lcDayOfYear);
		String crew = lotCode.substring(0, 2);
		if (crew.equals("2C")) {
			return dt.withHourOfDay(5);
		} else if (crew.equals("3B")) {
			return dt.withHourOfDay(14);
		} else if (crew.equals("4A")){
			return dt.withHourOfDay(0);
		}
		return null;
	}

	@Override
	public void printTrayLabels(Set<TrayLabelDto> trayLabelDtos) {
		Timer timer = new Timer(LOGGER).start();
		List<TrayLabel> trayLabels = newArrayList();
		for (TrayLabelDto dto : trayLabelDtos) {
			trayLabels.addAll(fromTrayLabelDtoFunction.apply(dto));
		}
		print(formatForQuery(getIds(trayLabels)));
		timer.stop("METHOD FINISHED");
	}
	
	/**
	 * Will generate tray labels for the given ids.
	 * @param ids Must be in the format of {number}, {number}, {number} etc.
	 */
	private void print(String ids) {
		Timer timer = new Timer(LOGGER).start("Print:");
		Map<String, Object> parameters = newHashMap();
		parameters.put("TRAY_LABEL_IDS", ids);
		String pdfFilePath = new ReportGenerator().generateReport(getCompiledReportImportFilePath(TRAY_LABEL), parameters);
		timer.logTime("GENERATED REPORT");
		Print.print(pdfFilePath, HP_WIRELESS_P1102W);
		timer.stop("PRINTED REPORT");
	}
	
	/**
	 * Converts the ids to a single string in the format {number}, {number}, {number} etc.
	 * @param ids
	 * @return 
	 */
	private String formatForQuery(List<String> ids) {
		if (CollectionsUtils.isEmpty(ids)) {
			return "";
		}
		String idsString = "";
		for (String id : ids) {
			idsString = idsString + id + ", ";
		}
		idsString = idsString.substring(0, idsString.length() - 2); // Remove the last ', '
		return idsString;
	}
	
	private List<String> getIds(List<TrayLabel> trayLabels) {
		List<String> strings = newArrayList();
		for (TrayLabel trayLabel : trayLabels) {
			strings.add("" + trayLabel.getId());
		}
		return strings;
	}

	@Override
	public InventoryTrayLabelDto getInventoryTrayLabelDto(String qbItemId) {
		Timer timer = new Timer(LOGGER).start();
		InventoryTrayLabelDto trayLabel = new InventoryTrayLabelDto();
		trayLabel.setCases(0.0);
		if (qbItemId.indexOf(" ") == -1) {//No sub items
			QuickbooksItem qbItem = new QuickbooksItemHibernateDao().findById(qbItemId);
			trayLabel.setCasesPerTray(qbItem.getCasesPerTray());
			trayLabel.setCakesPerCase(getCakesPerCase(qbItem));
			trayLabel.setQbItem(DtoUtils.toQuickbooksItemDtoFunction.apply(qbItem));
		} else {//Has sub item
			String primaryId = qbItemId.substring(0, qbItemId.indexOf(" "));
			String flavor = qbItemId.substring(qbItemId.indexOf(" "));
			QuickbooksItem qbItem = new QuickbooksItemHibernateDao().findById(primaryId);
			for (QuickbooksSubItem subItem : qbItem.getSubItems()) {
				if (subItem.getSubItem().getFlavor().equals(flavor)) {
					trayLabel.setQbSubItem(toQuickbooksItemDtoFunction.apply(subItem.getSubItem()));
					trayLabel.setCakesPerCase(subItem.getCakesPerCase());
					trayLabel.setCasesPerTray(subItem.getCasesPerTray());
					break;
				}
			}
			trayLabel.setQbItem(toQuickbooksItemDtoFunction.apply(qbItem));
		}
		timer.stop("METHOD FINISHED");
		return trayLabel;
	}


}
