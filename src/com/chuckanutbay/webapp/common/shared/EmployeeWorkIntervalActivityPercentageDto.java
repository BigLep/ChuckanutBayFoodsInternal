package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.documentation.Terminology;

/**
 * A Dto version of {@link EmployeeWorkIntervalPercentage}
 * {@link Terminology#DTO} for {@link EmployeeWorkIntervalPercentage}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class EmployeeWorkIntervalActivityPercentageDto implements Serializable {
	public ActivityDto activity;
	public Integer percentage;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public EmployeeWorkIntervalActivityPercentageDto(){}
	
	/**
	 * Basic constructor for {@link EmployeeWorkIntervalActivityPercentageDto} objects
	 * @param id Database id for the {@link EmployeeWorkIntervalPercentage}
	 * @param activity The activity worked during the interval
	 * @param percentage the percentage of the interval worked on the activity
	 */
	public EmployeeWorkIntervalActivityPercentageDto(Integer id, ActivityDto activity, Integer percentage) {
		this.activity = activity;
		this.percentage = percentage;
	}
	
	/*####################################
	 * JavaBean getters/setters 
	 *####################################*/

	public ActivityDto getActivity() {
		return activity;
	}

	public void setActivity(ActivityDto activity) {
		this.activity = activity;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
}
