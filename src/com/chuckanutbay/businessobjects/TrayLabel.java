package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tray_labels")
public class TrayLabel {
	private Integer id;
	private SalesOrderLineItem salesOrderLineItem;
	private QuickbooksItem quickbooksSubItem;
	private String lotCode;
	private double cases;
	private PackagingTransaction packagingTransactions;
	
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
	@JoinColumn(name = "line_item_id", nullable = false)
	public SalesOrderLineItem getSalesOrderLineItem() {
		return salesOrderLineItem;
	}
	public void setSalesOrderLineItem(SalesOrderLineItem salesOrderLineItem) {
		this.salesOrderLineItem = salesOrderLineItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_item_id", nullable = false)
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
	public double getCases() {
		return cases;
	}
	public void setCases(double cases) {
		this.cases = cases;
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
