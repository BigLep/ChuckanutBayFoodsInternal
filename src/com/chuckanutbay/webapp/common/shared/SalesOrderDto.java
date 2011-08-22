package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.Objects;

public class SalesOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String customerName;
	private Date shipdate;
	private String customerInstructions;
	
	public SalesOrderDto(int id, String customerName, Date shipdate) {
		this.id = id;
		this.customerName = customerName;
		this.shipdate = shipdate;
	}
	public SalesOrderDto() {
	}
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
	
	@Override
	public int hashCode(){
		return Objects.hashCode(id);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof SalesOrderDto) {
			SalesOrderDto that = (SalesOrderDto)object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}
}
