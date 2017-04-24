package ru.mail.ales2003.deals2017.dao.xml.impl;

import java.util.List;

import ru.mail.ales2003.deals2017.dao.api.GenericDao;

public abstract class AbstractDaoXmlImp<T, PK> implements GenericDao<T, PK> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.dao.api.GenericDao#delete(java.lang.Object)
	 */
	@Override
	public abstract void delete(PK arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.mail.ales2003.deals2017.dao.api.GenericDao#get(java.lang.Object)
	 */
	@Override
	public abstract T get(PK arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.mail.ales2003.deals2017.dao.api.GenericDao#getAll()
	 */
	@Override
	public abstract List<T> getAll();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.dao.api.GenericDao#insert(java.lang.Object)
	 */
	@Override
	public abstract T insert(T arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.mail.ales2003.deals2017.dao.api.GenericDao#update(java.lang.Object)
	 */
	@Override
	public abstract void update(T arg0);

}
