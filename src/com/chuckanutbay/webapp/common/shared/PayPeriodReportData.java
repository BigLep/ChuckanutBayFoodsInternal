package com.chuckanutbay.webapp.common.shared;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PayPeriodReportData implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name;
	public Date	date;
	public Date payPeriodStart;	
	public Date payPeriodEnd;
	public List<WeekReportData> weekReportData;
	public double hoursNormalPay = 0;
	public double hoursOvertime = 0;
	
	public PayPeriodReportData() {}

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

	public List<WeekReportData> getWeekReportData() {
		return weekReportData;
	}

	public void setWeekReportData(List<WeekReportData> weekReportData) {
		this.weekReportData = weekReportData;
	}

	public double getHoursNormalPay() {
		return hoursNormalPay;
	}

	public void setHoursNormalPay(double hoursNormalPay) {
		this.hoursNormalPay = hoursNormalPay;
	}

	public double getHoursOvertime() {
		return hoursOvertime;
	}

	public void setHoursOvertime(double hoursOvertime) {
		this.hoursOvertime = hoursOvertime;
	}
	
	public void addNormalPayHours(double hours) {
		hoursNormalPay += hours;
	}
	
	public void addOvertimeHours(double hours) {
		hoursOvertime += hours;
	}
	
	public void addInterval(WeekReportData week) {
		if (weekReportData == null) {
			weekReportData = newArrayList();
		}
		weekReportData.add(week);
	}
	
	
}
