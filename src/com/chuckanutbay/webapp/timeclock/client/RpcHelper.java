package com.chuckanutbay.webapp.timeclock.client;

import java.util.SortedSet;

import com.chuckanutbay.webapp.common.client.EmployeeClockInOutServiceAsync;
import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The {@link RpcHelper} class does many of the administrative tasks associated with making RPC calls to the database.
 *
 */
public class RpcHelper {
	public static final EmployeeClockInOutServiceAsync employeeClockInOutService = ServiceUtils.createEmployeeClockInOutService();
	
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
}
