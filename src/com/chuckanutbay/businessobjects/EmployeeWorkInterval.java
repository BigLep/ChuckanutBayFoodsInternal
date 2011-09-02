package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

@Entity
@Table(name = "employee_work_intervals")
public class EmployeeWorkInterval {

	private Integer id;
	private Employee employee;
	private Date startDateTime;
	private Date endDateTime;
	private String comment;
	private Set<EmployeeWorkIntervalActivityPercentage> employeeWorkIntervalActivityPercentages;
	
	public EmployeeWorkInterval() {}
	
	public EmployeeWorkInterval(Employee employee, Date startDateTime,
			Date endDateTime) {
		super();
		this.employee = employee;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public EmployeeWorkInterval(
			Integer id,
			Employee employee,
			Date startDateTime,
			Date endDateTime,
			Set<EmployeeWorkIntervalActivityPercentage> employeeWorkIntervalActivityPercentages) {
		super();
		this.id = id;
		this.employee = employee;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.employeeWorkIntervalActivityPercentages = employeeWorkIntervalActivityPercentages;
	}

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
	@Column(name = "start_date_time", length = 19)
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date_time", length = 19)
	public Date getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	@Column(name = "comments", length = 1000, nullable = true)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeWorkInterval")
	public Set<EmployeeWorkIntervalActivityPercentage> getEmployeeWorkIntervalActivityPercentages() {
		return employeeWorkIntervalActivityPercentages;
	}
	public void setEmployeeWorkIntervalActivityPercentages(
			Set<EmployeeWorkIntervalActivityPercentage> employeeWorkIntervalActivityPercentages) {
		this.employeeWorkIntervalActivityPercentages = employeeWorkIntervalActivityPercentages;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), employee, startDateTime);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof EmployeeWorkInterval) {
			EmployeeWorkInterval that = (EmployeeWorkInterval)object;
			if (Objects.equal(this.employee, that.employee) && Objects.equal(this.startDateTime, that.startDateTime)) {
				return true;
			}
		}
		return false;
	}
	
}
