package com.chuckanutbay.webapp.common.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.google.gwt.user.client.rpc.RemoteService;

public interface PackagingTransactionService extends RemoteService {
	
	/**
	 * 
	 * @return All of the {@link DamgeCodeDto}s from the database.
	 */
	public List<DamageCodeDto> getDamageCodeDtos();
	
	/**
	 * 
	 * @return All of the {@link EmployeeDto}s from the database.
	 */
	public List<EmployeeDto> getEmployeeDtos();
	
	/**
	 * Persists the given {@link PackagingTransactionDto} to the database.
	 * @param packagingTransaction
	 */
	public void persistPackagingTransactionDto(PackagingTransactionDto packagingTransaction);
}
