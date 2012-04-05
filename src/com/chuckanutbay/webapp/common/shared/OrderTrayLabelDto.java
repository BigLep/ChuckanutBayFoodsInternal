package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.google.common.base.Objects;

public class OrderTrayLabelDto extends TrayLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SalesOrderLineItemDto salesOrderLineItemDto;
	private double maximumCases;
	
	public OrderTrayLabelDto() {}
	
	
	public OrderTrayLabelDto(Integer id,
			SalesOrderLineItemDto salesOrderLineItemDto, String lotCode,
			double cases, double casesPerTray, double cakesPerCase) {
		super();
		this.id = id;
		this.salesOrderLineItemDto = salesOrderLineItemDto;
		this.lotCode = lotCode;
		this.cases = cases;
		this.maximumCases = cases;
		this.cakesPerCase = cakesPerCase;
		this.casesPerTray = casesPerTray;
	}
	
	public OrderTrayLabelDto(SalesOrderLineItemDto lineItem,
			double casesPerTray, double cakesPerCase, String lotCode) {
		this.setSalesOrderLineItemDto(lineItem);
		this.setCases(lineItem.getCases());
		this.setCasesPerTray(casesPerTray);
		this.setCakesPerCase(cakesPerCase);
		this.setMaximumCases(lineItem.getCases());
		this.setLotCode(lotCode);
	}


	public SalesOrderLineItemDto getSalesOrderLineItemDto() {
		return salesOrderLineItemDto;
	}
	public OrderTrayLabelDto setSalesOrderLineItemDto(SalesOrderLineItemDto salesOrderLineItemDto) {
		this.salesOrderLineItemDto = salesOrderLineItemDto;
		return this;
	}
	
	@Override
	public double getMaximumCases() {
		return maximumCases;
	}
	public void setMaximumCases(double maximumCases) {
		this.maximumCases = maximumCases;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(salesOrderLineItemDto);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof OrderTrayLabelDto) {
			OrderTrayLabelDto that = (OrderTrayLabelDto)object;
			return Objects.equal(this.salesOrderLineItemDto, that.salesOrderLineItemDto);
		}
		return false;
	}


	@Override
	public QuickbooksItemDto getQbItem() {
		return this.salesOrderLineItemDto.getQuickbooksItemDto();
	}


	@Override
	public QuickbooksItemDto getQbSubItem() {
		return this.salesOrderLineItemDto.getSubItemDto();
	}
}