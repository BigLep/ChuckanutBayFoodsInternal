package com.chuckanutbay.businessobjects;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "quickbooks_items")
@SecondaryTable(name = "quickbooks_item_supplements",
		pkJoinColumns = {@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")} )
public class QuickbooksItem {
	private String id;
	private String description;
	private boolean isAllergen;
	private String instructions;
	private String flavor;
	private String size;
	private Set<SalesOrderLineItem> salesOrderLineItems;
	private Set<QuickbooksSubItem> subItems;
	
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
	
	@Column(table = "quickbooks_item_supplements", name = "is_allergen", nullable = false, length = 1)
	public boolean isAllergen() {
		return isAllergen;
	}
	public void setAllergen(boolean isAllergen) {
		this.isAllergen = isAllergen;
	}
	
	@Column(table = "quickbooks_item_supplements", name = "unique_instructions", nullable = true, length = 255)
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	@Column(table = "quickbooks_item_supplements", name = "product_type", nullable = true)
	public String getFlavor() {
		return flavor;
	}
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	
	@Column(table = "quickbooks_item_supplements", name = "size", nullable = true)
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quickbooksItem")
	public Set<SalesOrderLineItem> getSalesOrderLineItems() {
		return salesOrderLineItems;
	}
	public void setSalesOrderLineItems(Set<SalesOrderLineItem> salesOrderLineItems) {
		this.salesOrderLineItems = salesOrderLineItems;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quickbooksItem")
	public Set<QuickbooksSubItem> getSubItems() {
		return subItems;
	}
	public void setSubItems(Set<QuickbooksSubItem> subItems) {
		this.subItems = subItems;
	}
	
	
}
