package com.chuckanutbay.webapp.shared;

import java.io.Serializable;
import java.util.Date;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DTO} for {@link InventoryItem}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class RecievingInspectionDto implements Serializable {
	private Date date;
	private String	inspector;
	private String truckingCompany;
	private 

	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public RecievingInspectionDto(){}

	public RecievingInspectionDto(String id, String description) {
		this.id = id;
		this.description = description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

}
