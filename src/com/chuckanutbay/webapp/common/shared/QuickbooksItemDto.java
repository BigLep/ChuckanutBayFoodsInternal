package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

public class QuickbooksItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String instructions;
	private String flavor;
	private String size;
	private double cakesPerCase;
	private boolean isAllergen;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getFlavor() {
		return flavor;
	}
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public double getCakesPerCase() {
		return cakesPerCase;
	}
	public void setCakesPerCase(double cakesPerCase) {
		this.cakesPerCase = cakesPerCase;
	}
	public boolean isAllergen() {
		return isAllergen;
	}
	public void setAllergen(boolean isAllergen) {
		this.isAllergen = isAllergen;
	}
	
	
}
