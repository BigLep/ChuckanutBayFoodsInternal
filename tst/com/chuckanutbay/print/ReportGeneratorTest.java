package com.chuckanutbay.print;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrder;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneTrayLabel;
import static com.chuckanutbay.util.testing.AssertExtensions.assertFileExists;
import static com.chuckanutbay.webapp.common.shared.ReportDto.TRAY_LABEL;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.BusinessObjects;
import com.chuckanutbay.businessobjects.SalesOrderLineItem;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.reportgeneration.ReportGenerator;
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.shared.ReportDto;

public class ReportGeneratorTest {
	
	/**
	 * @see ReportGenerator
	 */
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	@Test
	public void testGenerateReport() {
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(oneSalesOrder("Haggens", false), oneQuickbooksItem("1111-11"), 10);
		TrayLabel tl1 = oneTrayLabel(5, 1, 1, "4A111A1", soli1);
		TrayLabel tl2 = oneTrayLabel(5, 1, 1, "4A111A1", soli1);
		
		Map<String, Object> parameters = newHashMap();
		parameters.put("TRAY_LABEL_IDS", "1, 2");
		
		HibernateUtil.beginTransaction();
		assertFileExists(ReportGenerator.generateReport(new ReportDto().setName(TRAY_LABEL)));
		HibernateUtil.commitTransaction();
	}
}
