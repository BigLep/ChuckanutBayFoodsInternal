package com.chuckanutbay.util;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class DependenciesTest {
	
	@Test
	public void testDependencies() {
		Logger logger = LoggerFactory.getLogger(DependenciesTest.class);
		logger.info("Testing logging.");
		
		List<String> list = Lists.newArrayList();
		list.add("Testing google collections.");
		
		DateTime dateTime = new DateTime();
		logger.info(dateTime.toString());
	}

}
