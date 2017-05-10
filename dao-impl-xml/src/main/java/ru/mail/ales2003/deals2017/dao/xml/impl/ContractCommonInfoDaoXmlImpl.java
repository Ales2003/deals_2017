package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	@Override
	public Map<String, ArrayList<ContractCommonInfo>> getCACHEToPersistendSave() {
		throw new NotSupportedMethodException();
	}

	@Override
	public void SetPersistentSavedCACHE(Map<String, ArrayList<ContractCommonInfo>> cache) {
		throw new NotSupportedMethodException();
	}

	@Override
	public void clearCACHE() {
		throw new NotSupportedMethodException();
	}
}
