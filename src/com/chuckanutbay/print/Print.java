package com.chuckanutbay.print;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

import com.chuckanutbay.webapp.common.server.Timer;

public class Print {
	
	public static final String HP_WIRELESS_P1102W = "HP Wireless P1102w";
	
	public static void print(String pdfFilePath, String printerName) {
		Timer timer = new Timer(Logger.getLogger(Print.class.getName())).start("Creating print job:");
		DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF; //Printing a PDF "flavored" file
		
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); //Print job Attributes
		aset.add(MediaSizeName.NA_LETTER); //Letter format
		aset.add(new Copies(1)); // 1 Copy
		
		try {
			Doc doc = new SimpleDoc(new FileInputStream(pdfFilePath), flavor, null);
			for (PrintService pservice : PrintServiceLookup.lookupPrintServices(flavor, aset)) {//Find all the printers
				if (pservice.getName().equals(printerName)) {//Find the printer with given name
					pservice.createPrintJob().print(doc, aset); //Print the pdf to that printer
					timer.logTime("Printed:");
					break;
				}
			}
		} catch (PrintException e) { 
			System.err.println(e);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		timer.stop("Done:");
	}
}
