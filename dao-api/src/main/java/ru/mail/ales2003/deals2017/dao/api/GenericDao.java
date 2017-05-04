package ru.mail.ales2003.deals2017.dao.api;

import java.util.List;

public interface GenericDao<T, PK> {

	T insert(T entity);

	T getByManagerOrCustomerId(PK id);

	List<T> getAll();

	void update(T entity);

	void delete(PK id);
	
	
}
