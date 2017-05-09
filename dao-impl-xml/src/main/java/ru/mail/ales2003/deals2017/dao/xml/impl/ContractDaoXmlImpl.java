package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ru.mail.ales2003.deals2017.dao.api.IContractDao;
import ru.mail.ales2003.deals2017.dao.xml.impl.exception.NotSupportedMethodException;
import ru.mail.ales2003.deals2017.dao.xml.impl.wrapper.XmlModelWrapper;
import ru.mail.ales2003.deals2017.datamodel.Contract;

@Repository
public class ContractDaoXmlImpl extends AbstractDaoXmlImp<Contract, Integer> implements IContractDao {

	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Contract get(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Contract> wrapper = (XmlModelWrapper<Integer, Contract>) xstream.fromXML(file);
		List<Contract> entitys = wrapper.getRows();
		for (Contract entity : entitys) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<Integer, Contract> wrapper = (XmlModelWrapper<Integer, Contract>) xstream.fromXML(file);
		List<Contract> entitys = wrapper.getRows();
		Contract found = null;
		for (Contract entity : entitys) {
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
	public List<Contract> getAll() {
		File file = getFile();

		XmlModelWrapper<Integer, Contract> wrapper = (XmlModelWrapper<Integer, Contract>) xstream.fromXML(file);
		return wrapper.getRows();
	}

	@Override
	public Contract insert(Contract entity) {
		File file = getFile();

		XmlModelWrapper<Integer, Contract> wrapper = (XmlModelWrapper<Integer, Contract>) xstream.fromXML(file);
		List<Contract> entitys = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		entity.setId(newId);
		entitys.add(entity);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return entity;
	}

	@Override
	public void update(Contract entity) {
		File file = getFile();

		XmlModelWrapper<Integer, Contract> wrapper = (XmlModelWrapper<Integer, Contract>) xstream.fromXML(file);
		List<Contract> entitys = wrapper.getRows();
		for (Contract entityItem : entitys) {
			if (entityItem.getId().equals(entity.getId())) {

				entityItem.setContractStatus(entity.getContractStatus());
				entityItem.setCreated(entity.getCreated());
				entityItem.setCustomerId(entity.getCustomerId());
				entityItem.setPayForm(entity.getPayForm());
				entityItem.setPayStatus(entity.getPayStatus());
				entityItem.setTotalPrice(entity.getTotalPrice());
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	public BigDecimal calculateContractTotalPrice(Integer id) {
		return new BigDecimal("0");
		//throw new NotSupportedMethodException();
	}

	public void updateContractTotalPrice(Integer contractId, BigDecimal totalPrice) {
		//throw new NotSupportedMethodException();

	}

	private File getFile() {
		File file = new File(rootFolder + "contracts.xml");
		return file;
	}
}
