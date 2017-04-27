package ru.mail.ales2003.deals2017.services.servicesexceptions;

public class DuplicationKeyInformationException extends RuntimeException {

	String attachedMsg;

	/**
	 * @return the msg
	 */

	public DuplicationKeyInformationException(String msg) {
		super("Duplication of values is not supported: Entity with such name exist already in storage.");
		this.attachedMsg = msg;
	}

	/**
	 * @return the attachedMsg
	 */
	public String getAttachedMsg() {
		return attachedMsg;
	}

}
