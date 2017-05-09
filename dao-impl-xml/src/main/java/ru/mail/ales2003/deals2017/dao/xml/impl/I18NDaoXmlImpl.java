package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.II18NDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.I18N;

@Repository
public class I18NDaoXmlImpl extends AbstractDaoXmlImp<I18N, Integer> implements II18NDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public I18N get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, I18N> wrapper = (XmlModelWrapper<Integer, I18N>) xstream.fromXML(file);
		List<I18N> entitys = wrapper.getRows();
		for (I18N entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, I18N> wrapper = (XmlModelWrapper<Integer, I18N>) xstream.fromXML(file);
		List<I18N> entitys = wrapper.getRows();
		I18N found = null;
		for (I18N entity : entitys) {
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
	public List<I18N> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, I18N> wrapper = (XmlModelWrapper<Integer, I18N>) xstream.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public I18N insert(I18N entity) {
		File file = getFile();

		XmlModelWrapper<Integer, I18N> wrapper = (XmlModelWrapper<Integer, I18N>) xstream.fromXML(file);
		List<I18N> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(I18N entity) {
		File file = getFile();

		XmlModelWrapper<Integer, I18N> wrapper = (XmlModelWrapper<Integer, I18N>) xstream.fromXML(file);
		List<I18N> entitys = wrapper.getRows();
		for (I18N entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setKeyword(entity.getKeyword());
				entityItem.setLanguage(entity.getLanguage());
				entityItem.setMemberId(entity.getMemberId());
				entityItem.setTableName(entity.getTableName());
				entityItem.setValue(entity.getValue());

				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "i18ns.xml");
		return file;
	}
}
