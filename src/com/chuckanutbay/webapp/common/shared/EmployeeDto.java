package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.List;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.documentation.Terminology;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * {@link Terminology#DTO} for {@link Employee}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class EmployeeDto implements Serializable, Comparable<EmployeeDto> {
	private static final long serialVersionUID = 1L;
	public Integer id;
	public String firstName;
	public String lastName;
	public int minsWorkedThisWeek;
	public String comment;
	public List<EmployeeWorkIntervalActivityPercentageDto> employeeWorkIntervalPercentages;
	public Integer barcodeNumber;
	
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
			Integer barcodeNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.minsWorkedThisWeek = minsWorkedThisWeek;
		this.employeeWorkIntervalPercentages = employeeWorkIntervalPercentages;
		this.barcodeNumber = barcodeNumber;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<EmployeeWorkIntervalActivityPercentageDto> getEmployeeWorkIntervalPercentages() {
		return employeeWorkIntervalPercentages;
	}

	public void setEmployeeWorkIntervalPercentages(
			List<EmployeeWorkIntervalActivityPercentageDto> employeeWorkIntervalPercentages) {
		this.employeeWorkIntervalPercentages = employeeWorkIntervalPercentages;
	}

	public Integer getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(Integer barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(barcodeNumber);
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
	public String toString() {
		return Objects.toStringHelper(this)
			.add("barcodeNumber", barcodeNumber)
			.toString();
	}

	@Override
	public int compareTo(EmployeeDto that) {
		return ComparisonChain.start()
			.compare(this.firstName, that.firstName)
			.compare(this.lastName, that.lastName)
			.result();
	}
}
