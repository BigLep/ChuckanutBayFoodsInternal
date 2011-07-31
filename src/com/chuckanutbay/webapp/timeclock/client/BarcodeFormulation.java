package com.chuckanutbay.webapp.timeclock.client;

import com.google.gwt.core.client.GWT;

/**
 * Formulates a barcode number one character at a type. Maximum barcode length is 9 digits.
 *
 */
public class BarcodeFormulation {
	private byte[] barcodeNumberBeingFormulated;
	
	/**
	 * Adds the supplied character to the end of the barcode.
	 * @param characterCode The code of the character to be added.
	 */
	public void addCharacter(byte characterCode) {
		if (characterCode < 100 && characterCode >= 0) {
			
			//If it is the first byte then start a new array
			if (barcodeNumberBeingFormulated == null) {
				barcodeNumberBeingFormulated = new byte[1];
				barcodeNumberBeingFormulated[0] = characterCode;
			
			//Otherwise create a new array like the original but 1 byte larger and add the new bytes
			} else if (barcodeNumberBeingFormulated.length < 9) {
				byte[] oldArray = barcodeNumberBeingFormulated;
				barcodeNumberBeingFormulated = new byte[oldArray.length + 1];
				for(int i=0; i < oldArray.length; i++) {
					barcodeNumberBeingFormulated[i] = oldArray[i];
				}
				barcodeNumberBeingFormulated[barcodeNumberBeingFormulated.length-1] = characterCode;
			}
		}
	}
	
	/**
	 * Returns the barcode that has been generated as an BarcodeDto.
	 * @return The barcode as an BarcodeDto.
	 */
	public Integer getBarcode() {
		String formulatedBarcodeString = new String(barcodeNumberBeingFormulated);
		GWT.log("Barcode is: " + formulatedBarcodeString.length() + " characters long");
		GWT.log(formulatedBarcodeString);
		return new Integer(formulatedBarcodeString);
	}
}
