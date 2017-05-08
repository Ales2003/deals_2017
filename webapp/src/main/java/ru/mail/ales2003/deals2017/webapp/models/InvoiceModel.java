package ru.mail.ales2003.deals2017.webapp.models;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.customentities.ContractCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.customentities.ContractDetail;

public class InvoiceModel {
	//id of an invoice
		private Integer invoiceNumber;
		
		private ContractCommonInfo commonInfo;
		
		private List <ContractDetail> details;

}
