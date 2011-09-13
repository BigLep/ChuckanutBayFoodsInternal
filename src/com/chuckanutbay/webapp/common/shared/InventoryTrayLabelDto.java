package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.google.common.base.Objects;

public class InventoryTrayLabelDto extends TrayLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuickbooksItemDto qbItem;
	private QuickbooksItemDto qbSubItem;
	
	public InventoryTrayLabelDto() {}
	
	public InventoryTrayLabelDto(Integer id, QuickbooksItemDto qbItem, QuickbooksItemDto subItem, String lotCode, double cases, double cakesPerCase, double casesPerTray) {
		this.id = id;
		this.qbItem = qbItem;
		this.qbSubItem = subItem;
		this.lotCode = lotCode;
		this.cases = cases;
		this.cakesPerCase = cakesPerCase;
		this.casesPerTray = casesPerTray;
	}
	@Override
	public QuickbooksItemDto getQbItem() {
		return qbItem;
	}

	public void setQbItem(QuickbooksItemDto qbItem) {
		this.qbItem = qbItem;
	}

	@Override
	public QuickbooksItemDto getQbSubItem() {
		return qbSubItem;
	}

	public void setQbSubItem(QuickbooksItemDto qbSubItem) {
		this.qbSubItem = qbSubItem;
	}
	
	
	
	@Override
	public int hashCode(){
		return Objects.hashCode(id);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof InventoryTrayLabelDto) {
			OrderTrayLabelDto that = (OrderTrayLabelDto)object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}

	@Override
	public double getMaximumCases() {
		return 100000000; //A really big number!
	}
}