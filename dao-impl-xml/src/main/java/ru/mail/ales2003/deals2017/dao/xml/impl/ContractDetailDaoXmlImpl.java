package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IContractDetailDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;
import ru.mail.ales2003.deals2017.dao.xml.impl.exception.NotSupportedMethodException;

@Repository
public class ContractDetailDaoXmlImpl implements IContractDetailDao {

	@Override
	public List<ContractDetail> getDetails(Integer arg0) {
		throw new NotSupportedMethodException();
	}

}
