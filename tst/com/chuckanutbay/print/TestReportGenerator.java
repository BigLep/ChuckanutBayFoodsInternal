package com.chuckanutbay.print;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrder;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneTrayLabel;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.BusinessObjects;
import com.chuckanutbay.businessobjects.SalesOrderLineItem;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.util.testing.AssertExtensions;
import com.chuckanutbay.util.testing.DatabaseResource;

public class TestReportGenerator {
	
	/**
	 * @see ReportGenerator
	 */
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	@Test
	public void testGenerateReport() throws JRException {
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(oneSalesOrder("Haggens", false), oneQuickbooksItem("1111-11"), 10);
		TrayLabel tl1 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl2 = oneTrayLabel(5, "4A111A1", soli1);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("TRAY_LABEL_IDS", "1, 2");
		
		AssertExtensions.assertFileExists(ReportGenerator.generateReport(ReportUtil.TRAY_LABEL, parameters));
	}
}
