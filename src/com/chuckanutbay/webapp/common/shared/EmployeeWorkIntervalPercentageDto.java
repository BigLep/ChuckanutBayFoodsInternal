package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.documentation.Terminology;

/**
 * A Dto version of {@link EmployeeWorkIntervalPercentage}
 * @author mloeppky
 * {@link Terminology#DTO} for {@link EmployeeWorkIntervalPercentage}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class EmployeeWorkIntervalPercentageDto implements Serializable {
	public Integer id;
	public ActivityDto activity;
	public Integer percentage;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public EmployeeWorkIntervalPercentageDto(){}
	
	/**
	 * Basic constructor for {@link EmployeeWorkIntervalPercentageDto} objects
	 * @param id Database id for the {@link EmployeeWorkIntervalPercentage}
	 * @param activity The activity worked during the interval
	 * @param percentage the percentage of the interval worked on the activity
	 */
	public EmployeeWorkIntervalPercentageDto(Integer id, ActivityDto activity, Integer percentage) {
		this.id = id;
		this.activity = activity;
		this.percentage = percentage;
	}
	
	/*####################################
	 * JavaBean getters/setters 
	 *####################################*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
