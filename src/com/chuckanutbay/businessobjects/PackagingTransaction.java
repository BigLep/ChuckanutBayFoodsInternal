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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "packaging_transactions")
public class PackagingTransaction {
	private Integer id;
	private Employee employee;
	private Date date;
	private TrayLabel trayLabel;
	private String startLabel1;
	private String endLabel1;
	private String startLabel2;
	private String endLabel2;
	private Double testWeight;
	private Integer damagedQty;
	private DamageCode damageCode;
	
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
	@JoinColumn(name = "employee_id", nullable = false)
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", length = 19)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tray_label_id", nullable = false)
	public TrayLabel getTrayLabel() {
		return trayLabel;
	}
	public void setTrayLabel(TrayLabel trayLabel) {
		this.trayLabel = trayLabel;
	}
	
	@Column(name = "start_label_1", nullable = true, length = 15)
	public String getStartLabel1() {
		return startLabel1;
	}
	public void setStartLabel1(String startLabel1) {
		this.startLabel1 = startLabel1;
	}
	
	@Column(name = "end_label_1", nullable = true, length = 15)
	public String getEndLabel1() {
		return endLabel1;
	}
	public void setEndLabel1(String endLabel1) {
		this.endLabel1 = endLabel1;
	}
	
	@Column(name = "start_label_2", nullable = true, length = 15)
	public String getStartLabel2() {
		return startLabel2;
	}
	public void setStartLabel2(String startLabel2) {
		this.startLabel2 = startLabel2;
	}
	
	@Column(name = "end_label_2", nullable = true, length = 15)
	public String getEndLabel2() {
		return endLabel2;
	}
	public void setEndLabel2(String endLabel2) {
		this.endLabel2 = endLabel2;
	}

	@Column(name = "test_weight", nullable = true)
	public Double getTestWeight() {
		return testWeight;
	}
	public void setTestWeight(Double testWeight) {
		this.testWeight = testWeight;
	}
	
	@Column(name = "damaged_qty", nullable = true, length = 3)
	public Integer getDamagedQty() {
		return damagedQty;
	}
	public void setDamagedQty(Integer damagedQty) {
		this.damagedQty = damagedQty;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "damage_code_id", nullable = true)
	public DamageCode getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(DamageCode damageCode) {
		this.damageCode = damageCode;
	}
	

}
