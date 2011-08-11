package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sales_orders")
public class SalesOrder {
	private Integer id;
	private String purchaseOrder;
	private String customerName;
	private Date shipDate;
	private String addressLineOne;
	private String addressLineTwo;
	private String addressLineThree;
	private boolean orderClosed;
	private Set<SalesOrderLineItem> salesOrderLineItems;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "purchase_order", nullable = true, length = 50)
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	
	@Column(name = "customer_name", nullable = false, length = 50)
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ship_date", length = 19)
	public Date getShipDate() {
		return shipDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	
	@Column(name = "address_line_one", nullable = true, length = 255)
	public String getAddressLineOne() {
		return addressLineOne;
	}
	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}
	
	@Column(name = "address_line_two", nullable = true, length = 255)
	public String getAddressLineTwo() {
		return addressLineTwo;
	}
	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}
	
	@Column(name = "address_line_three", nullable = true, length = 255)
	public String getAddressLineThree() {
		return addressLineThree;
	}
	public void setAddressLineThree(String addressLineThree) {
		this.addressLineThree = addressLineThree;
	}
	
	@Column(name = "order_closed", nullable = false, length = 1)
	public boolean getOrderClosed() {
		return orderClosed;
	}
	public void setOrderClosed(boolean orderClosed) {
		this.orderClosed = orderClosed;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "salesOrder")
	public Set<SalesOrderLineItem> getSalesOrderLineItems() {
		return salesOrderLineItems;
	}
	public void setSalesOrderLineItems(Set<SalesOrderLineItem> salesOrderLineItems) {
		this.salesOrderLineItems = salesOrderLineItems;
	}
	
	
}
