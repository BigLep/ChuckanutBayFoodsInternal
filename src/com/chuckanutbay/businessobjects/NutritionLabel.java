package com.chuckanutbay.businessobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nutrition_labels")
public class NutritionLabel {
	private Integer id;
	private boolean isAllergen;
	private String crustType;
	private String crustAmount;
	private String batterType;
	private String batterAmount;
	private String decorationType;
	private String decorationAmount;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "is_allergen", nullable = true, length = 1)
	public boolean isAllergen() {
		return isAllergen;
	}
	public void setAllergen(boolean isAllergen) {
		this.isAllergen = isAllergen;
	}
	
	@Column(name = "crust_type", nullable = true, length = 20)
	public String getCrustType() {
		return crustType;
	}
	public void setCrustType(String crustType) {
		this.crustType = crustType;
	}
	
	@Column(name = "crust_amount", nullable = true, length = 20)
	public String getCrustAmount() {
		return crustAmount;
	}
	public void setCrustAmount(String crustAmount) {
		this.crustAmount = crustAmount;
	}
	
	@Column(name = "batter_type", nullable = true, length = 20)
	public String getBatterType() {
		return batterType;
	}
	public void setBatterType(String batterType) {
		this.batterType = batterType;
	}
	
	@Column(name = "batter_amount", nullable = true, length = 20)
	public String getBatterAmount() {
		return batterAmount;
	}
	public void setBatterAmount(String batterAmount) {
		this.batterAmount = batterAmount;
	}
	
	@Column(name = "decoration_type", nullable = true, length = 20)
	public String getDecorationType() {
		return decorationType;
	}
	public void setDecorationType(String decorationType) {
		this.decorationType = decorationType;
	}
	
	@Column(name = "decoration_amount", nullable = true, length = 20)
	public String getDecorationAmount() {
		return decorationAmount;
	}
	public void setDecorationAmount(String decorationAmount) {
		this.decorationAmount = decorationAmount;
	}
	
	
}

