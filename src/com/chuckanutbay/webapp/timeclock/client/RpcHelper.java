package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;

import java.util.List;
import java.util.Set;

import com.chuckanutbay.webapp.common.client.EmployeeClockInOutServiceAsync;
import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.lotmanagement.client.LotCodeManagerPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The {@link RpcHelper} class does many of the administrative tasks associated with making RPC calls to the database
 * @author mloeppky
 *
 */
public class RpcHelper {
	private final EmployeeClockInOutServiceAsync employeeClockInOutService;
	
	public RpcHelper() {
		employeeClockInOutService = ServiceUtils.createEmployeeClockInOutService();
	}
	
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
	
	public static AsyncCallback<Set<EmployeeDto>> createGetClockedInEmployeesCallback(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<Set<EmployeeDto>>() {
			@Override
			public void onSuccess(Set<EmployeeDto> employees) {
				caller.onSuccessfulGetClockedInEmployees(employees);
			}
		};
	}
	
	public static AsyncCallback<Set<ActivityDto>> createGetActivities(final ClockInOutServerCommunicator caller) {
		return new DefaultAsyncCallback<Set<ActivityDto>>() {
			@Override
			public void onSuccess(Set<ActivityDto> activities) {
				caller.onSuccessfulGetActivities(activities);
			}
		};
	}
}
