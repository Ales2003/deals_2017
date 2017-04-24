package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.IItemDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.Item;

@Repository
public class ItemDaoXmlImpl implements IItemDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Item get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Item> wrapper = (XmlModelWrapper<Integer, Item>) xstream.fromXML(file);
		List<Item> entitys = wrapper.getRows();
		for (Item entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Item> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item insert(Item entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Item entity) {
		// TODO Auto-generated method stub

	}

	private void writeNewData(File file, XmlModelWrapper obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private File getFile() {
		File file = new File(rootFolder + "items.xml");
		return file;
	}

}
