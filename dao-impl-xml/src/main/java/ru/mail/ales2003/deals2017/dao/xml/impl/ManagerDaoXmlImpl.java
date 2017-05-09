package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.IManagerDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.Manager;

@Repository
public class ManagerDaoXmlImpl extends AbstractDaoXmlImp<Manager, Integer> implements IManagerDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Manager get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Manager> wrapper = (XmlModelWrapper<Integer, Manager>) xstream.fromXML(file);
		List<Manager> entitys = wrapper.getRows();
		for (Manager entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Manager> wrapper = (XmlModelWrapper<Integer, Manager>) xstream.fromXML(file);
		List<Manager> entitys = wrapper.getRows();
		Manager found = null;
		for (Manager entity : entitys) {
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
	public List<Manager> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, Manager> wrapper = (XmlModelWrapper<Integer, Manager>) xstream.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public Manager insert(Manager entity) {
		File file = getFile();

		XmlModelWrapper<Integer, Manager> wrapper = (XmlModelWrapper<Integer, Manager>) xstream.fromXML(file);
		List<Manager> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(Manager entity) {
		File file = getFile();

		XmlModelWrapper<Integer, Manager> wrapper = (XmlModelWrapper<Integer, Manager>) xstream.fromXML(file);
		List<Manager> entitys = wrapper.getRows();
		for (Manager entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setFirstName(entity.getFirstName());
				entityItem.setPatronymic(entity.getPatronymic());
				entityItem.setLastName(entity.getLastName());
				entityItem.setPosition(entity.getPosition());
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "managers.xml");
		return file;
	}

}
