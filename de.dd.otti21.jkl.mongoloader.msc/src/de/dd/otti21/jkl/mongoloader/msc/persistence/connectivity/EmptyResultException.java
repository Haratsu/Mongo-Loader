package de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity;

/**
 * EmptyResultException
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class EmptyResultException extends Exception {

	private static final long serialVersionUID = -2037853580996453584L;

	public EmptyResultException() {
		super();
	}

	public EmptyResultException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyResultException(String message) {
		super(message);
	}

	public EmptyResultException(Throwable cause) {
		super(cause);
	}

	
}
