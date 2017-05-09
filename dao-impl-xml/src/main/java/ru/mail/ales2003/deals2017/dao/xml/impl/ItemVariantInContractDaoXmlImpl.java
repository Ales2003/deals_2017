package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.IItemVariantInContractDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.ItemVariantInContract;

@Repository
public class ItemVariantInContractDaoXmlImpl extends AbstractDaoXmlImp<ItemVariantInContract, Integer>
		implements IItemVariantInContractDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public ItemVariantInContract get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariantInContract> wrapper = (XmlModelWrapper<Integer, ItemVariantInContract>) xstream
				.fromXML(file);
		List<ItemVariantInContract> entitys = wrapper.getRows();
		for (ItemVariantInContract entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariantInContract> wrapper = (XmlModelWrapper<Integer, ItemVariantInContract>) xstream
				.fromXML(file);
		List<ItemVariantInContract> entitys = wrapper.getRows();
		ItemVariantInContract found = null;
		for (ItemVariantInContract entity : entitys) {
			if (entity.getId().equals(id)) {
				found = entity;
				break;
			}
		}
		if (found != null) {
			entitys.remove(found);
			writeNewData(file, wrapper);
		}

	}

	@Override
	public List<ItemVariantInContract> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariantInContract> wrapper = (XmlModelWrapper<Integer, ItemVariantInContract>) xstream
				.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public ItemVariantInContract insert(ItemVariantInContract entity) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariantInContract> wrapper = (XmlModelWrapper<Integer, ItemVariantInContract>) xstream
				.fromXML(file);
		List<ItemVariantInContract> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(ItemVariantInContract entity) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariantInContract> wrapper = (XmlModelWrapper<Integer, ItemVariantInContract>) xstream
				.fromXML(file);
		List<ItemVariantInContract> entitys = wrapper.getRows();
		for (ItemVariantInContract entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {

				entityItem.setContractId(entity.getContractId());
				entityItem.setItemVariantId(entity.getItemVariantId());
				entityItem.setQuantity(entity.getQuantity());

				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "itemvariantincontract.xml");
		return file;
	}
}
