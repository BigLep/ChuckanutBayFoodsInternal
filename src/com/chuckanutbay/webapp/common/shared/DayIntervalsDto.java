package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.List;

public class DayIntervalsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<EmployeeWorkIntervalDto> employeeWorkIntervals;
	public Integer totalMinutesWorked;
	
	public DayIntervalsDto() {}
	
	public List<EmployeeWorkIntervalDto> getEmployeeWorkIntervals() {
		return employeeWorkIntervals;
	}
	public void setEmployeeWorkIntervals(
			List<EmployeeWorkIntervalDto> employeeWorkIntervals) {
		this.employeeWorkIntervals = employeeWorkIntervals;
	}
	public Integer getTotalMinutesWorked() {
		return totalMinutesWorked;
	}
	public void setTotalMinutesWorked(Integer totalMinutesWorked) {
		this.totalMinutesWorked = totalMinutesWorked;
	}
	
	
}
