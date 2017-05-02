package ru.mail.ales2003.deals2017.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mail.ales2003.deals2017.dao.api.IManagerDao;
import ru.mail.ales2003.deals2017.datamodel.Manager;

public class TestAssistant {

	private static List<Manager> managers = new ArrayList<>();
	private static List<Manager> managersFromDb = new ArrayList<>();

	@Inject
	IManagerDao managerDao;

	private void createManagerForTests() {
		int count = 10;
		for (int i = 0; i < count; i++) {
			Manager m = createInstance("firstName" + i, "patronymic" + i, "lastName" + i, "position" + i);
			managers.add(m);
			managersFromDb.add(managerDao.insert(m));
		}
	}

	private Manager createInstance(String firstName, String patronymic, String lastName, String position) {
		Manager m = new Manager();
		m.setFirstName(firstName);
		m.setPatronymic(patronymic);
		m.setLastName(lastName);
		m.setPosition(position);
		return m;
	}

	
}
