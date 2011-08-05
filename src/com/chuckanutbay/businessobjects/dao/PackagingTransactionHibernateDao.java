package com.chuckanutbay.businessobjects.dao;

import com.chuckanutbay.businessobjects.PackagingTransaction;
import com.chuckanutbay.documentation.Technology;

/**
 * {@link PackagingTransactionDao} that uses {@link Technology#Hibernate}.
 */
public class PackagingTransactionHibernateDao extends GenericHibernateDao<PackagingTransaction,Integer> implements PackagingTransactionDao {

}