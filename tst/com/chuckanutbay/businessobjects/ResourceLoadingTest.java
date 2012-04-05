package com.chuckanutbay.businessobjects;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ResourceLoadingTest {
	
	@Test
	public void testGetReports() {
		assertNotNull(getClass().getClassLoader().getResourceAsStream("reports/trayLabel.jasper"));
	}

}