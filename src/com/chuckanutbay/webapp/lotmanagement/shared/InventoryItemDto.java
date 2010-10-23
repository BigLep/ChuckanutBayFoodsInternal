package com.chuckanutbay.webapp.lotmanagement.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InventoryItemDto implements IsSerializable {
	private String id;
	private String description;
	
	public InventoryItemDto(String id, String description) {
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
