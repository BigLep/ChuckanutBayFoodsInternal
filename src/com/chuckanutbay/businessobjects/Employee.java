package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "employees")
public class Employee {
	
	public Employee() {
		
	}
	
	public Employee(Integer id, String firstName, String lastName,
			Integer barcodeNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.barcodeNumber = barcodeNumber;
	}

	public Employee(Integer id, String firstName, String lastName,
			Integer barcodeNumber, Integer shift,
			Set<EmployeeWorkInterval> employeeWorkIntervals) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.barcodeNumber = barcodeNumber;
		this.shift = shift;
		this.employeeWorkIntervals = employeeWorkIntervals;
	}



	private Integer id;
	private String firstName;
	private String lastName;
	private Integer barcodeNumber;
	private Integer shift;
	private Set<EmployeeWorkInterval> employeeWorkIntervals = new HashSet<EmployeeWorkInterval>(0);
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

	@Column(name = "first_name", nullable = false, length = 32)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false, length = 32)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "barcode_number", nullable = false, length = 12)
	public Integer getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(Integer barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	
	@Column(name = "shift", nullable = true, length = 2)
	public Integer getShift() {
		return shift;
	}
	public void setShift(Integer shift) {
		this.shift = shift;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<EmployeeWorkInterval> getEmployeeWorkIntervals() {
		return employeeWorkIntervals;
	}
	public void setEmployeeWorkIntervals(Set<EmployeeWorkInterval> employeeWorkIntervals) {
		this.employeeWorkIntervals = employeeWorkIntervals;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<PackagingTransaction> getPackagingTransactions() {
		return packagingTransactions;
	}

	public void setPackagingTransactions(Set<PackagingTransaction> packagingTransactions) {
		this.packagingTransactions = packagingTransactions;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), barcodeNumber);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof Employee) {
			Employee that = (Employee)object;
			return Objects.equal(this.barcodeNumber, that.barcodeNumber);
		}
		return false;
	}
}
