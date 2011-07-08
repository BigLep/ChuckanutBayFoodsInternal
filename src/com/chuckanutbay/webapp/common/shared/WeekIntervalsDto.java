package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.List;

public class WeekIntervalsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<DayIntervalsDto> dayIntervalsDtos;
	public Integer minutesNormalPay;
	public Integer minutesOvertime;
	
	public WeekIntervalsDto() {}

	public List<DayIntervalsDto> getDayIntervalsDtos() {
		return dayIntervalsDtos;
	}

	public void setDayIntervalsDtos(List<DayIntervalsDto> dayIntervalsDtos) {
		this.dayIntervalsDtos = dayIntervalsDtos;
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
