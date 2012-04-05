package com.chuckanutbay.businessobjects.util;

import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Utility for running {@link SchemaExport} to create the necessary tables locally.
 */
public class DatabaseBootstrapper {

	public static void main(String[] args) {
		System.setProperty(HibernateUtil.CONFIG_KEY_HIBERNATE_CONFIG_TYPE, HibernateUtil.CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_LOCALHOST);
		SchemaExport schemaExport = new SchemaExport(HibernateUtil.getConfiguration());
		schemaExport.create(true, true);
	}
	
}
