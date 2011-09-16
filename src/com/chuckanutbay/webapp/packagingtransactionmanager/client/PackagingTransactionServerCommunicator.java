package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface PackagingTransactionServerCommunicator {
	
	void getTrayLabelDtoFromServer(Integer id);
	
	void persistPackagingTransactionDtoToServer(PackagingTransactionDto ptDto);
	
	void onSuccessfulGetTrayLabelDto(TrayLabelDto trayLabelDto);
	
	void onSuccessfulPersistPackaginTransactionDto();
	
}
