package ru.mail.ales2003.deals2017.dao.api;

import java.math.BigDecimal;

import ru.mail.ales2003.deals2017.datamodel.Contract;

public interface IContractDao extends GenericDao<Contract, Integer> {

	BigDecimal calculateContractTotalPrice(Integer id);

	void updateContractTotalPrice(Integer contractId, BigDecimal totalPrice);

}
