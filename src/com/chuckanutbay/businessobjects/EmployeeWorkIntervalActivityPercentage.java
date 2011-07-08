package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.Objects;


@Entity
@Table(name = "employee_work_interval_activity_percentages")
public class EmployeeWorkIntervalActivityPercentage {
	
	private Integer id;
	private EmployeeWorkInterval employeeWorkInterval;
	private Activity activity;
	private Integer percentage;
	
	public EmployeeWorkIntervalActivityPercentage() {
		
	}
	
	public EmployeeWorkIntervalActivityPercentage(Integer id, Activity activity, Integer percentage) {
		this.id = id;
		this.activity = activity;
		this.percentage = percentage;
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
	@JoinColumn(name = "employee_work_interval_id", nullable = false)
	public EmployeeWorkInterval getEmployeeWorkInterval() {
		return employeeWorkInterval;
	}
	public void setEmployeeWorkInterval(EmployeeWorkInterval employeeWorkInterval) {
		this.employeeWorkInterval = employeeWorkInterval;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id", nullable = false)
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	@Column(name = "percentage", nullable = false, length = 3)
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), employeeWorkInterval, activity);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof EmployeeWorkIntervalActivityPercentage) {
			EmployeeWorkIntervalActivityPercentage that = (EmployeeWorkIntervalActivityPercentage)object;
			if (Objects.equal(this.employeeWorkInterval, that.employeeWorkInterval) && Objects.equal(this.activity, that.activity)) {
				return true;
			}
		}
		return false;
	}
}
