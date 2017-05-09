package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IContractCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IContractFilter;
import ru.mail.ales2003.deals2017.dao.xml.impl.exception.NotSupportedMethodException;

@Repository
public class ContractCommonInfoDaoXmlImpl implements IContractCommonInfoDao {

	@Override
	public ContractCommonInfo getCommonInfo(Integer arg0) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoFiltered(IContractFilter arg0) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<ContractCommonInfo> getCommonInfoForAll() {
		throw new NotSupportedMethodException();
	}

}
