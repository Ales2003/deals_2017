package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.ICustomerGroupDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.CustomerGroup;

@Repository
public class CustomerGroupDaoXmlImpl extends AbstractDaoXmlImp<CustomerGroup, Integer> implements ICustomerGroupDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public CustomerGroup get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, CustomerGroup> wrapper = (XmlModelWrapper<Integer, CustomerGroup>) xstream
				.fromXML(file);
		List<CustomerGroup> entitys = wrapper.getRows();
		for (CustomerGroup entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, CustomerGroup> wrapper = (XmlModelWrapper<Integer, CustomerGroup>) xstream
				.fromXML(file);
		List<CustomerGroup> entitys = wrapper.getRows();
		CustomerGroup found = null;
		for (CustomerGroup entity : entitys) {
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
	public List<CustomerGroup> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, CustomerGroup> wrapper = (XmlModelWrapper<Integer, CustomerGroup>) xstream
				.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public CustomerGroup insert(CustomerGroup entity) {
		File file = getFile();

		XmlModelWrapper<Integer, CustomerGroup> wrapper = (XmlModelWrapper<Integer, CustomerGroup>) xstream
				.fromXML(file);
		List<CustomerGroup> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(CustomerGroup entity) {
		File file = getFile();

		XmlModelWrapper<Integer, CustomerGroup> wrapper = (XmlModelWrapper<Integer, CustomerGroup>) xstream
				.fromXML(file);
		List<CustomerGroup> entitys = wrapper.getRows();
		for (CustomerGroup entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setName(entity.getName());
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "customergroups.xml");
		return file;
	}

}
