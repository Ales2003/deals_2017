package ru.mail.ales2003.deals2017.services.servicesexceptions;

public class NotSupportedServiceMethodException extends RuntimeException {

	public NotSupportedServiceMethodException() {
		super("This method is not supported by Service layer");
	}

}
