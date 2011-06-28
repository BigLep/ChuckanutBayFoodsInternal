package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.documentation.Terminology;

/**
 * A Serializable object of an employee activity done during an {@link EmployeWorkInterval}
 * @author mloeppky
 * {@link Terminology#DTO} for {@link Employee}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class ActivityDto implements Serializable {
	public Integer id;
	public String name;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public ActivityDto(){}
	
	/**
	 * Basic constructor for {@link ActivityDto} objects
	 * @param id Database id for the activity
	 * @param name Name of the activity
	 */
	public ActivityDto(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
