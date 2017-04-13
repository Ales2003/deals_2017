package ru.mail.ales2003.deals2017.dao.api.custom;

import ru.mail.ales2003.deals2017.dao.api.custom.entities.ItemVariantSpecification;

public interface IItemVariantSpecificationDao {

	ItemVariantSpecification getByItemVariant(Integer itemVariantId);

}
