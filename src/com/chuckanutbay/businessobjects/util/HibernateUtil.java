package com.chuckanutbay.businessobjects.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.ReferenceSource.EffectiveJava;
import com.chuckanutbay.util.ObjectUtilsExtensions;
import com.chuckanutbay.util.SystemUtilsExtensions;

/**
 * Simple class for interfacing with Hibernate.
 */
public class HibernateUtil {

	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

	/**
	 * Defines the name of the system property/environment variable that should be defined to set which Hibernate configuration file to use.
	 */
	public static final String CONFIG_KEY_HIBERNATE_CONFIG_TYPE = "hibernateConfigType";
	private static final String CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_PROD = "prod";
	private static final String CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_LOCALHOST = "localhost";
	public static final String CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_UNIT_TEST = "unit-test";

	private static final Configuration configuration;
	static {
		String configFilePath =
			"hibernate/hibernate-" +
			ObjectUtilsExtensions.defaultIfNull(SystemUtilsExtensions.getConfigValue(CONFIG_KEY_HIBERNATE_CONFIG_TYPE), CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_LOCALHOST) +
			".cfg.xml";
		logger.info("Configuring Hibernate with configuration: " + configFilePath);
		configuration = new Configuration()
			.addAnnotatedClass(Employee.class)
			.addAnnotatedClass(EmployeeWorkInterval.class)
			.addAnnotatedClass(InventoryItem.class)
			.addAnnotatedClass(InventoryLot.class)
			.configure(configFilePath);
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	private static final SessionFactory sessionFactory;
	static {
		try {
			sessionFactory = configuration.buildSessionFactory();
		} catch (HibernateException e) {
			logger.error("Failed to initialize", e);
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @return Current {@link Session} for the {@link #sessionFactory}.
	 */
	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Begins a {@link Transaction} for the current {@link Session}.
	 */
	public static void beginTransaction() {
		getSession().beginTransaction();
	}

	/**
	 * Commits the {@link Transaction} of the current {@link Session}.
	 */
	public static void commitTransaction() {
		getSession().getTransaction().commit();
	}

	/**
	 * Rolls back the current {@link Transaction} of the current {@link Session}.
	 */
	public static void rollbackTransaction() {
		getSession().getTransaction().rollback();
	}

	/**
	 * Closes the current {@link Session} after first committing any open transactions.
	 * If the transaction doesn't get closed, then you will get table locks due to the open transactions.
	 */
	public static void closeSession() {
		if (getSession().getTransaction().isActive()) {
			try {
				commitTransaction();
			} catch (HibernateException e) {
				rollbackTransaction();
			}
		}
		getSession().close();
	}

	/**
	 * @see EffectiveJava#Enforce_noninstantiability
	 */
	private HibernateUtil() {}

}
