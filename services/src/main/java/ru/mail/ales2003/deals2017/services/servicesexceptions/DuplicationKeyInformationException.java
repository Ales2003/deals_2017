package ru.mail.ales2003.deals2017.services.servicesexceptions;

public class DuplicationKeyInformationException extends RuntimeException {

	String msg;

	public DuplicationKeyInformationException(String msg) {
		super("Such entity exist already in storage");
		this.msg = msg;
	}

}
