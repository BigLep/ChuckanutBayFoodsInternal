package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EmployeePayPeriodReportDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name;
	public Date	date;
	public Date payPeriodStart;	
	public Date payPeriodEnd;
	public List<WeekIntervalsDto> WeekIntervalsDtos;
	public Integer minutesNormalPay;
	public Integer minutesOvertime;
	
	public EmployeePayPeriodReportDto() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getPayPeriodStart() {
		return payPeriodStart;
	}

	public void setPayPeriodStart(Date payPeriodStart) {
		this.payPeriodStart = payPeriodStart;
	}

	public Date getPayPeriodEnd() {
		return payPeriodEnd;
	}

	public void setPayPeriodEnd(Date payPeriodEnd) {
		this.payPeriodEnd = payPeriodEnd;
	}

	public List<WeekIntervalsDto> getWeekIntervalsDtos() {
		return WeekIntervalsDtos;
	}

	public void setWeekIntervalsDtos(List<WeekIntervalsDto> weekIntervalsDtos) {
		WeekIntervalsDtos = weekIntervalsDtos;
	}

	public Integer getMinutesNormalPay() {
		return minutesNormalPay;
	}

	public void setMinutesNormalPay(Integer minutesNormalPay) {
		this.minutesNormalPay = minutesNormalPay;
	}

	public Integer getMinutesOvertime() {
		return minutesOvertime;
	}

	public void setMinutesOvertime(Integer minutesOvertime) {
		this.minutesOvertime = minutesOvertime;
	}
	
}
