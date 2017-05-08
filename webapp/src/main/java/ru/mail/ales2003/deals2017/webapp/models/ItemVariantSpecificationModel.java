package ru.mail.ales2003.deals2017.webapp.models;

import java.util.List;

public class ItemVariantSpecificationModel {

	private Integer itemVariantId;

	private ItemVariantCommonInfoModel commonInfoModel;
	// private ItemVariantDetailModel [] detailModel;
	private List<ItemVariantDetailModel> detailModel;

	public Integer getItemVariantId() {
		return itemVariantId;
	}

	public void setItemVariantId(Integer itemVariantId) {
		this.itemVariantId = itemVariantId;
	}

	/**
	 * @return the basicInfoModel
	 */
	public ItemVariantCommonInfoModel getCommonInfoModel() {
		return commonInfoModel;
	}

	/**
	 * @param basicInfoModel
	 *            the basicInfoModel to set
	 */
	public void setCommonInfoModel(ItemVariantCommonInfoModel commonInfoModel) {
		this.commonInfoModel = commonInfoModel;
	}

	/**
	 * @return the detailModel
	 */
	public List<ItemVariantDetailModel> getDetailModel() {
		return detailModel;
	}

	/**
	 * @param detailModel
	 *            the detailModel to set
	 */
	public void setDetailModel(List<ItemVariantDetailModel> detailModel) {
		this.detailModel = detailModel;
	}

	@Override
	public String toString() {
		return "ItemVariantSpecificationModel [itemVariantId=" + itemVariantId + ", commonInfoModel=" + commonInfoModel
				+ ", detailModel=" + detailModel + "]";
	}

}
