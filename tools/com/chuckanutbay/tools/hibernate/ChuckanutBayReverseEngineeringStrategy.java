package com.chuckanutbay.tools.hibernate;

import java.util.List;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class ChuckanutBayReverseEngineeringStrategy extends
		DelegatingReverseEngineeringStrategy {

	public ChuckanutBayReverseEngineeringStrategy(ReverseEngineeringStrategy delgate) {
		super(delgate);
	}

	@Override
	public String foreignKeyToCollectionName(String keyname,
			TableIdentifier fromTable, List fromColumns,
			TableIdentifier referencedTable, List referencedColumns,
			boolean uniqueReference) {
		// TODO Auto-generated method stub
		return super.foreignKeyToCollectionName(keyname, fromTable, fromColumns,
				referencedTable, referencedColumns, uniqueReference);
	}

	@Override
	public String tableToClassName(TableIdentifier tableIdentifier) {
		String delegateResult = super.tableToClassName(tableIdentifier);
		if (delegateResult.endsWith("s")) {
			return delegateResult.substring(0, delegateResult.length() - 2);
		} else {
			return delegateResult;
		}
	}



}
