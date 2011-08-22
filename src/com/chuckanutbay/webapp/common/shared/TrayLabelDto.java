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
	private double maximumCases;
	private String barcodeUrl;
	
	public TrayLabelDto() {
		super();
	}
	
	
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
	public double getMaximumCases() {
		return maximumCases;
	}
	public TrayLabelDto setMaximumCases(double maximumCases) {
		this.maximumCases = maximumCases;
		return this;
	}
	public String getBarcodeUrl() {
		return barcodeUrl;
	}
	public TrayLabelDto setBarcodeUrl(String barcodeUrl) {
		this.barcodeUrl = barcodeUrl;
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