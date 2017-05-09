package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.ICustomerDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.Customer;

@Repository
public class CustomerDaoXmlImpl extends AbstractDaoXmlImp<Customer, Integer> implements ICustomerDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Customer get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Customer> wrapper = (XmlModelWrapper<Integer, Customer>) xstream.fromXML(file);
		List<Customer> entitys = wrapper.getRows();
		for (Customer entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Customer> wrapper = (XmlModelWrapper<Integer, Customer>) xstream.fromXML(file);
		List<Customer> entitys = wrapper.getRows();
		Customer found = null;
		for (Customer entity : entitys) {
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
	public List<Customer> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, Customer> wrapper = (XmlModelWrapper<Integer, Customer>) xstream.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public Customer insert(Customer entity) {
		File file = getFile();

		XmlModelWrapper<Integer, Customer> wrapper = (XmlModelWrapper<Integer, Customer>) xstream.fromXML(file);
		List<Customer> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(Customer entity) {
		File file = getFile();

		XmlModelWrapper<Integer, Customer> wrapper = (XmlModelWrapper<Integer, Customer>) xstream.fromXML(file);
		List<Customer> entitys = wrapper.getRows();
		for (Customer entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setAddress(entity.getAddress());
				entityItem.setCompanyName(entity.getCompanyName());
				entityItem.setCustomerGroupId(entity.getCustomerGroupId());
				entityItem.setFirstName(entity.getFirstName());
				entityItem.setLastName(entity.getLastName());
				entityItem.setManagerId(entity.getManagerId());
				entityItem.setPatronymic(entity.getPatronymic());
				entityItem.setPhoneNumber(entity.getPhoneNumber());
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	private File getFile() {
		File file = new File(rootFolder + "customers.xml");
		return file;
	}

}
