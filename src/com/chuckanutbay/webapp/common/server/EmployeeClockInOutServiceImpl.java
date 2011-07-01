package com.chuckanutbay.webapp.common.server;

import java.util.HashSet;
import java.util.Set;

import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.webapp.common.client.EmployeeClockInOutService;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalPercentageDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EmployeeClockInOutServiceImpl extends RemoteServiceServlet implements EmployeeClockInOutService {

	private static final long serialVersionUID = 1L;
	
	private Set<EmployeeDto> employees;
	private Set<EmployeeWorkIntervalPercentageDto> activitiyPercentages;
	private Set<ActivityDto> activities;
	
	public EmployeeClockInOutServiceImpl() {
		activities = new HashSet<ActivityDto>();
		activities.add(new ActivityDto(1, "Production"));
		activities.add(new ActivityDto(2, "Packaging"));
		activities.add(new ActivityDto(3, "RIE"));
		activities.add(new ActivityDto(4, "DropShip"));
		activities.add(new ActivityDto(5, "Other"));
		activitiyPercentages = new HashSet<EmployeeWorkIntervalPercentageDto>();
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(1, new ActivityDto(1, "Production"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(2, new ActivityDto(2, "Packaging"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(3, new ActivityDto(3, "RIE"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(4, new ActivityDto(4, "DropShip"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(5, new ActivityDto(5, "Other"), 20));
		employees = new HashSet<EmployeeDto>();
		employees.add(new EmployeeDto(1, "Steve", "Jobs", 300, activitiyPercentages, new BarcodeDto(123456789)));
		employees.add(new EmployeeDto(2, "Bill", "Gates", 222, activitiyPercentages, new BarcodeDto(234567890)));
		employees.add(new EmployeeDto(3, "Tom", "Brady", 121, activitiyPercentages, new BarcodeDto(345678901)));
	}
	
	@Override
	public EmployeeDto clockIn(BarcodeDto barcode) {
		if (barcode.equals(new BarcodeDto(456789012))) {
			return new EmployeeDto(4, "Mitchell", "Loeppky", 1220, activitiyPercentages, new BarcodeDto(456789012));
		}
		return null;
	}

	@Override
	public void clockOut(EmployeeDto employee) {
		//Do nothing right now
	}

	@Override
	public Set<EmployeeDto> getClockedInEmployees() {
		return employees;
	}

	@Override
	public void cancelClockIn(BarcodeDto barcode) {
		//Do nothing right now
		
	}

	@Override
	public Set<ActivityDto> getActivities() {
		ActivityDao dao = new ActivityHibernateDao();
		return new HashSet<ActivityDto>(DtoUtils.transform(dao.findAll(), DtoUtils.toActivityDto));
	}
	
	
}
