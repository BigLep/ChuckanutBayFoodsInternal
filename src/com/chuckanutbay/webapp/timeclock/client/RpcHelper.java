package com.chuckanutbay.webapp.timeclock.client;

import java.util.List;
import java.util.SortedSet;

import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.client.TimeClockServiceAsync;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.IntervalDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The {@link RpcHelper} class does many of the administrative tasks associated with making RPC calls to the database.
 *
 */
public class RpcHelper {
	public static final TimeClockServiceAsync timeClockService = ServiceUtils.createTimeClockService();
	
	public static AsyncCallback<EmployeeDto> createClockInCallback(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<EmployeeDto>() {
			@Override
			public void onSuccess(EmployeeDto employee) {
				caller.onSuccessfulClockIn(employee);
			}
		};
	}
	
	public static AsyncCallback<Void> createClockOutCallback(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.onSuccessfulClockOut();
			}
		};
	}
	
	public static AsyncCallback<Void> createCancelClockInCallback(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.onSuccessfulCancelClockIn();
			}
		};
	}
	
	public static AsyncCallback<SortedSet<EmployeeDto>> createGetClockedInEmployeesCallback(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<SortedSet<EmployeeDto>>() {
			@Override
			public void onSuccess(SortedSet<EmployeeDto> employees) {
				caller.onSuccessfulGetClockedInEmployees(employees);
			}
		};
	}
	
	public static AsyncCallback<SortedSet<ActivityDto>> createGetActivitiesCallback(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<SortedSet<ActivityDto>>() {
			@Override
			public void onSuccess(SortedSet<ActivityDto> activities) {
				caller.onSuccessfulGetActivities(activities);
			}
		};
	}
	
	public static AsyncCallback<IntervalDto> createGetLastPayPeriodIntervalCallback(final TimeClockReportHandler caller) {
		return new DefaultAsyncCallback<IntervalDto>() {
			@Override
			public void onSuccess(IntervalDto interval) {
				caller.onSuccessfulGetLastPayPeriodInterval(interval);
			}
		};
	}
	
	public static AsyncCallback<List<PayPeriodReportData>> createGetPayPeriodReportDataCallback(final TimeClockReportHandler caller) {
		return new DefaultAsyncCallback<List<PayPeriodReportData>>() {
			@Override
			public void onSuccess(List<PayPeriodReportData> reportData) {
				caller.onSuccessfulGetPayPeriodReportData(reportData);
			}
		};
	}
}
