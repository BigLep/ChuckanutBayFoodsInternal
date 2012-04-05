package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "activities")
public class Activity {
	
	private Integer id;
	private String name;
	private Set<EmployeeWorkIntervalActivityPercentage> employeeWorkIntervalActivityPercentages;

	public Activity() {}
	
	public Activity(String name) {
		this.name = name;
	}
	
	public Activity(
			Integer id,
			String name,
			Set<EmployeeWorkIntervalActivityPercentage> employeeWorkIntervalActivityPercentages) {
		super();
		this.id = id;
		this.name = name;
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
	
	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "activity")
	public Set<EmployeeWorkIntervalActivityPercentage> getEmployeeWorkIntervalActivityPercentages() {
		return employeeWorkIntervalActivityPercentages;
	}
	public void setEmployeeWorkIntervalActivityPercentages(
			Set<EmployeeWorkIntervalActivityPercentage> employeeWorkIntervalActivityPercentages) {
		this.employeeWorkIntervalActivityPercentages = employeeWorkIntervalActivityPercentages;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), name);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof Activity) {
			Activity that = (Activity)object;
			return Objects.equal(this.name, that.name);
		}
		return false;
	}
}
