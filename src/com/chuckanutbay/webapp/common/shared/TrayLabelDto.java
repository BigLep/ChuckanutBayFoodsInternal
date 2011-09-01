package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.google.common.base.Objects;

public class TrayLabelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private SalesOrderLineItemDto salesOrderLineItemDto;
	private String lotCode;
	private double cases;
	private double cakesPerCase = 0.0;
	private double casesPerTray = 0.0;
	private double maximumCases;
	
	public TrayLabelDto() {}
	
	
	public TrayLabelDto(Integer id,
			SalesOrderLineItemDto salesOrderLineItemDto, String lotCode,
			double cases) {
		super();
		this.id = id;
		this.salesOrderLineItemDto = salesOrderLineItemDto;
		this.lotCode = lotCode;
		this.cases = cases;
		this.maximumCases = cases;
	}


	public Integer getId() {
		return id;
	}
	public TrayLabelDto setId(Integer id) {
		this.id = id;
		return this;
	}
	public SalesOrderLineItemDto getSalesOrderLineItemDto() {
		return salesOrderLineItemDto;
	}
	public TrayLabelDto setSalesOrderLineItemDto(SalesOrderLineItemDto salesOrderLineItemDto) {
		this.salesOrderLineItemDto = salesOrderLineItemDto;
		return this;
	}
	public String getLotCode() {
		return lotCode;
	}
	public TrayLabelDto setLotCode(String lotCode) {
		this.lotCode = lotCode;
		return this;
	}
	public double getCases() {
		return cases;
	}
	public TrayLabelDto setCases(double cases) {
		this.cases = cases;
		return this;
	}
	public double getCakesPerCase() {
		return cakesPerCase;
	}


	public TrayLabelDto setCakesPerCase(double cakesPerCase) {
		this.cakesPerCase = cakesPerCase;
		return this;
	}


	public double getCasesPerTray() {
		return casesPerTray;
	}


	public TrayLabelDto setCasesPerTray(double casesPerTray) {
		this.casesPerTray = casesPerTray;
		return this;
	}


	public double getMaximumCases() {
		return maximumCases;
	}
	public TrayLabelDto setMaximumCases(double maximumCases) {
		this.maximumCases = maximumCases;
		return this;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(salesOrderLineItemDto);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof TrayLabelDto) {
			TrayLabelDto that = (TrayLabelDto)object;
			return Objects.equal(this.salesOrderLineItemDto, that.salesOrderLineItemDto);
		}
		return false;
	}
}