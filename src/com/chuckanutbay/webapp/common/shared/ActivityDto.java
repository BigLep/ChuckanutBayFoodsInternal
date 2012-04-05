package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.documentation.Terminology;
import com.google.common.base.Objects;

/**
 * A Serializable object of an employee activity done during an {@link EmployeWorkInterval}
 * {@link Terminology#DTO} for {@link Activity}.
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
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), name);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof ActivityDto) {
			ActivityDto that = (ActivityDto)object;
			return Objects.equal(this.name, that.name);
		}
		return false;
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
