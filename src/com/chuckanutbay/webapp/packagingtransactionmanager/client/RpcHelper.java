package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	public static AsyncCallback<TrayLabelDto> createGetTrayLabelDtoCallback(final PackagingTransactionServerCommunicator caller) {
		return new DefaultAsyncCallback<TrayLabelDto>() {
			@Override
			public void onSuccess(TrayLabelDto trayLabelDto) {
				caller.onSuccessfulGetTrayLabelDto(trayLabelDto);
			}
		};
	}
	
	public static AsyncCallback<Void> createPersistPackagingTransactionDtoCallback(final PackagingTransactionServerCommunicator caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.onSuccessfulPersistPackaginTransactionDto();
			}
		};
	}
}
