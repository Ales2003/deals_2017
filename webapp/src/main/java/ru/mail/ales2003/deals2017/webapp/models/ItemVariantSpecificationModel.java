package ru.mail.ales2003.deals2017.webapp.models;

import java.util.List;

public class ItemVariantSpecificationModel {
	private ItemVariantCommonInfoModel basicInfoModel;
	//private ItemVariantDetailModel [] detailModel;
	private List <ItemVariantDetailModel> detailModel;
	
	/**
	 * @return the basicInfoModel
	 */
	public ItemVariantCommonInfoModel getBasicInfoModel() {
		return basicInfoModel;
	}
	/**
	 * @param basicInfoModel the basicInfoModel to set
	 */
	public void setBasicInfoModel(ItemVariantCommonInfoModel basicInfoModel) {
		this.basicInfoModel = basicInfoModel;
	}
	/**
	 * @return the detailModel
	 */
	public List<ItemVariantDetailModel> getDetailModel() {
		return detailModel;
	}
	/**
	 * @param detailModel the detailModel to set
	 */
	public void setDetailModel(List<ItemVariantDetailModel> detailModel) {
		this.detailModel = detailModel;
	}

/*	*//**
	 * @return the detailModel
	 *//*
	public ItemVariantDetailModel[] getDetailModel() {
		return detailModel;
	}
	*//**
	 * @param detailModel the detailModel to set
	 *//*
	public void setDetailModel(ItemVariantDetailModel[] detailModel) {
		this.detailModel = detailModel;
	}*/
	
	
	

	

}
