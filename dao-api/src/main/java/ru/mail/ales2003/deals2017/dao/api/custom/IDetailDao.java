package ru.mail.ales2003.deals2017.dao.api.custom;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.Detail;

public interface IDetailDao {

	List<Detail> getByItemVariant(Integer itemVariantId);
}
