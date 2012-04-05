package com.chuckanutbay.businessobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quickbooks_item_supplements")
public class QuickbooksItemSupplement {
	private String id;
	private String flavor;
	private String size;
	private String shortName;
	private Double casesPerTray;
	private QuickbooksItem quickbooksItem;
	private NutritionLabel nutritionLabel;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "product_type", nullable = true)
	public String getFlavor() {
		return flavor;
	}
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	
	@Column(name = "size", nullable = true)
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Column(name = "short_name", nullable = true)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Column(name = "cases_per_tray", nullable = true)
	public Double getCasesPerTray() {
		return casesPerTray;
	}
	public void setCasesPerTray(Double casesPerTray) {
		this.casesPerTray = casesPerTray;
	}
	
	@OneToOne(mappedBy = "quickbooksItemSupplement")
	public QuickbooksItem getQuickbooksItem() {
		return quickbooksItem;
	}
	public void setQuickbooksItem(QuickbooksItem quickbooksItem) {
		this.quickbooksItem = quickbooksItem;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nutrition_label_id", nullable = true)
	public NutritionLabel getNutritionLabel() {
		return nutritionLabel;
	}
	public void setNutritionLabel(NutritionLabel nutritionLabel) {
		this.nutritionLabel = nutritionLabel;
	}
}
