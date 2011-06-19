package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DTO} for {@link InventoryItem}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class InventoryItemDto implements Serializable {
	private String id;
	private String description;

	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public InventoryItemDto(){}

	public InventoryItemDto(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}
