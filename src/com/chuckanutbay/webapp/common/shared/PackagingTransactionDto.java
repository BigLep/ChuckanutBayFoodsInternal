package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

public class PackagingTransactionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private EmployeeDto employee;
	private Date date;
	private Integer trayLabelId;
	private String startLabel1;
	private String endLabel1;
	private String startLabel2;
	private String endLabel2;
	private Double testWeight;
	private Integer damagedQty;
	private DamageCodeDto damageCode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public EmployeeDto getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDto employee) {
		this.employee = employee;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getTrayLabelId() {
		return trayLabelId;
	}
	public void setTrayLabelId(Integer trayLabelId) {
		this.trayLabelId = trayLabelId;
	}
	public String getStartLabel1() {
		return startLabel1;
	}
	public void setStartLabel1(String startLabel1) {
		this.startLabel1 = startLabel1;
	}
	public String getEndLabel1() {
		return endLabel1;
	}
	public void setEndLabel1(String endLabel1) {
		this.endLabel1 = endLabel1;
	}
	public String getStartLabel2() {
		return startLabel2;
	}
	public void setStartLabel2(String startLabel2) {
		this.startLabel2 = startLabel2;
	}
	public String getEndLabel2() {
		return endLabel2;
	}
	public void setEndLabel2(String endLabel2) {
		this.endLabel2 = endLabel2;
	}
	public Double getTestWeight() {
		return testWeight;
	}
	public void setTestWeight(Double testWeight) {
		this.testWeight = testWeight;
	}
	public Integer getDamagedQty() {
		return damagedQty;
	}
	public void setDamagedQty(Integer damagedQty) {
		this.damagedQty = damagedQty;
	}
	public DamageCodeDto getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(DamageCodeDto damageCode) {
		this.damageCode = damageCode;
	}

}
