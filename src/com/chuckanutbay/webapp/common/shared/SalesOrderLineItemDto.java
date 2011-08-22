package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

public class SalesOrderLineItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private SalesOrderDto salesOrderDto;
	private QuickbooksItemDto quickbooksItemDto;
	private QuickbooksItemDto subItemDto;
	private double cases;
	
	public SalesOrderLineItemDto(int id, int cases,
			QuickbooksItemDto quickbooksItemDto, SalesOrderDto salesOrderDto) {
		this.id = id;
		this.cases = cases;
		this.quickbooksItemDto = quickbooksItemDto;
		this.salesOrderDto = salesOrderDto;
	}
	
	public SalesOrderLineItemDto() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public QuickbooksItemDto getSubItemDto() {
		return subItemDto;
	}
	public void setSubItemDto(QuickbooksItemDto subItemDto) {
		this.subItemDto = subItemDto;
	}
	public double getCases() {
		return cases;
	}
	public void setCases(double cases) {
		this.cases = cases;
	}
	
	

}
