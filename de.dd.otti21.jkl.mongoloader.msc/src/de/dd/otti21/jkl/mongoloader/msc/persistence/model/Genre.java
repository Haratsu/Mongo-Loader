package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

import com.mongodb.BasicDBObject;

/**
 * Genre
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class Genre extends BasicDBObject {
	private static final long serialVersionUID = 508659131805191175L;

	private Name name;
	private Boolean autoCreated;
	private Integer identifier;

	public Genre() {
		this(new Name(""), null, false);
	}

	public Genre(Name name, Integer identifier, Boolean autoCreated) {
		this.name = name;
		this.identifier = identifier;
		this.autoCreated = autoCreated;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
		this.put("name", name.asString());
	}

	public Boolean getAutoCreated() {
		return autoCreated == null ? false : autoCreated;
	}

	public void setAutoCreated(Boolean autoCreated) {
		this.autoCreated = autoCreated;
		this.put("autoCreated", autoCreated);
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
		if(identifier!=null) {
			this.put("identifier", identifier);
		} else {
			this.removeField("identifier");
		}
	}

//	public DBObject getInsertQuery() {
//		if (getIdentifier() == null) {
//			int maxIdentifier = getCurrentMaxIdentifier();
//			setIdentifier(maxIdentifier + 1);
//		}
//
//		return this;
//	}
//
//	private Integer getCurrentMaxIdentifier() {
//		try {
//			MongoMax<Integer> max = new MongoMax<Integer>("identifier","genre");
//			return Math.max(max.getMax(),1000);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
}
