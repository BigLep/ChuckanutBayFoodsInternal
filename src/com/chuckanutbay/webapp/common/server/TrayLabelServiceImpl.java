package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.print.Print.HP_WIRELESS_P1102W;
import static com.chuckanutbay.print.Print.print;
import static com.chuckanutbay.print.ReportGenerator.generateReport;
import static com.chuckanutbay.print.ReportUtil.TRAY_LABEL;
import static com.chuckanutbay.webapp.common.server.DtoUtils.fromTrayLabelDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toQuickbooksItemDtos;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toTrayLabelDtoFunction;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.SalesOrderHibernateDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelHibernateDao;
import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.webapp.common.client.TrayLabelService;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TrayLabelServiceImpl extends RemoteServiceServlet implements TrayLabelService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<SalesOrderLineItemDto> getSalesOrderLineItems() {
		List<SalesOrderLineItemDto> lineItemDtos = newArrayList();
		for (SalesOrderLineItemDto lineItemDto : DtoUtils.toSalesOrderLineItemDtosFunction.apply(new SalesOrderHibernateDao().findAllOpen())) {
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
		return lineItemDtos;
	}

	@Override
	public List<TrayLabelDto> getTrayLabelHistroy() {
		List<TrayLabel> trayLabels = new TrayLabelHibernateDao().findAll();
		Collections.sort(trayLabels, new Comparator<TrayLabel>() {
			@Override
			public int compare(TrayLabel tl1, TrayLabel tl2) {
				DateTime dt1 = getDateTime(tl1.getLotCode());
				DateTime dt2 = getDateTime(tl2.getLotCode());
				long difference = dt2.getMillis() - dt1.getMillis();
				if (difference == 0) {
					return tl2.getId() - tl1.getId();
				} else {
					return (int) difference;
				}
			}
		});
		return DtoUtils.transform(trayLabels, toTrayLabelDtoFunction);
		
	}

	@Override
	public void setTrayLabels(List<TrayLabelDto> newTrayLabels) {
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
		Map<String, Object> parameters = new HashMap<String, Object>();
		String idsString = "";
		System.out.println(idsString);
		for (TrayLabel trayLabel : persistedTrayLabels) {
			idsString = idsString + trayLabel.getId() + ", ";
		}
		idsString = idsString.substring(0, idsString.length() - 2); // Remove the last ', '
		parameters.put("TRAY_LABEL_IDS", idsString);
		
		String pdfFilePath = generateReport(TRAY_LABEL, parameters);
		print(pdfFilePath, HP_WIRELESS_P1102W);
	}

	@Override
	public Map<String, QuickbooksItemDto> getQuickbooksItems() {
		QuickbooksItemDao dao = new QuickbooksItemHibernateDao();
		return toQuickbooksItemDtos(dao.findCaseItems());
	}
	
	@Override
	public String getCurrentLotCode() {
		return getLotCode(new DateTime());
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
		TrayLabelDao dao = new TrayLabelHibernateDao();
		TrayLabel tl = dao.findById(trayLabel.getId());
		if (trayLabel.getSalesOrderLineItemDto().getQuickbooksItemDto() == null) {//The tray label is to be deleted
			dao.makeTransient(tl);
		} else {
			tl.setCases(trayLabel.getCases());
			tl.setLotCode(trayLabel.getLotCode());
		}
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
		List<TrayLabel> trayLabels = newArrayList();
		for (TrayLabelDto dto : trayLabelDtos) {
			trayLabels.addAll(fromTrayLabelDtoFunction.apply(dto));
		}
		// TODO Print
	}

}
