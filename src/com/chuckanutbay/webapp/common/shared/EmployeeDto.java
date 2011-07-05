package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Set;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Terminology;
/**
 * {@link Terminology#DTO} for {@link Employee}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class EmployeeDto implements Serializable, Comparable<Object> {
	private static final long serialVersionUID = 1L;
	public Integer id;
	public String firstName;
	public String lastName;
	public int minsWorkedThisWeek;
	public Set<EmployeeWorkIntervalPercentageDto> employeeWorkIntervalPercentages;
	public BarcodeDto barcodeNumber;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public EmployeeDto(){}
	
	/**
	 * Basic constructor for {@link EmployeeDto}
	 * @param id Employee id in batabase
	 * @param firstName Employee's first name
	 * @param lastName Employee's last name
	 * @param minsWorkedThisWeek The number of minutes that the employee has worked in the current work week
	 * @param employeeWorkIntervalPercentages The set of most recent activity percentages the employee has worked
	 * @param barcode The barcode associated with the employee
	 */
	public EmployeeDto(
			Integer id,
			String firstName,
			String lastName,
			int minsWorkedThisWeek,
			Set<EmployeeWorkIntervalPercentageDto> employeeWorkIntervalPercentages,
			BarcodeDto barcode) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.minsWorkedThisWeek = minsWorkedThisWeek;
		this.employeeWorkIntervalPercentages = employeeWorkIntervalPercentages;
		this.barcodeNumber = barcode;
	}

	/*####################################
	 * JavaBean getters/setters 
	 *####################################*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getMinsWorkedThisWeek() {
		return minsWorkedThisWeek;
	}

	public void setMinsWorkedThisWeek(int minsWorkedThisWeek) {
		this.minsWorkedThisWeek = minsWorkedThisWeek;
	}

	public Set<EmployeeWorkIntervalPercentageDto> getEmployeeWorkIntervalPercentages() {
		return employeeWorkIntervalPercentages;
	}

	public void setEmployeeWorkIntervalPercentages(
			Set<EmployeeWorkIntervalPercentageDto> employeeWorkIntervalPercentages) {
		this.employeeWorkIntervalPercentages = employeeWorkIntervalPercentages;
	}

	public BarcodeDto getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(BarcodeDto barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	
	/**
	 * Determines if two {@link EmployeeDto}s are the same based on their {@link BarcodeDto}s.
	 * @param employee The {@link EmployeeDto} to check for equivalency.
	 * @return Returns true if the two {@link EmployeeDto}s have the same barcode number.
	 */
	public boolean equals(EmployeeDto employee) {
		return this.barcodeNumber.equals(employee.barcodeNumber);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof EmployeeDto) {
			EmployeeDto that = (EmployeeDto)o;
			return (this.getLastName().compareTo(that.getLastName()));
		}
		return 0;
	}
}
