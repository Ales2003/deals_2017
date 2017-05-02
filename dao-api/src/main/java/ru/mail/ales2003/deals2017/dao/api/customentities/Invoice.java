package ru.mail.ales2003.deals2017.dao.api.customentities;

import java.util.List;

public class Invoice {

	//id of an invoice
	private Integer invoiceNumber;
	
	private ContractCommonInfo commonInfo;
	
	private List <ContractDetail> details;

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public ContractCommonInfo getCommonInfo() {
		return commonInfo;
	}

	public void setCommonInfo(ContractCommonInfo commonInfo) {
		this.commonInfo = commonInfo;
	}

	public List<ContractDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ContractDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceNumber=" + invoiceNumber + ", commonInfo=" + commonInfo + ", details=" + details + "]";
	}
	
	
}
