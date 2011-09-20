package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;

public interface PackagingTransactionServerCommunicator {
	
	void getDamageCodeDtosFromDatabase();
	
	void getEmployeeDtosFromDatabase();
	
	void persistPackagingTransactionDtoToDatabase(PackagingTransactionDto packagingTransaction);
	
	void onSuccessfulGetDamageCodeDtos(List<DamageCodeDto> damageCodes);
	
	void onSuccessfulGetEmployeeDtos(List<EmployeeDto> employees);
	
	void onSuccessfulPersistPackagingTransactionDto();
}
