package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.documentation.Terminology;

/**
 * A Serializable object of an employee activity done during an {@link EmployeWorkInterval}
 * @author mloeppky
 * {@link Terminology#DTO} for {@link Employee}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class ActivityDto implements Serializable, Comparable<Object> {
	
	private static final long serialVersionUID = 1L;
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
	
	/**
	 * Checks if the two {@link ActivityDto}s have the same id number.
	 * @param activity The {@link ActivityDto} to test for equivalency with with.
	 * @return Returns true if the two {@link ActivityDto}s have the same id number.
	 */
	public boolean equals(ActivityDto activity) {
		if (this.getId().equals(activity.getId())) {
			return true;
		} else {
			return false;
		}
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

	@Override
	public int compareTo(Object o) {
		if (o instanceof ActivityDto) {
			ActivityDto that = (ActivityDto)o;
			return (this.getId() - that.getId());
		}
		return 0;
	}
}
