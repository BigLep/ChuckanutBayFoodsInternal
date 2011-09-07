package com.chuckanutbay.businessobjects;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "quickbooks_items")
public class QuickbooksItem {
	private String id;
	private String description;
	private String instructions;
	private Integer pack;
	private QuickbooksItemSupplement quickbooksItemSupplement;
	private Set<SalesOrderLineItem> salesOrderLineItems;
	private Set<QuickbooksSubItem> subItems;
	
	public QuickbooksItem() {
		super();
	}

	public QuickbooksItem(String id, String description,
			String instructions, String flavor, String size,
			Set<SalesOrderLineItem> salesOrderLineItems) {
		super();
		this.id = id;
		this.description = description;
		this.instructions = instructions;
		quickbooksItemSupplement.setFlavor(flavor);
		quickbooksItemSupplement.setSize(size);
		this.salesOrderLineItems = salesOrderLineItems;
	}
	
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
	
	@Column(name = "unique_instructions", nullable = true, length = 255)
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	@Transient
	public String getFlavor() {
		if (quickbooksItemSupplement != null) {
			return quickbooksItemSupplement.getFlavor();
		} else {
			return null;
		}
	}
	public void setFlavor(String flavor) {
		if (quickbooksItemSupplement != null) {
			quickbooksItemSupplement.setFlavor(flavor);
		}
	}
	
	@Transient
	public String getSize() {
		if (quickbooksItemSupplement != null) {
			return quickbooksItemSupplement.getSize();
		} else {
			return null;
		}
	}
	public void setSize(String size) {
		if (quickbooksItemSupplement != null) {
			quickbooksItemSupplement.setSize(size);
		}
	}
	
	@Transient
	public String getShortName() {
		if (quickbooksItemSupplement != null) {
			return quickbooksItemSupplement.getShortName();
		} else {
			return null;
		}
	}
	public void setShortName(String shortName) {
		if (quickbooksItemSupplement != null) {
			quickbooksItemSupplement.setShortName(shortName);
		}
	}
	
	@Transient
	public Double getCasesPerTray() {
		if (quickbooksItemSupplement != null) {
			return quickbooksItemSupplement.getCasesPerTray();
		} else {
			return null;
		}
	}
	
	public void setCasesPerTray(Double casesPerTray) {
		if (quickbooksItemSupplement != null) {
			quickbooksItemSupplement.setCasesPerTray(casesPerTray);
		}
	}
	
	@Column(name = "pack", nullable = true)
	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="quickbooks_item_supplement_id")
	public QuickbooksItemSupplement getQuickbooksItemSupplement() {
		return quickbooksItemSupplement;
	}

	public void setQuickbooksItemSupplement(
			QuickbooksItemSupplement quickbooksItemSupplement) {
		this.quickbooksItemSupplement = quickbooksItemSupplement;
	}
	
	@Transient
	public NutritionLabel getNutritionLabel() {
		if (quickbooksItemSupplement != null) {
			return quickbooksItemSupplement.getNutritionLabel();
		} else {
			return null;
		}
	}

	public void setNutritionLabel(NutritionLabel nutritionLabel) {
		if (quickbooksItemSupplement != null) {
			quickbooksItemSupplement.setNutritionLabel(nutritionLabel);
		}
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
