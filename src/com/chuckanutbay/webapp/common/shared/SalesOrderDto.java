package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

public class SalesOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String customerName;
	private Date shipdate;
	private String customerInstructions;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getShipdate() {
		return shipdate;
	}
	public void setShipdate(Date shipdate) {
		this.shipdate = shipdate;
	}
	public String getCustomerInstructions() {
		return customerInstructions;
	}
	public void setCustomerInstructions(String customerInstructions) {
		this.customerInstructions = customerInstructions;
	}

}
