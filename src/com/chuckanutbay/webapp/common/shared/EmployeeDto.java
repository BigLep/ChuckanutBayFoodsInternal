package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.List;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.documentation.Terminology;
import com.google.common.base.Objects;

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
	public List<EmployeeWorkIntervalActivityPercentageDto> employeeWorkIntervalPercentages;
	public Barcode barcodeNumber;
	
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
			List<EmployeeWorkIntervalActivityPercentageDto> employeeWorkIntervalPercentages,
			Barcode barcode) {
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

	public List<EmployeeWorkIntervalActivityPercentageDto> getEmployeeWorkIntervalPercentages() {
		return employeeWorkIntervalPercentages;
	}

	public void setEmployeeWorkIntervalPercentages(
			List<EmployeeWorkIntervalActivityPercentageDto> employeeWorkIntervalPercentages) {
		this.employeeWorkIntervalPercentages = employeeWorkIntervalPercentages;
	}

	public Barcode getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(Barcode barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), barcodeNumber);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof EmployeeDto) {
			EmployeeDto that = (EmployeeDto)object;
			return Objects.equal(this.barcodeNumber, that.barcodeNumber);
		}
		return false;
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
