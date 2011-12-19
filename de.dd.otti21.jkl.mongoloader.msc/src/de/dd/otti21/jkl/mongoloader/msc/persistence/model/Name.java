package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

import java.io.Serializable;

/**
 * Name
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class Name implements Serializable {

	private static final long serialVersionUID = 8254251468053487260L;

	private static final Name EMPTY_NAME = new Name();

	private String name;

	private Name() {
		this("");
	}

	public Name(String name) {
		this.name = name;
	}

	public String asString() {
		return name == null ? EMPTY_NAME.asString() : name;
	}

	public boolean isEmpty() {
		return name == null ? true : name.isEmpty();
	}

	public boolean isEmptyOrWhitespace() {
		return name == null ? true : name.trim().isEmpty();
	}

	@Override
	public String toString() {
		return "Name [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
