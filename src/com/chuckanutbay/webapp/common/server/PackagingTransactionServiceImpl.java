package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.webapp.common.server.DtoUtils.fromPackagingTransactionDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toDamageCodeDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toEmployeeDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.transform;

import java.util.List;

import com.chuckanutbay.businessobjects.dao.DamageCodeHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;
import com.chuckanutbay.businessobjects.dao.PackagingTransactionHibernateDao;
import com.chuckanutbay.webapp.common.client.PackagingTransactionService;
import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PackagingTransactionServiceImpl extends RemoteServiceServlet implements PackagingTransactionService {

	@Override
	public List<DamageCodeDto> getDamageCodeDtos() {
		return transform(new DamageCodeHibernateDao().findAll(), toDamageCodeDtoFunction);
	}

	@Override
	public List<EmployeeDto> getEmployeeDtos() {
		return transform(new EmployeeHibernateDao().findAll(), toEmployeeDtoFunction);
	}

	@Override
	public void persistPackagingTransactionDto(PackagingTransactionDto ptDto) {
		new PackagingTransactionHibernateDao().makePersistent(fromPackagingTransactionDtoFunction.apply(ptDto));
	}

}
