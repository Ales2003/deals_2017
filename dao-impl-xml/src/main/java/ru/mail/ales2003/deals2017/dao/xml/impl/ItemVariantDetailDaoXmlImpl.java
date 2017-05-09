package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ru.mail.ales2003.deals2017.dao.api.customdao.IItemVariantDetailDao;
import ru.mail.ales2003.deals2017.dao.api.customentities.ItemVariantDetail;
import ru.mail.ales2003.deals2017.dao.xml.impl.exception.NotSupportedMethodException;

@Repository
public class ItemVariantDetailDaoXmlImpl implements IItemVariantDetailDao {

	@Override
	public List<ItemVariantDetail> getDetails(Integer arg0) {
		throw new NotSupportedMethodException();
	}

}
