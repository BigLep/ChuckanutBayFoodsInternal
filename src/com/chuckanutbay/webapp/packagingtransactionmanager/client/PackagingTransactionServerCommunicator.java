package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface PackagingTransactionServerCommunicator {
	
	void getDamageCodeDtosFromDatabase();
	
	void getEmployeeDtosFromDatabase();
	
	void getTrayLabelDto(Integer id);
	
	void persistPackagingTransactionDtoToDatabase(PackagingTransactionDto packagingTransaction);
	
	void onSuccessfulGetDamageCodeDtos(List<DamageCodeDto> damageCodes);
	
	void onSuccessfulGetEmployeeDtos(List<EmployeeDto> employees);

	void onSuccessfulGetTrayLabelDto(TrayLabelDto result);
	
	void onSuccessfulPersistPackagingTransactionDto();
}
