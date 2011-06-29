package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.chuckanutbay.documentation.Terminology;

/**
 * A Serializable object that stores a barcode number
 * @author mloeppky
 * {@link Terminology#DTO} for {@link Employee}.
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */

public class BarcodeDto implements Serializable {
	public Integer barcodeNumber;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public BarcodeDto(){}
	
	/**
	 * Basic constructor for {@link BarcodeDto} objects
	 * @param barcodeNumber The barcode number
	 */
	public BarcodeDto(Integer barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	
	/*####################################
	 * JavaBean getters/setters 
	 *####################################*/
	public Integer getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(Integer barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	
	/**
	 * Determines if two {@link BarcodeDto}s have the same barcode numbers.
	 * @param barcode The {@link BarcodeDto} to check for equivalency with.
	 * @return Returns true if the two {@link BarcodeDto}s have the same number.
	 */
	public boolean equals(BarcodeDto barcode) {
		return barcodeNumber.equals(barcode.getBarcodeNumber());
	}
}
