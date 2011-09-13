package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quickbooks_sub_items")
public class QuickbooksSubItem {
	private Integer id;
	private Double cakesPerCase;
	private Double casesPerTray;
	private QuickbooksItem quickbooksItem;
	private QuickbooksItem subItem;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "cakes_per_case", nullable = false)
	public Double getCakesPerCase() {
		return cakesPerCase;
	}
	public void setCakesPerCase(Double cakesPerCase) {
		this.cakesPerCase = cakesPerCase;
	}
	
	@Column(name = "cases_per_tray", nullable = false)
	public Double getCasesPerTray() {
		return casesPerTray;
	}
	public void setCasesPerTray(Double casesPerTray) {
		this.casesPerTray = casesPerTray;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qb_item_id", nullable = false)
	public QuickbooksItem getQuickbooksItem() {
		return quickbooksItem;
	}
	public void setQuickbooksItem(QuickbooksItem quickbooksItem) {
		this.quickbooksItem = quickbooksItem;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_item_id", nullable = false)
	public QuickbooksItem getSubItem() {
		return subItem;
	}
	public void setSubItem(QuickbooksItem subItem) {
		this.subItem = subItem;
	}
	
	
}
