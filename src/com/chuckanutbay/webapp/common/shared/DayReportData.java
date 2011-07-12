package com.chuckanutbay.webapp.common.shared;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

public class DayReportData implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<EmployeeWorkIntervalDto> employeeWorkIntervals;
	public double totalHoursWorked = 0;
	
	public DayReportData() {}
	
	public List<EmployeeWorkIntervalDto> getEmployeeWorkIntervals() {
		return employeeWorkIntervals;
	}
	public void setEmployeeWorkIntervals(
			List<EmployeeWorkIntervalDto> employeeWorkIntervals) {
		this.employeeWorkIntervals = employeeWorkIntervals;
	}

	public double getTotalHoursWorked() {
		return totalHoursWorked;
	}

	public void setTotalHoursWorked(double totalHoursWorked) {
		this.totalHoursWorked = totalHoursWorked;
	}
	
	public void addHours(double hours) {
		totalHoursWorked += hours;
	}
	
	public void addInterval(EmployeeWorkIntervalDto interval) {
		if (employeeWorkIntervals == null) {
			employeeWorkIntervals = newArrayList();
		}
		employeeWorkIntervals.add(interval);
	}
	
}
