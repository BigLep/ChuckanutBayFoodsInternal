package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import java.util.List;

import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	public static AsyncCallback<List<DamageCodeDto>> createGetDamageCodeDtosCallback(final PackagingTransactionServerCommunicator caller) {
		return new DefaultAsyncCallback<List<DamageCodeDto>>() {
			@Override
			public void onSuccess(List<DamageCodeDto> result) {
				caller.onSuccessfulGetDamageCodeDtos(result);
			}
		};
	}
	
	public static AsyncCallback<List<EmployeeDto>> createGetEmployeeDtosCallback(final PackagingTransactionServerCommunicator caller) {
		return new DefaultAsyncCallback<List<EmployeeDto>>() {
			@Override
			public void onSuccess(List<EmployeeDto> result) {
				caller.onSuccessfulGetEmployeeDtos(result);
			}
		};
	}
	
	public static AsyncCallback<TrayLabelDto> createGetTrayLabelDtoCallback(final PackagingTransactionServerCommunicator caller) {
		return new DefaultAsyncCallback<TrayLabelDto>() {
			@Override
			public void onSuccess(TrayLabelDto result) {
				caller.onSuccessfulGetTrayLabelDto(result);
			}
		};
	}
	
	public static AsyncCallback<Void> createPersistPackagingTransactionDtoCallback(final PackagingTransactionServerCommunicator caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.onSuccessfulPersistPackagingTransactionDto();
			}
		};
	}
}
