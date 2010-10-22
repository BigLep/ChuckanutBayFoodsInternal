package com.chuckanutbay.LotCodeManager.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class QBItem implements IsSerializable {
	private String ingredientName;
	private String ingredientCode;
	
	/**
	public QBItem(String argIngredientName, String argIngredientCode) {
		ingredientName = argIngredientName;
		ingredientCode = argIngredientCode;
	}
	*/
	public QBItem(){
		setIngredientName("");
		setIngredientCode("");
	}
	
	public void setIngredientName(String argIngredientName) {
		ingredientName = argIngredientName;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientCode(String argIngredientCode) {
		ingredientCode = argIngredientCode;
	}
	public String getIngredientCode() {
		return ingredientCode;
	}

}
