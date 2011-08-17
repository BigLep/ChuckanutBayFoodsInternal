package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

public class SalesOrderLineItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private SalesOrderDto salesOrderDto;
	private QuickbooksItemDto quickbooksItemDto;
	private double cakes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SalesOrderDto getSalesOrderDto() {
		return salesOrderDto;
	}
	public void setSalesOrderDto(SalesOrderDto salesOrderDto) {
		this.salesOrderDto = salesOrderDto;
	}
	public QuickbooksItemDto getQuickbooksItemDto() {
		return quickbooksItemDto;
	}
	public void setQuickbooksItemDto(QuickbooksItemDto quickbooksItemDto) {
		this.quickbooksItemDto = quickbooksItemDto;
	}
	public double getCakes() {
		return cakes;
	}
	public void setCakes(double cakes) {
		this.cakes = cakes;
	}
	
	

}
