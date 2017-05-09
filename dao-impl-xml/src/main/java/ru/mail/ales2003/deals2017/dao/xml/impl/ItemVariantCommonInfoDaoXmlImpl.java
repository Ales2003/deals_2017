package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantCommonInfoDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantCommonInfo;
import ru.mail.ales2003.deals2017.dao.api.filters.IItemVariantFilter;
import ru.mail.ales2003.deals2017.dao.xml.impl.exception.NotSupportedMethodException;

@Repository
public class ItemVariantCommonInfoDaoXmlImpl implements IItemVariantCommonInfoDao {

	@Override
	public ItemVariantCommonInfo getCommonInfo(Integer arg0) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<ItemVariantCommonInfo> getCommonInfoFiltered(IItemVariantFilter arg0) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<ItemVariantCommonInfo> getCommonInfoForAll() {
		throw new NotSupportedMethodException();
	}

}
