package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeInItemVariantDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.CharacterTypeInItemVariant;

@Repository
public class CharacterTypeInItemVariantDaoXmlImpl extends AbstractDaoXmlImp<CharacterTypeInItemVariant, Integer>
		implements ICharacterTypeInItemVariantDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public CharacterTypeInItemVariant get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterTypeInItemVariant> wrapper = (XmlModelWrapper<Integer, CharacterTypeInItemVariant>) xstream
				.fromXML(file);
		List<CharacterTypeInItemVariant> entitys = wrapper.getRows();
		for (CharacterTypeInItemVariant entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterTypeInItemVariant> wrapper = (XmlModelWrapper<Integer, CharacterTypeInItemVariant>) xstream
				.fromXML(file);
		List<CharacterTypeInItemVariant> entitys = wrapper.getRows();
		CharacterTypeInItemVariant found = null;
		for (CharacterTypeInItemVariant entity : entitys) {
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
	public List<CharacterTypeInItemVariant> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterTypeInItemVariant> wrapper = (XmlModelWrapper<Integer, CharacterTypeInItemVariant>) xstream
				.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public CharacterTypeInItemVariant insert(CharacterTypeInItemVariant entity) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterTypeInItemVariant> wrapper = (XmlModelWrapper<Integer, CharacterTypeInItemVariant>) xstream
				.fromXML(file);
		List<CharacterTypeInItemVariant> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(CharacterTypeInItemVariant entity) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterTypeInItemVariant> wrapper = (XmlModelWrapper<Integer, CharacterTypeInItemVariant>) xstream
				.fromXML(file);
		List<CharacterTypeInItemVariant> entitys = wrapper.getRows();
		for (CharacterTypeInItemVariant entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setAttribute(entity.getAttribute());
				entityItem.setCharacterTypeId(entity.getCharacterTypeId());
				entityItem.setItemVariantId(entity.getItemVariantId());
				entityItem.setValue(entity.getValue());

				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "charactertypeinitemvariant.xml");
		return file;
	}
}
