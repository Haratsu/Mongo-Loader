package de.dd.otti21.jkl.mongoloader.msc.persistence;

/**
 * InvalidKeyNameException
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class InvalidKeyNameException extends Exception {

	private static final long serialVersionUID = -2507533304615060176L;

	public InvalidKeyNameException() {
		super();
	}

	public InvalidKeyNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidKeyNameException(String message) {
		super(message);
	}

	public InvalidKeyNameException(Throwable cause) {
		super(cause);
	}

}
