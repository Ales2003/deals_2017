package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.IItemVariantDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.ItemVariant;

@Repository
public class ItemVariantDaoXmlImpl extends AbstractDaoXmlImp<ItemVariant, Integer> implements IItemVariantDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public ItemVariant get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariant> wrapper = (XmlModelWrapper<Integer, ItemVariant>) xstream.fromXML(file);
		List<ItemVariant> entitys = wrapper.getRows();
		for (ItemVariant entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariant> wrapper = (XmlModelWrapper<Integer, ItemVariant>) xstream.fromXML(file);
		List<ItemVariant> entitys = wrapper.getRows();
		ItemVariant found = null;
		for (ItemVariant entity : entitys) {
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
	public List<ItemVariant> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariant> wrapper = (XmlModelWrapper<Integer, ItemVariant>) xstream.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public ItemVariant insert(ItemVariant entity) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariant> wrapper = (XmlModelWrapper<Integer, ItemVariant>) xstream.fromXML(file);
		List<ItemVariant> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(ItemVariant entity) {
		File file = getFile();

		XmlModelWrapper<Integer, ItemVariant> wrapper = (XmlModelWrapper<Integer, ItemVariant>) xstream.fromXML(file);
		List<ItemVariant> entitys = wrapper.getRows();
		for (ItemVariant entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setItemId(entity.getItemId());
				entityItem.setVariantPrice(entity.getVariantPrice());

				break;
			}
		}

		writeNewData(file, wrapper);
	}

	private File getFile() {
		File file = new File(rootFolder + "itemvariants.xml");
		return file;
	}
}
