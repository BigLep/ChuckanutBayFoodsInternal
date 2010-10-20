package com.chuckanutbay.businessobjects.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;

/**
 * Simple class for interfacing with Hibernate.
 */
public class HibernateUtil {

	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

	private static final Configuration configuration;
	static {
		configuration = new Configuration()
			.addAnnotatedClass(InventoryItem.class)
			.addAnnotatedClass(InventoryLot.class)
			.configure("hibernate/hibernate-unit-test.cfg.xml");
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
}
