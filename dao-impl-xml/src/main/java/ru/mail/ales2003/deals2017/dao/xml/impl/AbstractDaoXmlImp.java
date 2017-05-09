package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.GenericDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;

public abstract class AbstractDaoXmlImp<T, PK> implements GenericDao<T, PK> {

	private final XStream xstream = new XStream(new DomDriver());

	@Override
	public abstract void delete(PK id);

	@Override
	public abstract T get(PK id);

	@Override
	public abstract List<T> getAll();

	@Override
	public abstract T insert(T entity);

	@Override
	public abstract void update(T entity);

	protected void writeNewData(File file, XmlModelWrapper obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
