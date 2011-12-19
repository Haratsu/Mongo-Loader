package de.dd.otti21.jkl.mongoloader.msc.persistence;

/**
 * InvalidGenreException
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class InvalidGenreException extends Exception {

	private static final long serialVersionUID = 8736732739706066571L;

	public InvalidGenreException() {
		super();
	}

	public InvalidGenreException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidGenreException(String message) {
		super(message);
	}

	public InvalidGenreException(Throwable cause) {
		super(cause);
	}

}
