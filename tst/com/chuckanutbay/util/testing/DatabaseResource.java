package com.chuckanutbay.util.testing;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.rules.ExternalResource;

import com.chuckanutbay.businessobjects.util.HibernateUtil;

/**
 * Simple {@link ExternalResource} that calls {@link SchemaExport#create(boolean, boolean)} and {@link SchemaExport#drop(boolean, boolean)} for each test.
 * This ensures one has a clean database for each test.
 */
public class DatabaseResource extends ExternalResource {

	@Override
	protected void before() throws Throwable {
		System.setProperty(HibernateUtil.CONFIG_KEY_HIBERNATE_CONFIG_TYPE, HibernateUtil.CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_UNIT_TEST);
		SchemaExport schemaExport = new SchemaExport(HibernateUtil.getConfiguration());
		schemaExport.create(true, true);
		HibernateUtil.beginTransaction();
	}

	@Override
	protected void after() {
		HibernateUtil.closeSession();
		SchemaExport schemaExport = new SchemaExport(HibernateUtil.getConfiguration());
		schemaExport.drop(true, true);
	}
}
