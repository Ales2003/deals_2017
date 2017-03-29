package ru.mail.ales2003.deals2017.webapp;

import ru.mail.ales2003.deals2017.datamodel.ContractStatus;

public class EnumsTest {
	public static void main(String[] args) {
		ContractStatus cs = ContractStatus.CONTRACT_PREPARATION;
		System.out.println(cs);
		System.out.println(cs.name());
		String s = "CONTRACT_PREPARATION";
		System.out.println(cs.name() == s);
		System.out.println(
				ContractStatus.CONTRACT_PREPARATION.equals(Enum.valueOf(ContractStatus.class, "CONTRACT_PREPARATION")));

	}
}
