package com.chuckanutbay.webapp.common.client;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

/**
 * Formulates a barcode number one character at a type. Maximum barcode length is 9 digits.
 *
 */
public class BarcodeFormulation {
	public static final int ENTER_KEY_CODE = 13;
	private byte[] barcodeNumberBeingFormulated;
	private List<ScanHandler> scanHandlers = newArrayList();
	/**
	 * Adds the supplied character to the end of the barcode.
	 * @param characterCode The code of the character to be added.
	 */
	public void addCharacter(int nativeKeyCode) {
		if (nativeKeyCode == 13 && barcodeNumberBeingFormulated != null) {//It is the enter key so call the scanHandlers!
			for (ScanHandler sh : scanHandlers) {
				sh.onScan(new String(barcodeNumberBeingFormulated));
			}
			barcodeNumberBeingFormulated = null;
		} else {
			byte characterCode = new Integer(nativeKeyCode).byteValue();
			if (characterCode < 100 && characterCode >= 0) {
				
				//If it is the first byte then start a new array
				if (barcodeNumberBeingFormulated == null) {
					barcodeNumberBeingFormulated = new byte[1];
					barcodeNumberBeingFormulated[0] = characterCode;
				
				//Otherwise create a new array like the original but 1 byte larger and add the new bytes
				} else {
					byte[] oldArray = barcodeNumberBeingFormulated;
					barcodeNumberBeingFormulated = new byte[oldArray.length + 1];
					for(int i=0; i < oldArray.length; i++) {
						barcodeNumberBeingFormulated[i] = oldArray[i];
					}
					barcodeNumberBeingFormulated[barcodeNumberBeingFormulated.length-1] = characterCode;
				}
			}
		}
	}
	
	/**
	 * 
	 * @return The barcode that has been generated as an String.
	 */
	public String getBarcode() {
		return new String(barcodeNumberBeingFormulated);
	}
	
	public BarcodeFormulation addScanHandler(ScanHandler scanHandler) {
		scanHandlers.add(scanHandler);
		return this;
	}
}
