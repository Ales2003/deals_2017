package ru.mail.ales2003.deals2017.dao.xml.impl.exception;

public class NotSupportedMethodException extends RuntimeException {

	public NotSupportedMethodException() {
		super("This method is not supported by XML DAO layer");
	}

}
