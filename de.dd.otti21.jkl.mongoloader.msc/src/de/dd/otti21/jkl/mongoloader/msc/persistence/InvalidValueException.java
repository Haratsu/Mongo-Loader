package de.dd.otti21.jkl.mongoloader.msc.persistence;

/**
 * InvalidValueException
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class InvalidValueException extends Exception {

	private static final long serialVersionUID = -4909494155310681236L;

	public InvalidValueException() {
		super();
	}

	public InvalidValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidValueException(String message) {
		super(message);
	}

	public InvalidValueException(Throwable cause) {
		super(cause);
	}
	
}
