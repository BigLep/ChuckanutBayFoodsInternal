package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * A Serializable object that stores a barcode number
 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
 */

public class Barcode implements Serializable {
	public Integer barcodeNumber;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public Barcode(){}
	
	/**
	 * Basic constructor for {@link Barcode} objects
	 * @param barcodeNumber The barcode number
	 */
	public Barcode(Integer barcodeNumber) {
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
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), barcodeNumber);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof Barcode) {
			Barcode that = (Barcode)object;
			return Objects.equal(this.barcodeNumber, that.barcodeNumber);
		}
		return false;
	}
}
