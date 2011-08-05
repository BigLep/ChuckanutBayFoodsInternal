package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transit_sheets")
public class TransitSheet {
	private Integer id;
	private SalesOrderLineItem salesOrderLineItem;
	private QuickbooksItem qbItem;
	private Date bakedDateTime;
	private Integer crew;
	private Integer cakes;
	private Set<PackagingTransaction> packagingTransactions;
	
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
	@JoinColumn(name = "qb_item_id", nullable = false)
	public QuickbooksItem getQbItem() {
		return qbItem;
	}
	public void setQbItem(QuickbooksItem qbItem) {
		this.qbItem = qbItem;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "baked_date_time", length = 19)
	public Date getBakedDateTime() {
		return bakedDateTime;
	}
	public void setBakedDateTime(Date bakedDateTime) {
		this.bakedDateTime = bakedDateTime;
	}
	
	@Column(name = "crew", nullable = false, length = 1)
	public Integer getCrew() {
		return crew;
	}
	public void setCrew(Integer crew) {
		this.crew = crew;
	}
	
	@Column(name = "cakes", nullable = false, length = 6)
	public Integer getCakes() {
		return cakes;
	}
	public void setCakes(Integer cakes) {
		this.cakes = cakes;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transitSheet")
	public Set<PackagingTransaction> getPackagingTransactions() {
		return packagingTransactions;
	}
	public void setPackagingTransactions(
			Set<PackagingTransaction> packagingTransactions) {
		this.packagingTransactions = packagingTransactions;
	}
	
	
}
