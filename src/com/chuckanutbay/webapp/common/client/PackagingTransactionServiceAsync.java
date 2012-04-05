package com.chuckanutbay.webapp.common.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PackagingTransactionServiceAsync {

	void getDamageCodeDtos(AsyncCallback<List<DamageCodeDto>> callback);

	void getEmployeeDtos(AsyncCallback<List<EmployeeDto>> callback);

	void persistPackagingTransactionDto(
			PackagingTransactionDto packagingTransaction,
			AsyncCallback<Void> callback);
	
}
