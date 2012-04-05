package com.chuckanutbay.webapp.dashboard.client;

import java.util.List;

import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	public static AsyncCallback<Void> createPrintReportCallback(final Dashboard caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.successfulPrint();
			}
		};
	}
}
