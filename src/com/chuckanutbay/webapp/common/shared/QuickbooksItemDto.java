package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.google.common.base.Objects;

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
	private double casesPerTray;
	private boolean isAllergen;
	
	public QuickbooksItemDto(String id, String flavor, int cakesPerCase, int casesPerTray) {
		this.id = id;
		this.flavor = flavor;
		this.cakesPerCase = cakesPerCase;
		this.casesPerTray = casesPerTray;
	}
	public QuickbooksItemDto() {
	}
	public QuickbooksItemDto(String id, String instructions, String flavor,
			String size, double cakesPerCase, double casesPerTray,
			boolean isAllergen) {
		super();
		this.id = id;
		this.instructions = instructions;
		this.flavor = flavor;
		this.size = size;
		this.cakesPerCase = cakesPerCase;
		this.casesPerTray = casesPerTray;
		this.isAllergen = isAllergen;
	}
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
	public double getCasesPerTray() {
		return casesPerTray;
	}
	public void setCasesPerTray(double casesPerTray) {
		this.casesPerTray = casesPerTray;
	}
	public boolean isAllergen() {
		return isAllergen;
	}
	public void setAllergen(boolean isAllergen) {
		this.isAllergen = isAllergen;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(id);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof QuickbooksItemDto) {
			QuickbooksItemDto that = (QuickbooksItemDto)object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}
	
}
