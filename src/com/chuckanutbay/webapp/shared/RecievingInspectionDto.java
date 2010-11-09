package com.chuckanutbay.webapp.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.documentation.Terminology;

/**
 * {@link Terminology#DTO} for {@link InventoryItem}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */
public class RecievingInspectionDto implements Serializable {
	private Date date;
	private String inspector;
	private String quickbooksPO;
	private String truckingCompany;
	private String truckCondition;
	private Boolean trailorLocked;
	private Boolean allergens;
	private Boolean glass;
	private Boolean goodCondition;
	private Boolean lotCodesEntered;
	private String item1;
	private List<String> productList;
	private List<Float> temperatureList;
	private List<Boolean> acceptableList;
	private String additionalNotes;
	 
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getTruckingCompany() {
		return truckingCompany;
	}
	public void setTruckingCompany(String truckingCompany) {
		this.truckingCompany = truckingCompany;
	}
	public String getTruckCondition() {
		return truckCondition;
	}
	public void setTruckCondition(String truckCondition) {
		this.truckCondition = truckCondition;
	}
	public Boolean getTrailorLocked() {
		return trailorLocked;
	}
	public void setTrailorLocked(Boolean trailorLocked) {
		this.trailorLocked = trailorLocked;
	}
	public Boolean getAllergens() {
		return allergens;
	}
	public void setAllergens(Boolean allergens) {
		this.allergens = allergens;
	}
	public Boolean getGlass() {
		return glass;
	}
	public void setGlass(Boolean glass) {
		this.glass = glass;
	}
	public Boolean getGoodCondition() {
		return goodCondition;
	}
	public void setGoodCondition(Boolean goodCondition) {
		this.goodCondition = goodCondition;
	}
	public Boolean getLotCodesEntered() {
		return lotCodesEntered;
	}
	public void setLotCodesEntered(Boolean lotCodesEntered) {
		this.lotCodesEntered = lotCodesEntered;
	}
	public String getItem1() {
		return item1;
	}
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	public List<String> getProductList() {
		return productList;
	}
	public void setProductList(List<String> productList) {
		this.productList = productList;
	}
	public List<Float> getTemperatureList() {
		return temperatureList;
	}
	public void setTemperatureList(List<Float> temperatureList) {
		this.temperatureList = temperatureList;
	}
	public List<Boolean> getAcceptableList() {
		return acceptableList;
	}
	public void setAcceptableList(List<Boolean> acceptableList) {
		this.acceptableList = acceptableList;
	}
	public String getAdditionalNotes() {
		return additionalNotes;
	}
	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}
	public void setQuickbooksPO(String quickbooksPO) {
		this.quickbooksPO = quickbooksPO;
	}
	public String getQuickbooksPO() {
		return quickbooksPO;
	}
	

	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 * */
	
}
