package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

public class TrayLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private SalesOrderLineItemDto salesOrderLineItemDto;
	private String lotCode;
	private double cakes;
	private QuickbooksItemDto subItem;
	private String barcodeUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SalesOrderLineItemDto getSalesOrderLineItemDto() {
		return salesOrderLineItemDto;
	}
	public void setSalesOrderLineItemDto(SalesOrderLineItemDto salesOrderLineItemDto) {
		this.salesOrderLineItemDto = salesOrderLineItemDto;
	}
	public String getLotCode() {
		return lotCode;
	}
	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}
	public double getCakes() {
		return cakes;
	}
	public void setCakes(double cakes) {
		this.cakes = cakes;
	}
	public QuickbooksItemDto getSubItem() {
		return subItem;
	}
	public void setSubItem(QuickbooksItemDto subItem) {
		this.subItem = subItem;
	}
	public String getBarcodeUrl() {
		return barcodeUrl;
	}
	public void setBarcodeUrl(String barcodeUrl) {
		this.barcodeUrl = barcodeUrl;
	}

}
