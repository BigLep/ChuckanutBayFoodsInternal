package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DTO} for {@link InventoryLot}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class InventoryLotDto implements Serializable {

	private Integer id;
	private String code;
	private InventoryItemDto inventoryItem;
	private Date receivedDatetime;
	private Date startUseDatetime;
	private Date endUseDatetime;

	/**
	 *  @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public InventoryLotDto(){}

	public InventoryLotDto(String code, InventoryItemDto inventoryItem, Date receivedDatetime) {
		this.code = code;
		this.inventoryItem = inventoryItem;
	  	this.receivedDatetime = receivedDatetime;
	}

	public InventoryLotDto(InventoryLotDto e) {
		this.id = e.getId();
		this.code = e.getCode();
		this.inventoryItem = e.getInventoryItem();
		this.receivedDatetime = e.getReceivedDatetime();
		this.startUseDatetime = e.getStartUseDatetime();
		this.endUseDatetime = e.getEndUseDatetime();
	}

	public void setReceivedDatetime(Date receivedDatetime) {
		this.receivedDatetime = receivedDatetime;
	}

	public void setStartUseDatetime(Date startUseDatetime) {
		this.startUseDatetime = startUseDatetime;
	}

	public void setEndUseDatetime(Date startUseDatetime) {
		this.endUseDatetime = startUseDatetime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InventoryItemDto getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItemDto inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	public Date getReceivedDatetime() {
		return receivedDatetime;
	}

	public Date getStartUseDatetime() {
		return startUseDatetime;
	}

	public Date getEndUseDatetime() {
		return endUseDatetime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
