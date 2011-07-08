package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

public class EmployeeWorkIntervalDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public Date startDateTime;
	public Date endDateTime;
	public Integer minutesWorked;
	
	public EmployeeWorkIntervalDto(){}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getMinutesWorked() {
		return minutesWorked;
	}

	public void setMinutesWorked(Integer minutesWorked) {
		this.minutesWorked = minutesWorked;
	}
	
	
}
