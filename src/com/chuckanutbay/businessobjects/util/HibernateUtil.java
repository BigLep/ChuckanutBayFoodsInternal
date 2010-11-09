package com.chuckanutbay.businessobjects.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static final String CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_UNIT_TEST = "unit-test";

	private static final Configuration configuration;
	static {
		String configFilePath =
			"hibernate/hibernate-" +
			ObjectUtilsExtensions.defaultIfNull(SystemUtilsExtensions.getConfigValue(CONFIG_KEY_HIBERNATE_CONFIG_TYPE), CONFIG_VALUE_HIBERNATE_CONFIG_TYPE_PROD) +
			".cfg.xml";
		logger.info("Configuring Hibernate with configuration: " + configFilePath);
		configuration = new Configuration()
			.addAnnotatedClass(InventoryItem.class)
			.addAnnotatedClass(InventoryLot.class)
			.configure("hibernate/hibernate-prod.cfg.xml");
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

	public static void beginTransaction() {
		getSession().beginTransaction();
	}

	public static void commitTransaction() {
		getSession().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		getSession().getTransaction().rollback();
	}

	public static void closeSession() {
		getSession().close();
	}

	/**
	 * @see EffectiveJava#Enforce_noninstantiability
	 */
	private HibernateUtil() {}

}
