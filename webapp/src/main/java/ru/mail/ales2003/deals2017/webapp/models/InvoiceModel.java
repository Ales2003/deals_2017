package ru.mail.ales2003.deals2017.webapp.models;

import java.util.List;

public class InvoiceModel {
	// id of an invoice
	private Integer invoiceNumber;

	private ContractCommonInfoModel commonInfo;

	private List<ContractDetailModel> details;

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public ContractCommonInfoModel getCommonInfo() {
		return commonInfo;
	}

	public void setCommonInfo(ContractCommonInfoModel commonInfo) {
		this.commonInfo = commonInfo;
	}

	public List<ContractDetailModel> getDetails() {
		return details;
	}

	public void setDetails(List<ContractDetailModel> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "InvoiceModel [invoiceNumber=" + invoiceNumber + ", commonInfo=" + commonInfo + ", details=" + details
				+ "]";
	}

}
