package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.ICharacterTypeDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.CharacterType;

@Repository
public class CharacterTypeDaoXmlImpl extends AbstractDaoXmlImp<CharacterType, Integer> implements ICharacterTypeDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public CharacterType get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterType> wrapper = (XmlModelWrapper<Integer, CharacterType>) xstream
				.fromXML(file);
		List<CharacterType> entitys = wrapper.getRows();
		for (CharacterType entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterType> wrapper = (XmlModelWrapper<Integer, CharacterType>) xstream
				.fromXML(file);
		List<CharacterType> entitys = wrapper.getRows();
		CharacterType found = null;
		for (CharacterType entity : entitys) {
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
	public List<CharacterType> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterType> wrapper = (XmlModelWrapper<Integer, CharacterType>) xstream
				.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public CharacterType insert(CharacterType entity) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterType> wrapper = (XmlModelWrapper<Integer, CharacterType>) xstream
				.fromXML(file);
		List<CharacterType> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(CharacterType entity) {
		File file = getFile();

		XmlModelWrapper<Integer, CharacterType> wrapper = (XmlModelWrapper<Integer, CharacterType>) xstream
				.fromXML(file);
		List<CharacterType> entitys = wrapper.getRows();
		for (CharacterType entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setName(entity.getName());
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "charactertypes.xml");
		return file;
	}
}
