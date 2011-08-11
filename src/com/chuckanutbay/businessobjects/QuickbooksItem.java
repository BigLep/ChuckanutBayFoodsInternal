package com.chuckanutbay.businessobjects;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quickbooks_items")
public class QuickbooksItem {
	private String id;
	private String description;
	private String upc;
	private double unitWeightOz;
	private Set<SalesOrderLineItem> salesOrderLineItems;
	private Set<TransitSheet> transitSheets;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "description", nullable = false, length = 255)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "upc", nullable = false, length = 16)
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	
	@Column(name = "unit_weight_oz", nullable = false, length = 6)
	public double getUnitWeightOz() {
		return unitWeightOz;
	}
	public void setUnitWeightOz(double unitWeightOz) {
		this.unitWeightOz = unitWeightOz;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quickbooksItem")
	public Set<SalesOrderLineItem> getSalesOrderLineItems() {
		return salesOrderLineItems;
	}
	public void setSalesOrderLineItems(Set<SalesOrderLineItem> salesOrderLineItems) {
		this.salesOrderLineItems = salesOrderLineItems;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quickbooksItem")
	public Set<TransitSheet> getTransitSheets() {
		return transitSheets;
	}
	public void setTransitSheets(Set<TransitSheet> transitSheets) {
		this.transitSheets = transitSheets;
	}
	
	
}
