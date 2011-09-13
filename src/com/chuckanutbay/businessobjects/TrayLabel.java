package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tray_labels")
public class TrayLabel {
	private Integer id;
	private SalesOrderLineItem salesOrderLineItem;
	private QuickbooksItem quickbooksItem;
	private QuickbooksItem quickbooksSubItem;
	private String lotCode;
	private Double cases;
	private Double cakesPerCase;
	private Double casesPerTray;
	private Date creationDateTime;
	private PackagingTransaction packagingTransactions;
	
	public TrayLabel() {
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_item_id", nullable = true)
	public SalesOrderLineItem getSalesOrderLineItem() {
		return salesOrderLineItem;
	}
	public void setSalesOrderLineItem(SalesOrderLineItem salesOrderLineItem) {
		this.salesOrderLineItem = salesOrderLineItem;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qb_item_id", nullable = true)
	public QuickbooksItem getQuickbooksItem() {
		return quickbooksItem;
	}

	public void setQuickbooksItem(QuickbooksItem quickbooksItem) {
		this.quickbooksItem = quickbooksItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_item_id", nullable = true)
	public QuickbooksItem getQuickbooksSubItem() {
		return quickbooksSubItem;
	}
	public void setQuickbooksSubItem(QuickbooksItem quickbooksSubItem) {
		this.quickbooksSubItem = quickbooksSubItem;
	}
	
	@Column(name = "lot_code", nullable = false, length = 7)
	public String getLotCode() {
		return lotCode;
	}
	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}
	
	@Column(name = "cases", nullable = false)
	public Double getCases() {
		return cases;
	}
	public void setCases(Double cases) {
		this.cases = cases;
	}
	
	@Column(name = "cakes_per_case", nullable = true)
	public Double getCakesPerCase() {
		return cakesPerCase;
	}

	public void setCakesPerCase(Double cakesPerCase) {
		this.cakesPerCase = cakesPerCase;
	}
	
	@Column(name = "cases_per_tray", nullable = true)
	public Double getCasesPerTray() {
		return casesPerTray;
	}

	public void setCasesPerTray(Double casesPerTray) {
		this.casesPerTray = casesPerTray;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date_time")
	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "trayLabel")
	public PackagingTransaction getPackagingTransaction() {
		return packagingTransactions;
	}
	public void setPackagingTransaction(
			PackagingTransaction packagingTransaction) {
		this.packagingTransactions = packagingTransaction;
	}
	
	
}
