package ru.mail.ales2003.deals2017.webapp.aspects;

//@Aspect
public class DictionaryKeySaver {

	// @Pointcut("execution(*
	// ru.mail.ales2003.deals2017.dao.db.impl.AbstractDaoImplDb.insert(..))")
	public void fillingTables() {

	}

	// @After("fillingTables()")
	public void insertKeysInDb() {
		System.out.println("=======================================USE ASPECTS===========================");
	}

}
