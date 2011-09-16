package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

public class PackagingTransactionDto implements Serializable {
	private Integer Id;
	private TrayLabelDto trayLabelDto;
	private String barcode;
	
	public PackagingTransactionDto() {}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public TrayLabelDto getTrayLabelDto() {
		return trayLabelDto;
	}

	public void setTrayLabelDto(TrayLabelDto trayLabelDto) {
		this.trayLabelDto = trayLabelDto;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}
