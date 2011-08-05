package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

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

@Entity
@Table(name = "sales_order_line_items")
public class SalesOrderLineItem {
	private Integer id;
	private SalesOrder salesOrder;
	private QuickbooksItem qbItem;
	private Integer cases;
	private Double amount;
	private Set<TransitSheet> transitSheets;
	
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
	@JoinColumn(name = "sales_order_id", nullable = false)
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qb_item_id", nullable = false)
	public QuickbooksItem getQbItem() {
		return qbItem;
	}
	public void setQbItem(QuickbooksItem qbItem) {
		this.qbItem = qbItem;
	}
	
	@Column(name = "cases", nullable = false, length = 10)
	public Integer getCases() {
		return cases;
	}
	public void setCases(Integer cases) {
		this.cases = cases;
	}
	
	@Column(name = "amount", nullable = true, length = 10)
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "salesOrderLineItem")
	public Set<TransitSheet> getTransitSheets() {
		return transitSheets;
	}
	public void setTransitSheets(Set<TransitSheet> transitSheets) {
		this.transitSheets = transitSheets;
	}
}
