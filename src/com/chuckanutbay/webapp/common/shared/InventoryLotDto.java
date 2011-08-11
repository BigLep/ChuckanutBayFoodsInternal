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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private InventoryItemDto inventoryItem;
	private int quantity;
	private Date receivedDatetime;
	private Date startUseDatetime;
	private Date endUseDatetime;
	private InventoryLotStickerColor inventoryLotStickerColor;
	
	public static enum InventoryLotStickerColor {
		blue, green
	}

	/**
	 *  @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public InventoryLotDto(){}

	public InventoryLotDto(String code, InventoryItemDto inventoryItem, int quantity, Date receivedDatetime) {
		this.code = code;
		this.inventoryItem = inventoryItem;
	  	this.quantity = quantity;
	  	this.receivedDatetime = receivedDatetime;
	}

	public InventoryLotDto(InventoryLotDto e) {
		this.id = e.getId();
		this.code = e.getCode();
		this.inventoryItem = e.getInventoryItem();
		this.quantity = e.getQuantity();
		this.receivedDatetime = e.getReceivedDatetime();
		this.startUseDatetime = e.getStartUseDatetime();
		this.endUseDatetime = e.getEndUseDatetime();
		this.inventoryLotStickerColor = e.getInventoryLotStickerColor();
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public InventoryLotStickerColor getInventoryLotStickerColor() {
		return inventoryLotStickerColor;
	}

	public void setInventoryLotStickerColor(InventoryLotStickerColor inventoryLotStickerColor) {
		this.inventoryLotStickerColor = inventoryLotStickerColor;
	}
}
