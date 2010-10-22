package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemInInventory implements IsSerializable {
	private String lotCode;
	private String itemType;
	private String itemCode;
	private Date checkedInDate;
	private Date inUseDate;
	private Date usedUpDate;
	
	
	public ItemInInventory(String argLotCode, String argItemType, String argItemCode, Date argCheckedInDate) {
		lotCode = argLotCode;
		itemType = argItemType;
		itemCode = argItemCode;
	  	checkedInDate = argCheckedInDate;
	}
	public ItemInInventory(ItemInInventory e) {
		lotCode = e.getLotCode();
		itemType = e.getItemType();
		itemCode = e.getItemCode();
		checkedInDate = e.getCheckedInDate();
		inUseDate = e.getInUseDate();
		usedUpDate = e.getUsedUpDate();
	}
	public ItemInInventory(){}
	
	public void setInUseDate(Date argInUseDate) {
		inUseDate = argInUseDate;
	}
	
	public void setUsedUpDate(Date argUsedUpDate) {
		usedUpDate = argUsedUpDate;
	}
	
	public void setItemCode(String argItemCode) {
		itemCode = argItemCode;
	}
	
	public String getLotCode() {
		return lotCode;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public Date getCheckedInDate() {
		return checkedInDate;
	}
	
	public Date getInUseDate() {
		return inUseDate;
	}
	
	public Date getUsedUpDate() {
		return usedUpDate;
	}
}
