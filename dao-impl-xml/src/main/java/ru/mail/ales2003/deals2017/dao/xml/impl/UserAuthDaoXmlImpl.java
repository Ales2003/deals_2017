package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.IUserAuthDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.exception.NotSupportedMethodException;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.UserAuth;

@Repository
public class UserAuthDaoXmlImpl extends AbstractDaoXmlImp<UserAuth, Integer> implements IUserAuthDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public UserAuth get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, UserAuth> wrapper = (XmlModelWrapper<Integer, UserAuth>) xstream.fromXML(file);
		List<UserAuth> entitys = wrapper.getRows();
		for (UserAuth entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, UserAuth> wrapper = (XmlModelWrapper<Integer, UserAuth>) xstream.fromXML(file);
		List<UserAuth> entitys = wrapper.getRows();
		UserAuth found = null;
		for (UserAuth entity : entitys) {
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
	public List<UserAuth> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, UserAuth> wrapper = (XmlModelWrapper<Integer, UserAuth>) xstream.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public UserAuth insert(UserAuth entity) {
		File file = getFile();

		XmlModelWrapper<Integer, UserAuth> wrapper = (XmlModelWrapper<Integer, UserAuth>) xstream.fromXML(file);
		List<UserAuth> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(UserAuth entity) {
		File file = getFile();

		XmlModelWrapper<Integer, UserAuth> wrapper = (XmlModelWrapper<Integer, UserAuth>) xstream.fromXML(file);
		List<UserAuth> entitys = wrapper.getRows();
		for (UserAuth entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {
				entityItem.setLogin(entity.getLogin());
				entityItem.setInOwnTableId(entity.getInOwnTableId());
				entityItem.setPassword(entity.getPassword());
				entityItem.setRole(entity.getRole());
				break;
			}
		}

		writeNewData(file, wrapper);
	}

	@Override
	public Set<String> getAllLogins() {
		throw new NotSupportedMethodException();
	}

	@Override
	public UserAuth getByCustomerId(Integer arg0) {
		throw new NotSupportedMethodException();
	}

	@Override
	public UserAuth getByLogin(String arg0) {
		throw new NotSupportedMethodException();
	}

	@Override
	public UserAuth getByManagerId(Integer arg0) {
		throw new NotSupportedMethodException();
	}

	private File getFile() {
		File file = new File(rootFolder + "userauths.xml");
		return file;
	}

}
