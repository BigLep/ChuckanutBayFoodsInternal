package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Set;

import com.google.common.base.Objects;

public abstract class TrayLabelDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected String lotCode;
	protected double cases;
	protected double cakesPerCase = 0.0;
	protected double casesPerTray = 0.0;
	protected Set<PackagingTransactionDto> packagingTransactions;
	
	public TrayLabelDto() {}

	public Integer getId() {
		return id;
	}
	public TrayLabelDto setId(Integer id) {
		this.id = id;
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
	
	public Set<PackagingTransactionDto> getPackagingTransactions() {
		return packagingTransactions;
	}

	public void setPackagingTransactions(
			Set<PackagingTransactionDto> packagingTransactions) {
		this.packagingTransactions = packagingTransactions;
	}
	
	public abstract QuickbooksItemDto getQbItem();
	
	public abstract QuickbooksItemDto getQbSubItem();
	
	public abstract double getMaximumCases();

	
	@Override
	public int hashCode(){
		return Objects.hashCode(id);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof TrayLabelDto) {
			TrayLabelDto that = (TrayLabelDto)object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}
}
